package com.meizu.upspushsdklib.receiver.dispatcher;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.impl.AbstractHandler;
import com.meizu.upspushsdklib.util.UpsConstantCode;
import com.meizu.upspushsdklib.util.UpsLogger;


class UpsPlatformRegister extends CommandMessageDispatcher<RegisterStatus>{


    public UpsPlatformRegister(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public RegisterStatus upsPlatformMessage() {
        RegisterStatus registerStatus = new RegisterStatus();
        int expireTime = AbstractHandler.getUpsExpireTime(context);
        String upsPushId = AbstractHandler.getUpsPushId(context);
        boolean flag = !TextUtils.isEmpty(upsPushId) && System.currentTimeMillis()/1000 < expireTime;
        if(!flag){
            UpsLogger.e(this,"retry register ups pushId ");

            ANResponse<String> anResponse = UpsPushAPI.register(getUpsAppId(),getUpsAppKey(),
                    upsCommandMessage.getCompany().code(),
                    context.getPackageName(),
                    getDeviceId(),
                    upsCommandMessage.getCommandResult());
            if(anResponse.isSuccess()){
                registerStatus = new RegisterStatus(anResponse.getResult());
                UpsLogger.e(this,"platform register status "+registerStatus);
                upsCommandMessage.setCommandResult(registerStatus.getPushId());
                upsCommandMessage.setCode(Integer.valueOf(registerStatus.getCode()));
                upsCommandMessage.setMessage(registerStatus.getMessage());
                AbstractHandler.putUpsPushId(context,registerStatus.getPushId());
                AbstractHandler.putUpsExpireTime(context,registerStatus.getExpireTime()+(int) (System.currentTimeMillis()/1000));
            } else {
                UpsLogger.e(this,"platform register error "+anResponse.getError());
                upsCommandMessage.setCode(anResponse.getError().getErrorCode());
                upsCommandMessage.setMessage(anResponse.getError().getErrorBody());
            }
        } else {
            upsCommandMessage.setCode(UpsConstantCode.SUCCESS);
            upsCommandMessage.setCommandResult(upsPushId);
            upsCommandMessage.setMessage("dont register frequently");
        }

        return registerStatus;
    }



}
