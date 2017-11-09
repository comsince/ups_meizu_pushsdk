package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;
import android.content.Intent;

import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageType;
import com.meizu.upspushsdklib.util.UpsConstants;

public class UpsPushMessageDispatcher {

    /**
     * 分发消息
     * 透传和通知栏相关回调事件只需要发送消息到UpsPushMessageReceiver,不需要与平台交互
     * @param context
     * @param upsPushMessage
     * @param upsPushMessageType 消息类型
     * */
    public static void dispatch(Context context,UpsPushMessage upsPushMessage, UpsPushMessageType upsPushMessageType){
        Intent intent = new Intent();
        intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_EXTRA_UPS_MESSAGE,upsPushMessage);
        switch (upsPushMessageType){
             case NOTIFICATION_ARRIVED:
                 intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD,UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_NOTIFICATION_ARRIVED);
                 break;
             case NOTIFICATION_CLICK:
                 intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD,UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_NOTIFICATION_CLICK);
                 break;
             case NOTIFICATION_DELETE:
                 intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD,UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_NOTIFICATION_DELETE);
                 break;
             case THROUGH_MESSAGE:
                 intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD,UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_THROUGH_MESSAGE);
                 break;
         }
        PlatformMessageSender.sendMessageFromBroadcast(context,intent,UpsConstants.UPS_MEIZU_PUSH_ON_MESSAGE_ACTION,context.getPackageName());
    }

}
