package com.meizu.upspushsdklib;

import android.content.Context;
import android.content.Intent;

import com.meizu.cloud.pushsdk.base.IntentReceiver;
import com.meizu.upspushsdklib.receiver.handler.UpsReceiverHandlerProxy;
import com.meizu.upspushsdklib.receiver.handler.UpsReceiverListener;
import com.meizu.upspushsdklib.util.UpsLogger;


public abstract class UpsPushMessageReceiver extends IntentReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context,intent);
        } catch (Exception e){
            UpsLogger.e(this,"UpsPushMessageReceiver Event core error "+e.getMessage());
        }
    }

    @Override
    protected void onHandleIntent(Context context, Intent intent) {
        UpsReceiverHandlerProxy.with(context).registerListener(new UpsReceiverListener() {
            @Override
            public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onThroughMessage "+upsPushMessage);
                UpsPushMessageReceiver.this.onThroughMessage(context,upsPushMessage);
            }

            @Override
            public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onNotificationClicked "+upsPushMessage);
                UpsPushMessageReceiver.this.onNotificationClicked(context,upsPushMessage);
            }

            @Override
            public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onNotificationArrived "+upsPushMessage);
                UpsPushMessageReceiver.this.onNotificationArrived(context,upsPushMessage);
            }

            @Override
            public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onNotificationDeleted "+upsPushMessage);
                UpsPushMessageReceiver.this.onNotificationDeleted(context,upsPushMessage);
            }

            @Override
            public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onUpsCommandResult "+upsCommandMessage);
                UpsPushMessageReceiver.this.onUpsCommandResult(context,upsCommandMessage);
            }
        }).processMessage(intent);
    }

    /**
     * 透传消息
     * @param context
     * @param upsPushMessage
     * */
    public abstract void onThroughMessage(Context context,UpsPushMessage upsPushMessage);

    /**
     * 接收通知栏消息点击回调
     * @param upsPushMessage
     * @param context
     * */
    public abstract void onNotificationClicked(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收通知栏消息到达回调
     * @param upsPushMessage
     * @param context
     * */
    public abstract void onNotificationArrived(Context context, UpsPushMessage upsPushMessage);


    /**
     * 通知栏删除回调
     * @param upsPushMessage
     * @param context
     * */
    public abstract void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收订阅，反订阅，别名订阅，取消别名订阅回调
     * @param upsCommandMessage
     * @param context
     * */
    public abstract void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage);
}
