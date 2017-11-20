package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;

import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.impl.AbstractHandler;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsPlatformUnSetAlias extends CommandMessageDispatcher<SubAliasStatus>{

    public UpsPlatformUnSetAlias(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public SubAliasStatus upsPlatformMessage() {
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        String upsPushId = AbstractHandler.getUpsPushId(context);
        ANResponse<String> anResponse = UpsPushAPI.unSetAlias(getUpsAppId(),getUpsAppKey(),
                upsCommandMessage.getCompany().code(),
                context.getPackageName(),
                MzSystemUtils.getDeviceId(context),
                upsPushId);

        if(anResponse.isSuccess()){
            subAliasStatus = new SubAliasStatus(anResponse.getResult());
        } else {
            UpsLogger.e(this,"ups unset alias error "+anResponse.getError());
        }
        return subAliasStatus;
    }
}
