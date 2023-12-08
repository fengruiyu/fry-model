package com.example.base.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class Logcat {
    private static String TAG = "logcat";
    private static String mVersionName = "null";
    private static final String VERSION_LOG = "V1.0.0";
    private static final int MAX_ELEMENT_LEN = 4;
    private static final int MAX_ELEMENT_INDEX = 3;
    private static volatile boolean mConfigured = false;
    private static volatile boolean isVerbose = false;
    private static volatile boolean isDebug = false;
    private static volatile boolean isInfo = false;
    private static volatile boolean isWarn = true;
    private static volatile boolean isError = true;

    private Logcat() {
    }

    public static void config(Context context, String tag, LogLevel level) {
        config(SystemUtils.getAppVersionName(context), tag, level);
    }

    public static synchronized void config(String version, String tag, LogLevel level) {
        boolean isUseInDebug = isUseInDebug();
        String levelValue = PlatformProperty.get("persist.logcat.level", "");
        LogLevel minLevel = recognizeLevel(isUseInDebug, levelValue, level);
        isInfo = LogLevel.I.compare(minLevel) >= 0;
        isDebug = LogLevel.D.compare(minLevel) >= 0;
        isVerbose = LogLevel.V.compare(minLevel) >= 0;
        isWarn = LogLevel.W.compare(minLevel) >= 0;
        isError = LogLevel.E.compare(minLevel) >= 0;
        mVersionName = TextUtils.isEmpty(version) ? "null" : version;
        TAG = String.format("%s[%s]", tag, mVersionName);
        mConfigured = true;
        Log.d(TAG, String.format("LogVer: %s, minLevel: %s, isUseInDebug: %b, levelValue: %s", "V1.0.8", minLevel, isUseInDebug, levelValue));
    }

    private static LogLevel recognizeLevel(boolean isUseInDebug, String levelValue, LogLevel level) {
        LogLevel minLevel;
        if (TextUtils.isEmpty(levelValue)) {
            if (isUseInDebug) {
                minLevel = level.compare(LogLevel.OFF) == 0 ? LogLevel.OFF : LogLevel.ON;
            } else {
                minLevel = level;
            }
        } else {
            minLevel = LogLevel.valueOf(levelValue, LogLevel.W);
        }

        return minLevel;
    }

    public static boolean isVerbose() {
        return isVerbose;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static boolean isInfo() {
        return isInfo;
    }

    public static boolean isWarn() {
        return isWarn;
    }

    public static boolean isError() {
        return isError;
    }

    public static boolean isConfigured() {
        return mConfigured;
    }

    private static boolean isMaxElementCount(StackTraceElement[] elements) {
        return elements != null && elements.length >= 4;
    }

    private static String emptyMessage() {
        Thread th = Thread.currentThread();
        return String.format("[%s(%d)]", th.getName(), th.getId());
    }

    private static String singleMessage(String msg) {
        Thread th = Thread.currentThread();
        return String.format("[%s(%d)], %s", th.getName(), th.getId(), msg);
    }

    private static String concatTag(String tag) {
        return String.format("%s[%s]", tag, mVersionName);
    }

    public static void i() {
        if (isInfo) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.i(TAG, generateMsg(elements[3]));
            } else {
                Log.i(TAG, emptyMessage());
            }
        }

    }

    public static void i(String msg) {
        if (isInfo) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.i(TAG, generateMsg(elements[3], msg));
            } else {
                Log.i(TAG, singleMessage(msg));
            }
        }

    }

    public static void i(Object... msg) {
        if (isInfo) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.i(TAG, generateMsg(elements[3], msg));
            } else {
                Log.i(TAG, generateMsg(msg));
            }
        }

    }

    public static void i(String msg, Throwable tr) {
        if (isInfo) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.i(TAG, generateMsg(elements[3], msg), tr);
            } else {
                Log.i(TAG, singleMessage(msg), tr);
            }
        }

    }

    public static void iTag(String tag, String msg) {
        if (isInfo) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }

            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.i(concatTag(tag), generateMsg(elements[3], msg));
            } else {
                Log.i(concatTag(tag), singleMessage(msg));
            }
        }

    }

    public static void d() {
        if (isDebug) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.d(TAG, generateMsg(elements[3]));
            } else {
                Log.d(TAG, emptyMessage());
            }
        }

    }

    public static void d(String msg) {
        if (isDebug) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.d(TAG, generateMsg(elements[3], msg));
            } else {
                Log.d(TAG, singleMessage(msg));
            }
        }

    }

    public static void df(String msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.d(TAG, generateMsg(elements[3], msg));
        } else {
            Log.d(TAG, singleMessage(msg));
        }

    }

    public static void d(String msg, Throwable tr) {
        if (isDebug) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.d(TAG, generateMsg(elements[3], msg), tr);
            } else {
                Log.d(TAG, singleMessage(msg));
            }
        }

    }

    public static void df(String msg, Throwable tr) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.d(TAG, generateMsg(elements[3], msg), tr);
        } else {
            Log.d(TAG, singleMessage(msg));
        }

    }

    public static void d(Object... msg) {
        if (isDebug) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.d(TAG, generateMsg(elements[3], msg));
            } else {
                Log.d(TAG, generateMsg(msg));
            }
        }

    }

    public static void df(Object... msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.d(TAG, generateMsg(elements[3], msg));
        } else {
            Log.d(TAG, generateMsg(msg));
        }

    }

    public static void dTag(String tag, String msg) {
        if (isDebug) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }

            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.d(concatTag(tag), generateMsg(elements[3], msg));
            } else {
                Log.d(concatTag(tag), singleMessage(msg));
            }
        }

    }

    public static void dfTag(String tag, String msg) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.d(concatTag(tag), generateMsg(elements[3], msg));
        } else {
            Log.d(concatTag(tag), singleMessage(msg));
        }

    }

    public static void v() {
        if (isVerbose) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.v(TAG, generateMsg(elements[3]));
            } else {
                Log.v(TAG, emptyMessage());
            }
        }

    }

    public static void vf() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.v(TAG, generateMsg(elements[3]));
        } else {
            Log.v(TAG, emptyMessage());
        }

    }

    public static void v(String msg) {
        if (isVerbose) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.v(TAG, generateMsg(elements[3], msg));
            } else {
                Log.v(TAG, singleMessage(msg));
            }
        }

    }

    public static void vf(String msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.v(TAG, generateMsg(elements[3], msg));
        } else {
            Log.v(TAG, singleMessage(msg));
        }

    }

    public static void v(String msg, Throwable tr) {
        if (isVerbose) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.v(TAG, generateMsg(elements[3], msg), tr);
            } else {
                Log.v(TAG, singleMessage(msg), tr);
            }
        }

    }

    public static void vf(String msg, Throwable tr) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.v(TAG, generateMsg(elements[3], msg), tr);
        } else {
            Log.v(TAG, singleMessage(msg), tr);
        }

    }

    public static void v(Object... msg) {
        if (isVerbose) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.v(TAG, generateMsg(elements[3], msg));
            } else {
                Log.v(TAG, generateMsg(msg));
            }
        }

    }

    public static void vf(Object... msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.v(TAG, generateMsg(elements[3], msg));
        } else {
            Log.v(TAG, generateMsg(msg));
        }

    }

    public static void vTag(String tag, String msg) {
        if (isVerbose) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }

            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.v(concatTag(tag), generateMsg(elements[3], msg));
            } else {
                Log.v(concatTag(tag), singleMessage(msg));
            }
        }

    }

    public static void vfTag(String tag, String msg) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.v(concatTag(tag), generateMsg(elements[3], msg));
        } else {
            Log.v(concatTag(tag), singleMessage(msg));
        }

    }

    public static void w() {
        if (isWarn) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.w(TAG, generateMsg(elements[3]));
            } else {
                Log.w(TAG, emptyMessage());
            }
        }

    }

    public static void wf() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.w(TAG, generateMsg(elements[3]));
        } else {
            Log.w(TAG, emptyMessage());
        }

    }

    public static void w(String msg) {
        if (isWarn) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.w(TAG, generateMsg(elements[3], msg));
            } else {
                Log.w(TAG, singleMessage(msg));
            }
        }

    }

    public static void wf(String msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.w(TAG, generateMsg(elements[3], msg));
        } else {
            Log.w(TAG, singleMessage(msg));
        }

    }

    public static void w(String msg, Throwable tr) {
        if (isWarn) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.w(TAG, generateMsg(elements[3], msg), tr);
            } else {
                Log.w(TAG, singleMessage(msg), tr);
            }
        }

    }

    public static void wf(String msg, Throwable tr) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.w(TAG, generateMsg(elements[3], msg), tr);
        } else {
            Log.w(TAG, singleMessage(msg), tr);
        }

    }

    public static void w(Object... msg) {
        if (isWarn) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.w(TAG, generateMsg(elements[3], msg));
            } else {
                Log.w(TAG, generateMsg(msg));
            }
        }

    }

    public static void wf(Object... msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.w(TAG, generateMsg(elements[3], msg));
        } else {
            Log.w(TAG, generateMsg(msg));
        }

    }

    public static void wTag(String tag, String msg) {
        if (isWarn) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }

            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.w(concatTag(tag), generateMsg(elements[3], msg));
            } else {
                Log.w(concatTag(tag), singleMessage(msg));
            }
        }

    }

    public static void wfTag(String tag, String msg) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.w(concatTag(tag), generateMsg(elements[3], msg));
        } else {
            Log.w(concatTag(tag), singleMessage(msg));
        }

    }

    public static void e() {
        if (isError) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.e(TAG, generateMsg(elements[3]));
            } else {
                Log.e(TAG, emptyMessage());
            }
        }

    }

    public static void ef() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.e(TAG, generateMsg(elements[3]));
        } else {
            Log.e(TAG, emptyMessage());
        }

    }

    public static void e(String msg) {
        if (isError) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.e(TAG, generateMsg(elements[3], msg));
            } else {
                Log.e(TAG, singleMessage(msg));
            }
        }

    }

    public static void ef(String msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.e(TAG, generateMsg(elements[3], msg));
        } else {
            Log.e(TAG, singleMessage(msg));
        }

    }

    public static void e(String msg, Throwable tr) {
        if (isError) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.e(TAG, generateMsg(elements[3], msg), tr);
            } else {
                Log.e(TAG, singleMessage(msg), tr);
            }
        }

    }

    public static void ef(String msg, Throwable tr) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.e(TAG, generateMsg(elements[3], msg), tr);
        } else {
            Log.e(TAG, singleMessage(msg), tr);
        }

    }

    public static void e(Object... msg) {
        if (isError) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.e(TAG, generateMsg(elements[3], msg));
            } else {
                Log.e(TAG, generateMsg(msg));
            }
        }

    }

    public static void ef(Object... msg) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.e(TAG, generateMsg(elements[3], msg));
        } else {
            Log.e(TAG, generateMsg(msg));
        }

    }

    public static void eTag(String tag, String msg) {
        if (isError) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }

            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (isMaxElementCount(elements)) {
                Log.e(concatTag(tag), generateMsg(elements[3], msg));
            } else {
                Log.e(concatTag(tag), singleMessage(msg));
            }
        }

    }

    public static void efTag(String tag, String msg) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (isMaxElementCount(elements)) {
            Log.e(concatTag(tag), generateMsg(elements[3], msg));
        } else {
            Log.e(concatTag(tag), singleMessage(msg));
        }

    }

    private static boolean isUseInDebug() {
        String buildType = PlatformProperty.get("ro.build.type", "userdebug");
        return "userdebug".equals(buildType);
    }

    private static String generateMsg(Object... msg) {
        int length = msg == null ? 0 : msg.length;
        if (length <= 0) {
            return emptyMessage();
        } else if (length == 1 && msg[0] == null) {
            return emptyMessage();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.ensureCapacity(100);
            sb.append(emptyMessage()).append(", ");
            Object[] var3 = msg;
            int var4 = msg.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object log = var3[var5];
                if (log instanceof Throwable) {
                    sb.append(Log.getStackTraceString((Throwable)log));
                } else {
                    sb.append(log);
                }
            }

            return sb.toString();
        }
    }

    private static String generateMsg(StackTraceElement elements, Object... msg) {
        StringBuilder sb = new StringBuilder();
        sb.ensureCapacity(100);
        sb.append(elements.getFileName()).append('(').append(elements.getLineNumber()).append("): ").append(elements.getMethodName());
        sb.append(": ");
        sb.append(emptyMessage()).append(", ");
        if (msg != null && msg.length > 0) {
            Object[] var3 = msg;
            int var4 = msg.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object log = var3[var5];
                if (log instanceof Throwable) {
                    sb.append(Log.getStackTraceString((Throwable)log));
                } else {
                    sb.append(log);
                }

                sb.append(' ');
            }
        }

        return sb.toString();
    }

    private static String generateMsg(StackTraceElement elements, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.ensureCapacity(100);
        sb.append(elements.getFileName()).append('(').append(elements.getLineNumber()).append("): ").append(elements.getMethodName()).append(": ");
        sb.append(emptyMessage()).append(", ");
        if (msg != null && msg.length() > 0) {
            sb.append(msg);
        }

        return sb.toString();
    }
}
