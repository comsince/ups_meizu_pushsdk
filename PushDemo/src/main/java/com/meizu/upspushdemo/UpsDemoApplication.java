package com.meizu.upspushdemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.meizu.cloud.pushinternal.DebugLogger;

import java.text.SimpleDateFormat;
import java.util.Date;


public class UpsDemoApplication extends Application {

    private static DemoHandler sHandler = null;
    private static MainActivity sMainActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        DebugLogger.initDebugLogger(this);
        if (sHandler == null) {
            sHandler = new DemoHandler(getApplicationContext());
        }
    }

    public static DemoHandler getHandler() {
        return sHandler;
    }

    public static void setMainActivity(MainActivity activity) {
        sMainActivity = activity;
    }

    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            MainActivity.logList.add(0, getSimpleDate() + " " + s);
            if (sMainActivity != null) {
                sMainActivity.refreshLogInfo();
            }
            if (!TextUtils.isEmpty(s)) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }

        @SuppressLint("SimpleDateFormat")
        private static String getSimpleDate() {
            return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
        }
    }
}
