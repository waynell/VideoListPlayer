package com.waynell.videolist.model;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * @author Wayne
 */
public class VideoListItem implements ListItem {

    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;

    private int mState = STATE_IDLE;

    private int mPosition;
    private String mHttpUrl;
    private String mFilePath;
    private VideoViewHolder mViewHolder;
    private final Rect mCurrentViewRect = new Rect();

    public VideoListItem(String url) {
        mHttpUrl = url;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setViewHolder(VideoViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }

    public void setFilePath(String filePath) {
        Log.e("VideoListItem", "setFilePath " + filePath + " pos " + mPosition);
        mFilePath = filePath;
        if(filePath != null) {
            if (mState == STATE_ACTIVED) {
                mViewHolder.videoView.setVideoPath(filePath);
                mViewHolder.videoView.start();
            }
        }
    }

    public String getFilePath() {
        return mFilePath;
    }

    public String getHttpUrl() {
        return mHttpUrl;
    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    @Override
    public int getVisibilityPercents(View currentView) {
        int percents = 100;

        currentView.getLocalVisibleRect(mCurrentViewRect);

        int height = currentView.getHeight();

        if(viewIsPartiallyHiddenTop()){
            // view is partially hidden behind the top edge
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if(viewIsPartiallyHiddenBottom(height)){
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        return percents;
    }

    @Override
    public void setActive(View currentView, int newActiveViewPosition) {
        Log.e("VideoListItem", "setActive " + newActiveViewPosition + " path " + mFilePath);
        mState = STATE_ACTIVED;
        if (mFilePath != null) {
            mViewHolder.videoView.setVideoPath(mFilePath);
            mViewHolder.videoView.start();
        }
    }

    @Override
    public void deactivate(View currentView, int position) {
        Log.e("VideoListItem", "deactivate " + position);
        mState = STATE_DEACTIVED;
        mViewHolder.videoView.stop();
    }
}
