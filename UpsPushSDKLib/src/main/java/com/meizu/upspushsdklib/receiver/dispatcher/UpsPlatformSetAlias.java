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

import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.network.Response;
import com.meizu.upspushsdklib.util.UpsLogger;

class UpsPlatformSetAlias extends CommandMessageDispatcher<SubAliasStatus>{

    public UpsPlatformSetAlias(Context context, UpsCommandMessage upsCommandMessage) {
        super(context, upsCommandMessage);
    }

    @Override
    public SubAliasStatus upsPlatformMessage() {
        SubAliasStatus subAliasStatus = new SubAliasStatus();
//        ANResponse<String> anResponse = UpsPushAPI.setAlias(getUpsAppId(),getUpsAppKey(),
//                upsCommandMessage.getCompany().code(),
//                context.getPackageName(),
//                getDeviceId(),
//                getCompanyToken(),
//                upsCommandMessage.getCommandResult());

        Response<String> response = UpsPushAPI.setAlias0(getUpsAppId(),getUpsAppKey(),
                upsCommandMessage.getCompany().code(),
                context.getPackageName(),
                getDeviceId(),
                getCompanyToken(),
                upsCommandMessage.getCommandResult());

        if(response.isSuccess()){
            subAliasStatus = new SubAliasStatus(response.getBody());
            upsCommandMessage.setMessage(subAliasStatus.getMessage());
            upsCommandMessage.setCode(Integer.parseInt(subAliasStatus.getCode()));
            upsCommandMessage.setCommandResult(Boolean.toString("200".equals(subAliasStatus.getCode())));
        } else {
            upsCommandMessage.setCode(response.getStatusCode());
            upsCommandMessage.setMessage(response.getErrorBody().toString());
            UpsLogger.e(this,"ups set alias error "+response.getErrorBody());
        }
        return subAliasStatus;
    }


}
