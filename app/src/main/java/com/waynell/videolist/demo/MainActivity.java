package com.waynell.videolist.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.waynell.videolist.demo.model.VideoItem;
import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mScrollState;

    private SingleListViewItemActiveCalculator mCalculator;

    private static final String url = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";

    private static final String url2 = "http://techslides.com/demos/sample-videos/small.mp4";

    private static final String url3 = "http://download.wavetlan.com/SVV/Media/HTTP/H264/Other_Media/H264_test7_voiceclip_mp4_480x360.mp4";

    private static final String url4 = "http://download.wavetlan.com/SVV/Media/HTTP/MP4/ConvertedFiles/Media-Convert/Unsupported/test7.mp4";

    private static final String purl1 = "http://img10.3lian.com/sc6/show02/67/27/03.jpg";
    private static final String purl2 = "http://img10.3lian.com/sc6/show02/67/27/04.jpg";
    private static final String purl3 = "http://img10.3lian.com/sc6/show02/67/27/01.jpg";
    private static final String purl4 = "http://img10.3lian.com/sc6/show02/67/27/02.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final VideoListAdapter adapter = new VideoListAdapter();
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

    private class VideoListAdapter extends RecyclerView.Adapter<VideoViewHolder> implements ItemsProvider {

        private List<VideoItem> mListItems = new ArrayList<>();

        public VideoListAdapter() {
            generateMockData();
        }

        private void generateMockData() {
            mListItems.add(new VideoItem(url, purl1));
            mListItems.add(new VideoItem(url2, purl2));
            mListItems.add(new VideoItem(url3, purl3));
            mListItems.add(new VideoItem(url4, purl4));
            mListItems.add(new VideoItem(url, purl1));
            mListItems.add(new VideoItem(url2, purl2));
            mListItems.add(new VideoItem(url3, purl3));
            mListItems.add(new VideoItem(url4, purl4));
            mListItems.add(new VideoItem(url, purl1));
            mListItems.add(new VideoItem(url2, purl2));
            mListItems.add(new VideoItem(url3, purl3));
            mListItems.add(new VideoItem(url4, purl4));
            mListItems.add(new VideoItem(url, purl1));
            mListItems.add(new VideoItem(url2, purl2));
            mListItems.add(new VideoItem(url3, purl3));
            mListItems.add(new VideoItem(url4, purl4));
            mListItems.add(new VideoItem(url, purl1));
            mListItems.add(new VideoItem(url2, purl2));
            mListItems.add(new VideoItem(url3, purl3));
            mListItems.add(new VideoItem(url4, purl4));
        }

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_list_item, parent, false));
        }

        public VideoItem getItem(int position) {
            return mListItems.get(position);
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            holder.bind(position, getItem(position));
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
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
