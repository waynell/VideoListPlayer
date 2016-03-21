package com.waynell.videolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll.RecyclerViewItemPositionGetter;
import com.waynell.videolist.model.VideoListItem;

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

        mListItems.add(new VideoListItem(url, purl1));
        mListItems.add(new VideoListItem(url2, purl2));
        mListItems.add(new VideoListItem(url3, purl3));
        mListItems.add(new VideoListItem(url4, purl4));
        mListItems.add(new VideoListItem(url, purl1));
        mListItems.add(new VideoListItem(url2, purl2));
        mListItems.add(new VideoListItem(url3, purl3));
        mListItems.add(new VideoListItem(url4, purl4));
        mListItems.add(new VideoListItem(url, purl1));
        mListItems.add(new VideoListItem(url2, purl2));
        mListItems.add(new VideoListItem(url3, purl3));
        mListItems.add(new VideoListItem(url4, purl4));
        mListItems.add(new VideoListItem(url, purl1));
        mListItems.add(new VideoListItem(url2, purl2));
        mListItems.add(new VideoListItem(url3, purl3));
        mListItems.add(new VideoListItem(url4, purl4));
        mListItems.add(new VideoListItem(url, purl1));
        mListItems.add(new VideoListItem(url2, purl2));
        mListItems.add(new VideoListItem(url3, purl3));
        mListItems.add(new VideoListItem(url4, purl4));

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
            Log.i("VideoListItem", "preFilePath " + item.getVideoPath() + " prepos " + item.getPosition()
                    + " curpos " + position);
            item.bindViewHolder(position, holder);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

    }

}
