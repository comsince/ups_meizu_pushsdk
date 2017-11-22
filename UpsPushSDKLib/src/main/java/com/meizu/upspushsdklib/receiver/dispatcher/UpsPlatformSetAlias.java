package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;

import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.impl.AbstractHandler;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsPlatformSetAlias extends CommandMessageDispatcher<SubAliasStatus>{

    public UpsPlatformSetAlias(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public SubAliasStatus upsPlatformMessage() {
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        ANResponse<String> anResponse = UpsPushAPI.setAlias(getUpsAppId(),getUpsAppKey(),
                upsCommandMessage.getCompany().code(),
                context.getPackageName(),
                getDeviceId(),
                getUpsPushId(),
                upsCommandMessage.getCommandResult());

        if(anResponse.isSuccess()){
            subAliasStatus = new SubAliasStatus(anResponse.getResult());
            upsCommandMessage.setMessage(subAliasStatus.getMessage());
            upsCommandMessage.setCode(Integer.parseInt(subAliasStatus.getCode()));
            upsCommandMessage.setCommandResult(subAliasStatus.getAlias());
        } else {
            upsCommandMessage.setCode(anResponse.getError().getErrorCode());
            upsCommandMessage.setMessage(anResponse.getError().getErrorBody());
            UpsLogger.e(this,"ups set alias error "+anResponse.getError());
        }
        return subAliasStatus;
    }


}
