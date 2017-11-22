package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;

import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsPlatformUnSetAlias extends CommandMessageDispatcher<SubAliasStatus>{

    public UpsPlatformUnSetAlias(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public SubAliasStatus upsPlatformMessage() {
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        ANResponse<String> anResponse = UpsPushAPI.unSetAlias(getUpsAppId(),getUpsAppKey(),
                upsCommandMessage.getCompany().code(),
                context.getPackageName(),
                getDeviceId(),
                getUpsPushId());

        if(anResponse.isSuccess()){
            subAliasStatus = new SubAliasStatus(anResponse.getResult());
            upsCommandMessage.setCode(Integer.parseInt(subAliasStatus.getCode()));
            upsCommandMessage.setMessage(subAliasStatus.getMessage());
            upsCommandMessage.setCommandResult(Boolean.toString(true));
        } else {
            upsCommandMessage.setCode(anResponse.getError().getErrorCode());
            upsCommandMessage.setMessage(anResponse.getError().getErrorBody());
            UpsLogger.e(this,"ups unset alias error "+anResponse.getError());
        }
        return subAliasStatus;
    }
}
