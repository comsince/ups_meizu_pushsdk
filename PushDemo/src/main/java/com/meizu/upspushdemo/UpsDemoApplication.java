package com.meizu.upspushdemo;

import android.app.Application;

import com.meizu.cloud.pushinternal.DebugLogger;

/**
 * Created by liaojinlong on 17-10-27.
 */

public class UpsDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DebugLogger.initDebugLogger(this);
    }
}
