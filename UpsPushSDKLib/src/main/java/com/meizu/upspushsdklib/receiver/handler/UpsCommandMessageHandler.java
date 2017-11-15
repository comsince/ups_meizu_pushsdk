package com.meizu.upspushsdklib.receiver.handler;


import android.content.Context;
import android.content.Intent;

import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsCommandMessageHandler extends AbstractUpsReceiverHandler<UpsCommandMessage>{

    public UpsCommandMessageHandler(Context context, UpsReceiverListener receiverListener) {
        super(context, receiverListener);
    }

    @Override
    public boolean messageMatch(Intent intent) {
        UpsLogger.i(this,"start UpsCommandMessageHandler");
        return UpsConstants.UPS_MEIZU_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction())
                && UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_COMMAND_RESULT.equals(getIntentMethod(intent));
    }

    @Override
    public String getProcessorName() {
        return UpsCommandMessageHandler.class.getSimpleName();
    }

    @Override
    public UpsCommandMessage getMessage(Intent intent) {
        return (UpsCommandMessage) intent.getParcelableExtra(UpsConstants.UPS_MEIZU_PUSH_EXTRA_UPS_MESSAGE);
    }

    @Override
    public void sendMessage(UpsCommandMessage message) {
        upsReceiverListener().onUpsCommandResult(context(),message);
    }
}
