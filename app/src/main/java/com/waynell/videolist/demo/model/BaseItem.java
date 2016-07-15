package com.waynell.videolist.demo.model;

/**
 * @author Wayne
 */
public abstract class BaseItem {

    public static final int VIEW_TYPE_TEXT = 0;
    public static final int VIEW_TYPE_PICTURE = 1;
    public static final int VIEW_TYPE_VIDEO = 2;

    private final int mViewType;

    public BaseItem(int viewType) {
        mViewType = viewType;
    }

    public int getViewType() {
        return mViewType;
    }
}
