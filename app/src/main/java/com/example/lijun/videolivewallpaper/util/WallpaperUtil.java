package com.example.lijun.videolivewallpaper.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import com.example.lijun.videolivewallpaper.wallpaper.VideoWallpaperService;

/**
 * author: lijun
 * time: 2018/5/5 11:30
 * describe:
 */
public class WallpaperUtil {
    /**
     * 跳转到系统设置壁纸界面
     *
     * @param context
     * @param paramActivity
     */
    public static void setLiveWallpaper(Context context, Activity paramActivity, int requestCode) {
        try {
            Intent localIntent = new Intent();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {//ICE_CREAM_SANDWICH_MR1  15
                //android.service.wallpaper.CHANGE_LIVE_WALLPAPER
                localIntent.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
                //android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT
                localIntent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT"
                        , new ComponentName(context.getApplicationContext().getPackageName()
                                , VideoWallpaperService.class.getCanonicalName()));
            } else {
                localIntent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");//android.service.wallpaper.LIVE_WALLPAPER_CHOOSER
            }
            paramActivity.startActivityForResult(localIntent, requestCode);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static boolean supportGlEs20(Activity activity) {
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(
                Context.ACTIVITY_SERVICE);
        return activityManager.getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
    }

    /**
     * 判断是否是使用我们的壁纸
     *
     * @param paramContext
     * @return
     */
    public static boolean wallpaperIsUsed(Context paramContext) {
        WallpaperInfo localWallpaperInfo = WallpaperManager.getInstance(paramContext).getWallpaperInfo();
        return ((localWallpaperInfo != null) && (localWallpaperInfo.getPackageName().equals(paramContext.getPackageName())) &&
                (localWallpaperInfo.getServiceName().equals(VideoWallpaperService.class.getCanonicalName())));
    }

    public static Bitmap getDefaultWallpaper(Context paramContext) {
        Bitmap localBitmap;
        if (isLivingWallpaper(paramContext))
            localBitmap = null;
        do {
            localBitmap = ((BitmapDrawable) WallpaperManager.getInstance(paramContext).getDrawable()).getBitmap();
            return localBitmap;
        }
        while (localBitmap != null);
    }

    public static boolean isLivingWallpaper(Context paramContext) {
        return (WallpaperManager.getInstance(paramContext).getWallpaperInfo() != null);
    }
}
