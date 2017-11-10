package com.meizu.upspushsdklib.receiver.dispatcher;

import android.content.Context;

import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;

class UpsPlatformUnRegister extends CommandMessageDispatcher<UnRegisterStatus>{

    public UpsPlatformUnRegister(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public UnRegisterStatus upsPlatformMessage() {
        return null;
    }
}
