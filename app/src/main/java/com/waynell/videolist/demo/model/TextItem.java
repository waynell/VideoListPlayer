package com.waynell.videolist.demo.model;

/**
 * @author Wayne
 */
public class TextItem extends BaseItem {

    private String mText;

    public TextItem(String text) {
        super(BaseItem.VIEW_TYPE_TEXT);
        mText = text;
    }

    public String getText() {
        return mText;
    }
}
