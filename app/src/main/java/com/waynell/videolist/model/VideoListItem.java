package com.waynell.videolist.model;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.volokh.danylo.visibility_utils.items.ListItem;
import com.waynell.videolist.VideoViewHolder;

/**
 * @author Wayne
 */
public class VideoListItem implements ListItem {

    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;

    private int mState = STATE_IDLE;

    private int mPosition;
    private String mCoverUrl;
    private String mVideoUrl;
    private String mVideoPath;
    private VideoViewHolder mViewHolder;
    private final Rect mCurrentViewRect = new Rect();

    public VideoListItem(String videoUrl, String coverUrl) {
        mCoverUrl = coverUrl;
        mVideoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public int getPosition() {
        return mPosition;
    }

    public void bindViewHolder(int position, VideoViewHolder viewHolder) {
        mPosition = position;
        mViewHolder = viewHolder;
    }

    public void setVideoPath(String videoPath) {
        Log.e("VideoListItem", "setVideoPath " + videoPath + " pos " + mPosition);
        mVideoPath = videoPath;
        if(videoPath != null) {
            if (mState == STATE_ACTIVED) {
                mViewHolder.videoView.setVideoPath(videoPath);
                mViewHolder.videoView.start();
            }
        }
    }

    public String getVideoPath() {
        return mVideoPath;
    }

    public String getVideoUrl() {
        return mVideoUrl;
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
        Log.e("VideoListItem", "setActive " + newActiveViewPosition + " path " + mVideoPath);
        mState = STATE_ACTIVED;
        if (mVideoPath != null) {
            mViewHolder.videoView.setVideoPath(mVideoPath);
            mViewHolder.videoView.start();
        }
    }

    @Override
    public void deactivate(View currentView, int position) {
        Log.e("VideoListItem", "deactivate " + position);
        mState = STATE_DEACTIVED;
        mViewHolder.videoView.stop();
        mViewHolder.videoCover.setVisibility(View.VISIBLE);
        mViewHolder.videoCover.setAlpha(1.f);
    }
}
