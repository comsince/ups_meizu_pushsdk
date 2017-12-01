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
