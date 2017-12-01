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

package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 处理小米相关接口
 * 反订阅接口由于没有相关Receiver回调,需要自己处理
 * */
public class XiaoMiHandler extends AbstractHandler{


    @Override
    public boolean isCurrentModel() {
        return UpsUtils.isXiaoMi();
    }

    @Override
    public String name() {
        return Company.XIAOMI.name();
    }

    @Override
    public void onRegister(Context context, String appId, String appKey) {
        if(TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)){
            UpsLogger.e(this,"xm appId or appKey not null");
        } else {
            MiPushClient.registerPush(context,appId,appKey);
        }
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
        //调用unregisterPush()之后，服务器不会向app发送任何消息
        //详情：https://dev.mi.com/console/doc/detail?pId=41#_2_0
        MiPushClient.unregisterPush(context);
    }

    @Override
    public void onSetAlias(Context context, String appId, String appKey, String alias) {
        MiPushClient.setAlias(context,alias,null);
    }

    @Override
    public void onUnsetAlias(Context context, String appId, String appKey, String alias) {
        MiPushClient.unsetAlias(context,alias,null);
    }

    @Override
    protected boolean dispatchToUpsReceiver(CommandType commandType) {
        boolean flag;
        switch (commandType){
            case UNREGISTER:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }
}
