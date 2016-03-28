# VideoListPlayer
VideoListPlayer实现了在列表控件（ListView, RecyclerView）中加载并播放视频，并支持滑动时自动播放/暂停的功能

利用该项目，可以轻松实现类似Instagram的视频播放功能

**注意：最低支持API 14以上**

#效果预览
![](./art/preview.gif)

#基本用法
添加gradle依赖
	
	repositories {
		maven { url "https://jitpack.io" }
	}
	
	dependencies {
	        compile 'com.github.waynell:VideoListPlayer:1.0'
	}

在xml布局中加入以下代码

	<com.waynell.videolist.widget.TextureVideoView
            android:id="@+id/video_view"
            android:layout_width="match_parent"
            android:layout_height="300dp" />
            
然后设置视频路径地址，最后调用start()方法开始播放视频

	videoView.setVideoPath(mVideoPath);
    videoView.start();

视频播放是异步的，可以通过设置MediaPlayerCallback回调监听播放事件

	view.setMediaPlayerCallback(this);

监听事件如下

    void onPrepared(MediaPlayer mp);
    void onCompletion(MediaPlayer mp);
    void onBufferingUpdate(MediaPlayer mp, int percent);
    void onVideoSizeChanged(MediaPlayer mp, int width, int height);

    boolean onInfo(MediaPlayer mp, int what, int extra);
    boolean onError(MediaPlayer mp, int what, int extra);


#滑动时自动播放/停止的功能
首先你的adapter models必须先实现ListItem接口
	
    public interface ListItem {
    	// 返回当前item view的可见比
        int getVisibilityPercents(View view);
        
        // 当前item被激活
        void setActive(View newActiveView, int newActiveViewPosition);
        
        // 当前item被取消
        void deactivate(View currentView, int position);
	}


最后添加以下代码实现可见比的计算，以RecyclerView为例

	ListItemsVisibilityCalculator calculator = new SingleListViewItemActiveCalculator(new
            DefaultSingleItemCalculatorCallback(), mListItems);
            
    mItemsPositionGetter = new RecyclerViewItemPositionGetter(layoutManager, mRecyclerView);
    
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && !mListItems.isEmpty()){
                    mCalculator.onScrollStateIdle(mItemsPositionGetter);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mItemsPositionGetter, mScrollState);
            }
        });
        
# 网络视频的本地缓存
请参考demo工程的实现


#实现原理
请参见我的博客 [视频在滑动列表中的异步缓存和播放](http://blog.waynell.com/2016/03/21/video-loader/)


#Thanks
[VideoPlayerManager](https://github.com/danylovolokh/VideoPlayerManager)
滑动自动播放/暂停的功能基于项目优化而来
