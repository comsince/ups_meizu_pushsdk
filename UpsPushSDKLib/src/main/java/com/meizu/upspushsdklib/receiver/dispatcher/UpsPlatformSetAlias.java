package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;

import com.meizu.upspushsdklib.UpsCommandMessage;

class UpsPlatformSetAlias extends CommandMessageDispatcher<UpsPlatformSetAlias.UpsPlatformAliasMessage>{

    public UpsPlatformSetAlias(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public UpsPlatformAliasMessage upsPlatformMessage() {
        return null;
    }

    class UpsPlatformAliasMessage extends UpsPlatformMessage{

    }
}
