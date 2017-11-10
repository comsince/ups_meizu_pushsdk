package com.meizu.upspushsdklib.receiver.dispatcher;

import android.content.Context;

import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;


class UpsPlatformRegister extends CommandMessageDispatcher<RegisterStatus>{


    public UpsPlatformRegister(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public RegisterStatus upsPlatformMessage() {
        return null;
    }

}
