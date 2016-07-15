package com.waynell.videolist.demo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.waynell.videolist.demo.model.BaseItem;
import com.waynell.videolist.visibility.items.ListItem;

import butterknife.ButterKnife;

/**
 * @author Wayne
 */
public abstract class BaseViewHolder<T extends BaseItem> extends RecyclerView.ViewHolder implements ListItem {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBind(int position, T iItem);

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {

    }

    @Override
    public void deactivate(View currentView, int position) {

    }
}
