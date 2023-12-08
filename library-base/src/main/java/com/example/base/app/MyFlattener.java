package com.example.base.app;

import android.os.Build;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.flattener.Flattener;
import com.elvishew.xlog.flattener.Flattener2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyFlattener implements Flattener, Flattener2 {
    @Override
    public CharSequence flatten(int logLevel, String tag, String message) {
        return flatten(System.currentTimeMillis(), logLevel, tag, message);
    }

    @Override
    public CharSequence flatten(long timeMillis, int logLevel, String tag, String message) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZone.getDefault(); //获取默认时区
        sdf.setTimeZone(timeZone); //设置时区

        String formattedDateTime = sdf.format(new Date(timeMillis)); //将时间戳转换为指定格式的字符串

        System.out.println("Formatted DateTime: " + formattedDateTime);
        return formattedDateTime.toString()
                + '|' + LogLevel.getLevelName(logLevel)
                + '|' + tag
                + '|' + message;
    }


}
