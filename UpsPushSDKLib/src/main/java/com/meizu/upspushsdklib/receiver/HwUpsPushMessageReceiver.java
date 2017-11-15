package com.meizu.upspushsdklib.receiver;


import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.support.api.push.PushReceiver;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.PushType;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageType;
import com.meizu.upspushsdklib.receiver.dispatcher.CommandMessageDispatcher;
import com.meizu.upspushsdklib.receiver.dispatcher.UpsPushMessageDispatcher;
import com.meizu.upspushsdklib.util.UpsConstantCode;
import com.meizu.upspushsdklib.util.UpsLogger;

public final class HwUpsPushMessageReceiver extends PushReceiver{
    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        UpsLogger.i(UpsPushManager.TAG, "belongId为:" + belongId);
        UpsLogger.i(UpsPushManager.TAG, "Token为:" + token);

        UpsCommandMessage upsCommandMessage = UpsCommandMessage.builder()
                .code(UpsConstantCode.SUCCESS)
                .company(Company.HUAWEI)
                .commandResult(token)
                .commandType(CommandType.REGISTER)
                .extra(extras)
                .build();

        CommandMessageDispatcher.create(context,upsCommandMessage).dispatch();

    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            //CP可以自己解析消息内容，然后做相应的处理
            String content = new String(msg, "UTF-8");
            UpsLogger.i(UpsPushManager.TAG, "收到PUSH透传消息,消息内容为:" + content);
            UpsPushMessageDispatcher.dispatch(context, UpsPushMessage.builder()
                            .content(content)
                            .company(Company.HUAWEI)
                            .extra(bundle)
                            .pushType(PushType.THROUGH_MESSAGE)
                            .build(),
                    UpsPushMessageType.THROUGH_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        UpsLogger.i(this,"onNotification event "+event);
        //只有带自定义参数时，此方法才回调
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            String message = extras.getString(BOUND_KEY.pushMsgKey);

            UpsLogger.i(UpsPushManager.TAG, "收到通知栏消息点击事件,notifyId:" + notifyId);
            UpsPushMessageDispatcher.dispatch(context, UpsPushMessage.builder()
                            .content(message)
                            .noifyId(notifyId)
                            .company(Company.HUAWEI)
                            .extra(extras)
                            .pushType(PushType.NOTIFICATION_MESSAGE)
                            .build(),
                    UpsPushMessageType.NOTIFICATION_CLICK);
        }

        super.onEvent(context, event, extras);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        UpsLogger.i(UpsPushManager.TAG, "Push连接状态为:" + pushState);

    }
}
