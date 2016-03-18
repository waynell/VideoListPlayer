package com.waynell.videolist.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.waynell.videolist.R;
import com.waynell.videolist.ui.TextureVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.video_view)
    public TextureVideoView videoView;

    @Bind(R.id.video_text)
    public TextView videoTitle;

    @Bind(R.id.video_cover)
    public ImageView videoCover;

    public VideoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
