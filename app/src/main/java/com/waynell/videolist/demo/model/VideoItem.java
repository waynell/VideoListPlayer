package com.waynell.videolist.demo.model;

/**
 * @author Wayne
 */
public class VideoItem {
    private String mVideoUrl;
    private String mCoverUrl;

    public VideoItem(String videoUrl, String coverUrl) {
        mVideoUrl = videoUrl;
        mCoverUrl = coverUrl;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }
}
