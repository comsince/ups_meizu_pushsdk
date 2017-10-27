package com.meizu.upspushsdklib.receiver;


import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.upspushsdklib.UpsPushManager;

public final class HwUpsPushMessageReceiver extends PushReceiver{
    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        DebugLogger.i(UpsPushManager.TAG, "belongId为:" + belongId);
        DebugLogger.i(UpsPushManager.TAG, "Token为:" + token);

    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            //CP可以自己解析消息内容，然后做相应的处理
            String content = new String(msg, "UTF-8");
            DebugLogger.i(UpsPushManager.TAG, "收到PUSH透传消息,消息内容为:" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            DebugLogger.i(UpsPushManager.TAG, "收到通知栏消息点击事件,notifyId:" + notifyId);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
        }

        String message = extras.getString(BOUND_KEY.pushMsgKey);
        super.onEvent(context, event, extras);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        DebugLogger.i(UpsPushManager.TAG, "Push连接状态为:" + pushState);

    }
}
