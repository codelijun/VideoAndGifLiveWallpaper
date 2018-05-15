package com.example.lijun.videolivewallpaper.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.lijun.videolivewallpaper.BuildConfig;

import java.io.IOException;

public class GIFImage extends BaseObject {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "GIFImage";
    private static final int FRAME_DURATION = 20;
    private Movie mMovie;
    private Handler mHandler;
    private boolean mVisible;
    private float mScreenAspectRatio;
    private float mImageAspectRatio;
    private int mImageWidth;
    private int mImageHeight;

    public GIFImage(Context context, String fileName) {
        mHandler = new Handler(Looper.myLooper());
        try {
            mMovie = Movie.decodeStream(context.getResources().getAssets().open(fileName));
            if (DEBUG) {
                Log.d(TAG, " GIFImage() width== " + mMovie.width() + " height== " + mMovie.height());
            }
            if (mMovie != null) {
                mImageWidth = mMovie.width();
                mImageHeight = mMovie.height();
                mImageAspectRatio = (float) mMovie.width() / (float) mMovie.height();
            }
        } catch (IOException e) {
            if (DEBUG) {
                Log.e(TAG, " onCreateEngine() error Could not load assets file ");
            }
            e.printStackTrace();
        }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        mVisible = visible;
        if (visible) {
            mHandler.post(drawGIF);
        } else {
            mHandler.removeCallbacks(drawGIF);
        }
    }

    private Runnable drawGIF = new Runnable() {
        @Override
        public void run() {
            draw();
        }
    };

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (DEBUG) {
            Log.d(TAG, " onSurfaceChanged() " + "holder = [" + holder + "], format = [" + format + "], width = [" + width + "], height = [" + height + "]");
        }
        mScreenAspectRatio = (float) width / (float) height;
        if (mScreenAspectRatio > mImageAspectRatio) {  //根据图片的宽缩放图片
            mScaleX = (float) width / (float) mImageWidth;
            mScaleY = (float) width / (float) mImageWidth;
        } else {  //根据图片的高缩放图片
            mScaleY = (float) height / (float) mImageHeight;
            mScaleX = (float) height / (float) mImageHeight;
        }
        if (DEBUG) {
            Log.d(TAG, " onSurfaceChanged() mScaleX== " + mScaleX + " mScaleY== " + mScaleY);
        }
    }

    private float mScaleX, mScaleY;

    private void draw() {
        if (mVisible && mMovie != null) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.save();
            canvas.scale(mScaleX, mScaleY);
            mMovie.draw(canvas, 0, 0);
            canvas.restore();
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            mMovie.setTime((int) (System.currentTimeMillis() % mMovie.duration()));

            mHandler.removeCallbacks(drawGIF);
            mHandler.postDelayed(drawGIF, FRAME_DURATION);
        }
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(drawGIF);
    }
}
