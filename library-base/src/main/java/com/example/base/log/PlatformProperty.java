package com.example.base.log;

import android.text.TextUtils;

import java.lang.reflect.Method;

public class PlatformProperty {
    public PlatformProperty() {
    }

    public static String get(String key, String def) {
        String platform = "";

        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            platform = (String)getMethod.invoke(classType, key);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return TextUtils.isEmpty(platform) ? def : platform;
    }
}
