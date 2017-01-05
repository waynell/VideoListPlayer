package com.waynell.videolist.demo.holder;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.waynell.videolist.demo.BuildConfig;
import com.waynell.videolist.demo.R;
import com.waynell.videolist.demo.VideoListGlideModule;
import com.waynell.videolist.demo.model.VideoItem;
import com.waynell.videolist.demo.model.VideoLoadMvpView;
import com.waynell.videolist.demo.target.VideoLoadTarget;
import com.waynell.videolist.demo.target.VideoProgressTarget;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.widget.TextureVideoView;

import java.io.File;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;


public class VideoViewHolder extends BaseViewHolder<VideoItem>
        implements VideoLoadMvpView, ViewPropertyAnimatorListener, ListItem {

    @Bind(R.id.video_view)
    public TextureVideoView videoView;

    @Bind(R.id.video_text)
    public TextView videoTitle;

    @Bind(R.id.video_cover)
    public ImageView videoCover;

    @Bind(R.id.video_progress)
    public CircularProgressBar progressBar;

    private int videoState = STATE_IDLE;
    private String videoLocalPath;

    private final VideoProgressTarget progressTarget;
    private final VideoLoadTarget videoTarget;

    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;

    private static final String TAG = "ListItem";
    private static final boolean SHOW_LOGS = BuildConfig.DEBUG;

    public VideoViewHolder(View view) {
        super(view);

        videoView.setAlpha(0);
        videoTarget = new VideoLoadTarget(this);
        progressTarget = new VideoProgressTarget(videoTarget, progressBar);
    }

    @OnClick(R.id.video_view)
    void cliclVideoView() {
        if (!videoView.isHasAudio()) {
            Toast.makeText(itemView.getContext(), "video has no sound", Toast.LENGTH_SHORT).show();
            return;
        }

        if (videoView.isMute()) {
            videoView.unMute();
            Toast.makeText(itemView.getContext(), "turn on video sound", Toast.LENGTH_SHORT).show();
        }
        else {
            videoView.mute();
            Toast.makeText(itemView.getContext(), "turn off video sound", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(itemView.getContext(), "durration: " + videoView.getDuration() + " pos: " + videoView.getCurrentPosition(), Toast.LENGTH_LONG).show();
    }

    private void reset() {
        videoState = STATE_IDLE;
        videoView.stop();
        videoLocalPath = null;
        videoStopped();
    }

    @Override
    public void onBind(int position, VideoItem item) {
        reset();

        videoTitle.setText(String.format("Video Position %s", position));
        progressTarget.setModel(item.getVideoUrl());

        // load video cover photo
        Glide.with(itemView.getContext())
                .load(item.getCoverUrl())
                .placeholder(new ColorDrawable(0xffdcdcdc))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(videoCover);

        // load video file
        Glide.with(itemView.getContext())
                .using(VideoListGlideModule.getOkHttpUrlLoader(), InputStream.class)
                .load(new GlideUrl(item.getVideoUrl()))
                .as(File.class)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(progressTarget);
    }

    private void cancelAlphaAnimate(View v) {
        ViewCompat.animate(v).cancel();
    }

    private void startAlphaAnimate(View v) {
        ViewCompat.animate(v).setListener(this).alpha(0f);
    }

    @Override
    public TextureVideoView getVideoView() {
        return videoView;
    }

    @Override
    public void videoBeginning() {
        videoView.setAlpha(1.f);
        cancelAlphaAnimate(videoCover);
        startAlphaAnimate(videoCover);
    }

    @Override
    public void videoStopped() {
        cancelAlphaAnimate(videoCover);
        videoView.setAlpha(0);
        videoCover.setAlpha(1.f);
        videoCover.setVisibility(View.VISIBLE);
    }

    @Override
    public void videoPrepared(MediaPlayer player) {

    }

    @Override
    public void videoResourceReady(String videoPath) {
        videoLocalPath = videoPath;
        if(videoLocalPath != null) {
            videoView.setVideoPath(videoPath);
            if(videoState == STATE_ACTIVED) {
                videoView.start();
            }
        }
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        if (SHOW_LOGS) {
            Log.i(TAG, "setActive " + newActiveViewPosition + " path " + videoLocalPath);
        }

        videoState = STATE_ACTIVED;
        if (videoLocalPath != null) {
            videoView.setVideoPath(videoLocalPath);
            videoView.start();
        }
    }

    @Override
    public void deactivate(View currentView, int position) {
        if(SHOW_LOGS) {
            Log.w(TAG, "deactivate " + position);
        }

        videoState = STATE_DEACTIVED;
        videoView.stop();
        videoStopped();
    }

    @Override
    public void onAnimationStart(View view) {

    }

    @Override
    public void onAnimationEnd(View view) {
        view.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationCancel(View view) {

    }
}
