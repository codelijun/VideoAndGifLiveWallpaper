package com.example.lijun.videolivewallpaper.wallpaper;

import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.lijun.videolivewallpaper.BuildConfig;
import com.example.lijun.videolivewallpaper.object.BaseObject;
import com.example.lijun.videolivewallpaper.util.WallpaperFactory;

public class VideoWallpaperService extends WallpaperService {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "VideoWallpaperService";

    private static final String FILE_NAME = "wallpaper.mp4";

    @Override
    public Engine onCreateEngine() {
        return new GIFWallpaperEngine();
    }

    private class GIFWallpaperEngine extends WallpaperService.Engine {
        BaseObject mDisplayObject;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            mDisplayObject = WallpaperFactory.getWallpaperObject(getApplicationContext(), FILE_NAME);
            mDisplayObject.setSurfaceHolder(surfaceHolder);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (DEBUG) {
                Log.d(TAG, " onVisibilityChanged() " + "visible = [" + visible + "]");
            }
            if (mDisplayObject != null) {
                mDisplayObject.onVisibilityChanged(visible);
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            if (mDisplayObject != null) {
                mDisplayObject.onSurfaceChanged(holder, format, width, height);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (DEBUG) {
                Log.d(TAG, " onDestroy() ");
            }
            if (mDisplayObject != null) {
                mDisplayObject.onDestroy();
            }
        }
    }
}
