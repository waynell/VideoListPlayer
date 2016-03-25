package com.waynell.videolist.demo.target;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.waynell.videolist.demo.model.VideoListItem;
import com.waynell.videolist.widget.TextureVideoView;

import java.io.File;

/**
 * @author Wayne
 */
public class VideoLoadTarget extends ViewTarget<TextureVideoView, File> implements TextureVideoView.MediaPlayerCallback {

    private VideoListItem mItem;
    private final ImageView mCoverView;

    public VideoLoadTarget(TextureVideoView videoView, ImageView coverView) {
        super(videoView);
        mCoverView = coverView;
    }

    public void bind(VideoListItem item) {
        mItem = item;
        if (mCoverView.animate() != null) {
            mCoverView.animate().cancel();
        }
        mCoverView.setVisibility(View.VISIBLE);
        mCoverView.setAlpha(1.f);

        Glide.with(mCoverView.getContext())
                .load(item.getCoverUrl())
                .placeholder(new ColorDrawable(0xffdcdcdc))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mCoverView);

        view.setMediaPlayerCallback(this);
        view.stop();
    }

    @Override
    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
        mItem.setVideoPath(resource.getAbsolutePath());
    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mCoverView.animate() != null) {
            mCoverView.animate().cancel();
        }
        mCoverView.setAlpha(1.f);
        mCoverView.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            mCoverView.animate()
                    .alpha(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mCoverView.setVisibility(View.INVISIBLE);
                        }
                    });
            return true;
        }
        return false;
    }
}
