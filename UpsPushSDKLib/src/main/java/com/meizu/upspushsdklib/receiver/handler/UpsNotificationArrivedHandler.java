package com.meizu.upspushsdklib.receiver.handler;


import android.content.Context;
import android.content.Intent;

import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;

public class UpsNotificationArrivedHandler extends AbstractUpsReceiverHandler<UpsPushMessage>{

    public UpsNotificationArrivedHandler(Context context, UpsReceiverListener receiverListener) {
        super(context, receiverListener);
    }

    @Override
    public boolean messageMatch(Intent intent) {
        UpsLogger.i(this,"start UpsNotificationArrivedHandler");
        return UpsConstants.UPS_MEIZU_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction())
                && UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_NOTIFICATION_ARRIVED.equals(getIntentMethod(intent));
    }

    @Override
    public String getProcessorName() {
        return UpsNotificationArrivedHandler.class.getSimpleName();
    }

    @Override
    public UpsPushMessage getMessage(Intent intent) {
        return intent.getParcelableExtra(UpsConstants.UPS_MEIZU_PUSH_EXTRA_UPS_MESSAGE);
    }

    @Override
    public void sendMessage(UpsPushMessage message) {
        upsReceiverListener().onNotificationArrived(context(),message);
    }
}
