package com.meizu.upspushdemo;


import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageReceiver;
import com.meizu.upspushsdklib.util.UpsLogger;


public class UpsReceiver extends UpsPushMessageReceiver{
    @Override
    public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {
         sendMessage("onThroughMessage "+upsPushMessage.getContent());
    }

    @Override
    public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationClicked "+upsPushMessage);
    }

    @Override
    public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationArrived "+upsPushMessage);
    }

    @Override
    public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationDeleted "+upsPushMessage);
    }

    @Override
    public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
        UpsLogger.i(this,"UpsReceiver "+upsCommandMessage);
        switch (upsCommandMessage.getCompany()){
            case HUAWEI:
                Bundle bundle = (Bundle) upsCommandMessage.getExtra();
                String belongId = bundle.getString("belongId");
                UpsLogger.i(this,"hw belongId "+belongId);
                break;
            default:
        }

        sendMessage("onUpsCommandResult commandResult "+upsCommandMessage.getCommandResult());
    }


    private void sendMessage(String log){
        Message msg = Message.obtain();
        msg.obj = log;
        UpsDemoApplication.getHandler().sendMessage(msg);
    }
}
