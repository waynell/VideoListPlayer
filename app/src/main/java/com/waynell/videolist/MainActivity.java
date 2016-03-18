package com.waynell.videolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll.RecyclerViewItemPositionGetter;
import com.waynell.videolist.model.VideoListItem;
import com.waynell.videolist.model.VideoViewHolder;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mScrollState;

    private ItemsPositionGetter mItemsPositionGetter;

    private List<VideoListItem> mListItems = new ArrayList<>();

    private SingleListViewItemActiveCalculator mCalculator = new SingleListViewItemActiveCalculator(new
            DefaultSingleItemCalculatorCallback(), mListItems);

    private static final String url = "http://vweixinf.tc.qq" +
            ".com/snsvideodownload?filekey" +
            "=30270201010420301e0201660402535a0410165c8aa30524d1de81b162c8576b880b020306fe010400&bizid=1023&hy=SZ" +
            "&fileparam=302c0201010425302302040df93e0b020456dd610f02024eea02031e903a02032dc6c00204bdda370a0201000400" +
            "%20200%20GET%20vweixinf.tc.qq" +
            ".com%20/snsvideodownload?filekey" +
            "=30270201010420301e0201660402535a0410165c8aa30524d1de81b162c8576b880b020306fe010400&bizid=1023&hy=SZ" +
            "&fileparam=302c0201010425302302040df93e0b020456dd610f02024eea02031e903a02032dc6c00204bdda370a0201000400" +
            "%20Tue%20Mar%2008%2017:40:09%20CST%202016%20130%20459147%20Complete";

    private static final String url2 = "http://10.33.91.71/test/v/20160316/7020434498005237761803316592_s.mp4";

    private static final String url3 = "http://10.33.91.71/test/v/20160316/6542116569848217601803281574_s.mp4";

    private static final String url4 = "http://10.33.91.71/test/v/20160316/6542116569848217601803363274_s.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mListItems.add(new VideoListItem(url));
        mListItems.add(new VideoListItem(url2));
        mListItems.add(new VideoListItem(url3));
        mListItems.add(new VideoListItem(url4));
        mListItems.add(new VideoListItem(url));
        mListItems.add(new VideoListItem(url2));
        mListItems.add(new VideoListItem(url3));
        mListItems.add(new VideoListItem(url4));
        mListItems.add(new VideoListItem(url));
        mListItems.add(new VideoListItem(url2));
        mListItems.add(new VideoListItem(url3));
        mListItems.add(new VideoListItem(url4));
        mListItems.add(new VideoListItem(url));
        mListItems.add(new VideoListItem(url2));
        mListItems.add(new VideoListItem(url3));
        mListItems.add(new VideoListItem(url4));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new VideoListAdapter());
        mItemsPositionGetter = new RecyclerViewItemPositionGetter(layoutManager, mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && !mListItems.isEmpty()){
                    mCalculator.onScrollStateIdle(mItemsPositionGetter);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mItemsPositionGetter, mScrollState);
            }
        });
    }

    private class VideoListAdapter extends RecyclerView.Adapter<VideoViewHolder> {

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            VideoListItem item = mListItems.get(position);
            Log.e("VideoListItem", "preFilePath " + item.getFilePath() + " prepos " + item.getPosition()
                    + " curpos " + position);
            item.setViewHolder(holder);
            item.setPosition(position);

            Glide.with(holder.itemView.getContext())
                    .load("http://10.33.91.71/test/small/pg/20160316/6542116569848217601803281515.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.videoCover);

            Glide.with(holder.itemView.getContext())
                    .using(new HttpUrlGlideUrlLoader(), InputStream.class)
                    .load(new GlideUrl(item.getHttpUrl()))
                    .as(File.class)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new VideoViewTarget(holder.videoView, holder.videoCover, item));
            holder.videoTitle.setText(String.format("Video Position %s", position));
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

    }

}
