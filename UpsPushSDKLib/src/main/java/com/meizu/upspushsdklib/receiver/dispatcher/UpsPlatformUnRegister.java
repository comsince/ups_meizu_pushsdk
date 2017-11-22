package com.meizu.upspushsdklib.receiver.dispatcher;

import android.content.Context;

import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.impl.AbstractHandler;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsPlatformUnRegister extends CommandMessageDispatcher<UnRegisterStatus>{

    public UpsPlatformUnRegister(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public UnRegisterStatus upsPlatformMessage() {
        UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
        ANResponse<String> anResponse = UpsPushAPI.unRegister(getUpsAppId(),
                getUpsAppKey(),
                upsCommandMessage.getCompany().code(),
                context.getPackageName(),
                getDeviceId());
        if(anResponse.isSuccess()){
            unRegisterStatus = new UnRegisterStatus(anResponse.getResult());
            upsCommandMessage.setCode(Integer.valueOf(unRegisterStatus.getCode()));
            upsCommandMessage.setMessage(unRegisterStatus.getMessage());
            AbstractHandler.putUpsExpireTime(context,0);
            AbstractHandler.putUpsPushId(context,"");
        } else {
            upsCommandMessage.setCode(anResponse.getError().getErrorCode());
            upsCommandMessage.setMessage(anResponse.getError().getErrorBody());
            UpsLogger.e(this,"ups unregister fail "+anResponse.getError());
        }
        return unRegisterStatus;
    }
}
