package com.example.lijun.videolivewallpaper.object;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.lijun.videolivewallpaper.BuildConfig;

import java.io.IOException;

public class VideoWallpaper extends BaseObject {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "VideoWallpaper";
    private MediaPlayer mMediaPlayer;

    public VideoWallpaper(Context context, String fileName) {
        mMediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor fileDescriptor = context.getAssets().openFd(fileName);
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setVolume(0, 0);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            if (DEBUG) {
                Log.e(TAG, " onCreateEngine() error Could not load assets file ");
            }
            e.printStackTrace();
        }
    }

    @Override
    public void setSurfaceHolder(final SurfaceHolder surfaceHolder) {
        super.setSurfaceHolder(surfaceHolder);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.setSurface(surfaceHolder.getSurface());
            }
        });
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if (visible) {
            mMediaPlayer.start();
        } else {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void onDestroy() {
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
