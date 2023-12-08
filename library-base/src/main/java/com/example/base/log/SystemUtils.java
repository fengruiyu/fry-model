package com.example.base.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SystemUtils {
    private SystemUtils() {
    }

    public static String getAppVersionName(Context context) {
        String appVersionName = "null";

        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            if (pm != null) {
                PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
                appVersionName = packageInfo.versionName;
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return appVersionName;
    }
}
