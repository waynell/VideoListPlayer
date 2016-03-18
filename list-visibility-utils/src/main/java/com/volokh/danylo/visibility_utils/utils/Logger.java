package com.volokh.danylo.visibility_utils.utils;


import android.util.Log;

public class Logger {

    public static int err(final String TAG, final String message) {
        return Log.e(TAG, message);
    }

    public static int err(final String TAG, final String message, Throwable throwable) {
        return Log.e(TAG, message, throwable);
    }

    public static int w(final String TAG, final String message) {
        return Log.w(TAG, message);
    }

    public static int inf(final String TAG, final String message) {
        return Log.i(TAG, message);
    }

    public static int d(final String TAG, final String message) {
        return Log.d(TAG, message);
    }

    public static int v(final String TAG, final String message) {
        return Log.v(TAG, message);
    }
}
