package com.meizu.upspushsdklib.receiver.handler;

import android.content.Context;
import android.content.Intent;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsNotificationClickHandler extends AbstractUpsReceiverHandler<UpsPushMessage>{


    public UpsNotificationClickHandler(Context context, UpsReceiverListener receiverListener) {
        super(context, receiverListener);
    }

    @Override
    public UpsPushMessage getMessage(Intent intent) {
        return (UpsPushMessage) intent.getSerializableExtra(UpsConstants.UPS_MEIZU_PUSH_EXTRA_UPS_MESSAGE);
    }

    @Override
    public void sendMessage(UpsPushMessage message) {
        upsReceiverListener().onNotificationClicked(context(),message);
    }

    @Override
    public boolean messageMatch(Intent intent) {
        UpsLogger.e(this,"start UpsNotificationClickHandler");
        return UpsConstants.UPS_MEIZU_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction())
                && UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_NOTIFICATION_CLICK.equals(getIntentMethod(intent));
    }

    @Override
    public String getProcessorName() {
        return UpsNotificationClickHandler.class.getSimpleName();
    }
}
