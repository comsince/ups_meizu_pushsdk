package com.meizu.upspushdemo;


import android.content.Context;

import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageReceiver;
import com.meizu.upspushsdklib.util.UpsLogger;

public class UpsReceiver extends UpsPushMessageReceiver{
    @Override
    public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
        UpsLogger.i(this,"UpsReceiver "+upsCommandMessage);
    }
}
