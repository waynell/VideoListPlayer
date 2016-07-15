package com.waynell.videolist.demo.model;

/**
 * @author Wayne
 */
public class PicItem extends BaseItem {

    private String mCoverUrl;

    public PicItem(String coverUrl) {
        super(BaseItem.VIEW_TYPE_PICTURE);
        mCoverUrl = coverUrl;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }
}
