package com.example.lijun.videolivewallpaper.util;

import android.content.Context;
import android.util.Log;

import com.example.lijun.videolivewallpaper.BuildConfig;
import com.example.lijun.videolivewallpaper.object.BaseObject;
import com.example.lijun.videolivewallpaper.object.GIFImage;
import com.example.lijun.videolivewallpaper.object.VideoWallpaper;

public class WallpaperFactory {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "WallpaperFactory";

    private static final String SUFFIX_MP4 = "mp4";
    private static final String SUFFIX_GIF = "gif";

    public static BaseObject getWallpaperObject(Context context, String fileName) {
        if (DEBUG) {
            Log.d(TAG, " getWallpaperObject() " + "fileName = [" + fileName + "]");
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (suffix.equals(SUFFIX_MP4)) {
            if (DEBUG) {
                Log.d(TAG, " getWallpaperObject() MP4格式的视频文件");
            }
            return new VideoWallpaper(context, fileName);
        } else if (suffix.equals(SUFFIX_GIF)) {
            if (DEBUG) {
                Log.d(TAG, " getWallpaperObject() gif格式的动图文件");
            }
            return new GIFImage(context, fileName);
        } else {
            if (DEBUG) {
                Log.d(TAG, " getWallpaperObject() 不知道是什么格式的文件");
            }
            return null;
        }
    }
}
