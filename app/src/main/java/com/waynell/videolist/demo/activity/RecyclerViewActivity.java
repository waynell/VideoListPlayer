package com.waynell.videolist.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.waynell.videolist.demo.R;
import com.waynell.videolist.demo.holder.BaseViewHolder;
import com.waynell.videolist.demo.holder.ViewHolderFactory;
import com.waynell.videolist.demo.model.BaseItem;
import com.waynell.videolist.demo.util.ItemUtils;
import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Wayne
 */
public class RecyclerViewActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mScrollState;

    private SingleListViewItemActiveCalculator mCalculator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        final MyAdapter adapter = new MyAdapter();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mCalculator = new SingleListViewItemActiveCalculator(adapter,
                new RecyclerViewItemPositionGetter(layoutManager, mRecyclerView));

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && adapter.getItemCount() > 0){
                    mCalculator.onScrollStateIdle();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mScrollState);
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<BaseViewHolder<? extends BaseItem>>
            implements ItemsProvider {

        private List<? extends BaseItem> mListItems;

        public MyAdapter() {
            mListItems = ItemUtils.generateMockData();
        }

        @Override
        public BaseViewHolder<? extends BaseItem> onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolderFactory.buildViewHolder(parent, viewType);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            BaseItem baseItem = mListItems.get(position);
            holder.onBind(position, baseItem);
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mListItems.get(position).getViewType();
        }

        @Override
        public ListItem getListItem(int position) {
            RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(position);
            if (holder instanceof ListItem) {
                return (ListItem) holder;
            }
            return null;
        }

        @Override
        public int listItemSize() {
            return getItemCount();
        }
    }
}
