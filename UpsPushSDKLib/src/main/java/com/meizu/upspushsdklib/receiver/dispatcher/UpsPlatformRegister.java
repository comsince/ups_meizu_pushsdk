/*
 * MIT License
 *
 * Copyright (c) [2017] [Meizu.inc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.meizu.upspushsdklib.receiver.dispatcher;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.impl.AbstractHandler;
import com.meizu.upspushsdklib.network.Response;
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

            Response<String> response = UpsPushAPI.register0(getUpsAppId(),getUpsAppKey(),
                    upsCommandMessage.getCompany().code(),
                    context.getPackageName(),
                    getDeviceId(),
                    upsCommandMessage.getCommandResult());
            UpsLogger.e(this,"web response "+response.getResponseMessage());

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
