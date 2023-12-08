package com.example.base.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.elvishew.xlog.XLog;

import java.io.File;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.syncIsDebug(this);
        XLogConfig.init(AppUtils.isDebug(),getApplicationContext().getExternalCacheDir().toString());
        XLog.e("路径",getCacheDirectoryPath(this));
    }

    public static String getCacheDirectoryPath(Context context) {
        File cacheDir;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cacheDir = context.getCacheDir();
            //XLog.e("路径","getCacheDirectoryPath");

        } else {
            cacheDir = context.getExternalCacheDir();
           // XLog.e("路径","getExternalCacheDir");

        }
        //XLog.e("路径","cacheDir");


        if (cacheDir == null) {
            cacheDir = new File(context.getFilesDir(), "cache");
            cacheDir.mkdirs();
        }

        return cacheDir.getAbsolutePath();
    }
}
