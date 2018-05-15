package com.example.lijun.videolivewallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.lijun.videolivewallpaper.util.WallpaperUtil;

public class MainActivity extends AppCompatActivity {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_SET_WALLPAPER = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!WallpaperUtil.supportGlEs20(this)) {
//            Toast.makeText(this, "不支持openGL 2.0", Toast.LENGTH_SHORT).show();
//            return;
//        }
        WallpaperUtil.setLiveWallpaper(this, MainActivity.this, REQUEST_CODE_SET_WALLPAPER);
        if (WallpaperUtil.wallpaperIsUsed(this)) {
            if (DEBUG) {
                Log.d(TAG, " onCreate() 这个壁纸正在被使用");
            }
            Toast.makeText(this, "这款壁纸已经在使用了,不需要重复设置", Toast.LENGTH_SHORT).show();
        } else {
            if (DEBUG) {
                Log.d(TAG, " onCreate() 这个壁纸还没有被使用");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DEBUG) {
            Log.d(TAG, " onActivityResult() " + "requestCode = [" + requestCode + "], resultCode = [" + resultCode + "]");
        }
        if (requestCode == REQUEST_CODE_SET_WALLPAPER) {
            if (resultCode == RESULT_OK) {
                // TODO: 2017/3/13 设置动态壁纸成功
                Toast.makeText(this, "设置动态壁纸成功!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // TODO: 2017/3/13 取消设置动态壁纸
//                Toast.makeText(this, "设置动态壁纸失败!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
