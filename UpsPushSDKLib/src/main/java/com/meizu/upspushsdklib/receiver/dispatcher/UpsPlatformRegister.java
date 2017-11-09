package com.meizu.upspushsdklib.receiver.dispatcher;

import android.content.Context;

import com.meizu.upspushsdklib.UpsCommandMessage;


class UpsPlatformRegister extends CommandMessageDispatcher<UpsPlatformRegister.UpsPlatformRegisterMessage>{


    public UpsPlatformRegister(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public UpsPlatformRegisterMessage upsPlatformMessage() {
        return null;
    }


    class UpsPlatformRegisterMessage extends UpsPlatformMessage{

    }
}
