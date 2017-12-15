package com.pujitech.commonhttplibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import com.pujitech.commonhttplibrary.BuildConfig;


/**
 * 日志工具类
 *
 * @author sugarzhang
 */
public class LogUtils {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TAG = "LogUtils";

    public static void v(String tag, String log) {
        if (DEBUG) {
            Log.v(TextUtils.isEmpty(tag) ? TAG : tag, log);
        }
    }

    public static void d(String tag, String log) {
        if (DEBUG) {
            Log.d(TextUtils.isEmpty(tag) ? TAG : tag, log);
        }
    }

    public static void i(String tag, String log) {
        if (DEBUG) {
            Log.i(TextUtils.isEmpty(tag) ? TAG : tag, log);
        }
    }

    public static void w(String tag, String log) {
        if (DEBUG) {
            Log.w(TextUtils.isEmpty(tag) ? TAG : tag, log);
        }
    }

    public static void e(String tag, String log) {
        if (DEBUG) {
            Log.e(TextUtils.isEmpty(tag) ? TAG : tag, log);
        }
    }
}
