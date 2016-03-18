package com.waynell.videolist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.waynell.videolist.model.VideoListItem;
import com.waynell.videolist.ui.TextureVideoView;

import java.io.File;

/**
 * @author Wayne
 */
public class VideoViewTarget extends ViewTarget<TextureVideoView, File> implements TextureVideoView.Callback {

    private VideoListItem mItem;
    private ImageView mImageView;

    public VideoViewTarget(TextureVideoView view, ImageView cover, VideoListItem item) {
        super(view);
        view.setCallback(this);
        mItem = item;
        mImageView = cover;
        if (mImageView.animate() != null) {
            Log.e("VideoViewTarget", "onConstructor cancel animate");
            mImageView.animate().cancel();
        }
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setAlpha(1.f);
    }

    @Override
    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
        Log.i("VideoViewTarget", "onResourceReady " + mItem.getPosition() + " path " + resource.getAbsolutePath());
        mItem.setFilePath(resource.getAbsolutePath());
    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mImageView.animate() != null) {
            Log.i("VideoViewTarget", "onError cancel animate");
            mImageView.animate().cancel();
        }
        mImageView.setAlpha(1.f);
        mImageView.setVisibility(View.VISIBLE);
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
            Log.i("VideoViewTarget", "onInfo MEDIA_INFO_VIDEO_RENDERING_START");
            mImageView.animate()
                    .alpha(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mImageView.setVisibility(View.GONE);
                        }
                    });
        }
        return false;
    }
}
