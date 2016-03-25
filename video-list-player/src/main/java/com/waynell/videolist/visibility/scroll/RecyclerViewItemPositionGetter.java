package com.waynell.videolist.visibility.scroll;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.waynell.videolist.visibility.utils.Config;
import com.waynell.videolist.visibility.utils.Logger;

/**
 * This class is an API for {@link com.waynell.videolist.visibility.calculator.ListItemsVisibilityCalculator}
 * Using this class is can access all the data from RecyclerView
 * Created by danylo.volokh on 06.01.2016.
 */
public class RecyclerViewItemPositionGetter implements ItemsPositionGetter {

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = RecyclerViewItemPositionGetter.class.getSimpleName();

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public RecyclerViewItemPositionGetter(LinearLayoutManager layoutManager, RecyclerView recyclerView) {
        mLayoutManager = layoutManager;
        mRecyclerView = recyclerView;
    }

    @Override
    public View getChildAt(int position) {
        if(SHOW_LOGS) {
            Logger.v(TAG, "mRecyclerView getChildAt, position " + position);
        }

        return mLayoutManager.getChildAt(position);
    }

    @Override
    public int indexOfChild(View view) {
        return mRecyclerView.indexOfChild(view);
    }

    @Override
    public int getChildCount() {
        int childCount = mRecyclerView.getChildCount();
        if(SHOW_LOGS) {
            Logger.v(TAG, "getChildCount, mRecyclerView " + childCount);
        }
        return childCount;
    }

    @Override
    public int getLastVisiblePosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public int getFirstVisiblePosition() {
        if(SHOW_LOGS) Logger.v(TAG, "getFirstVisiblePosition, findFirstVisibleItemPosition " + mLayoutManager.findFirstVisibleItemPosition());
        return mLayoutManager.findFirstVisibleItemPosition();
    }
}
