package com.waynell.videolist.demo.holder;

import android.view.View;
import android.widget.TextView;

import com.waynell.videolist.demo.R;
import com.waynell.videolist.demo.model.TextItem;

import butterknife.Bind;

/**
 * @author Wayne
 */
public class TextViewHolder extends BaseViewHolder<TextItem> {

    @Bind(R.id.text_view)
    TextView mTextView;

    public TextViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(int position, TextItem iItem) {
        mTextView.setText(String.format("%s - %s", iItem.getText(), position));
    }
}
