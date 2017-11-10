package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;

import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;

class UpsPlatformSetAlias extends CommandMessageDispatcher<SubAliasStatus>{

    public UpsPlatformSetAlias(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public SubAliasStatus upsPlatformMessage() {
        return null;
    }


}
