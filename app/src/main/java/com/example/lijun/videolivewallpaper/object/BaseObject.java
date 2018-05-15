package com.example.lijun.videolivewallpaper.object;

import android.view.SurfaceHolder;

public abstract class BaseObject {
    protected SurfaceHolder mSurfaceHolder;

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
    }

    public abstract void onVisibilityChanged(boolean visible);

    public abstract void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height);

    public abstract void onDestroy();
}
