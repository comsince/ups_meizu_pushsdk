package com.meizu.upspushsdklib.receiver.handler;


import android.content.Context;

import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;

public interface UpsReceiverListener {
    /**
     * 透传消息
     * @param context
     * @param upsPushMessage
     * */
    void onThroughMessage(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收通知栏消息点击回调
     * @param upsPushMessage
     * @param context
     * */
    void onNotificationClicked(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收通知栏消息到达回调
     * @param upsPushMessage
     * @param context
     * */
    void onNotificationArrived(Context context, UpsPushMessage upsPushMessage);


    /**
     * 通知栏删除回调
     * @param upsPushMessage
     * @param context
     * */
    void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收订阅，反订阅，别名订阅，取消别名订阅回调
     * @param upsCommandMessage
     * @param context
     * */
    void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage);
}
