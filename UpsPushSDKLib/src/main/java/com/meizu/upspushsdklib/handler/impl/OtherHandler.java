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
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.pushservice.MzPushService;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;

/**
 * 支持非flyme手机的推送， 使用魅族自有通道
 */

public class OtherHandler extends MeizuHandler {


    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
        String connectId = null;
        startPushService(ctx.pipeline().context(),connectId);
        onRegister(ctx.pipeline().context(),appId,appKey);
        //dispatcherToUpsReceiver(context, Company.MEIZU, CommandType.REGISTER,pushId);
    }

    @Override
    public void unRegister(HandlerContext ctx) {
        String appId = getAppId(ctx.pipeline().context(), meizuName());
        String appKey = getAppKey(ctx.pipeline().context(), meizuName());
        onUnRegister(ctx.pipeline().context(),appId,appKey);
    }

    @Override
    public void setAlias(HandlerContext ctx, String alias) {
        String appId = getAppId(ctx.pipeline().context(), meizuName());
        String appKey = getAppKey(ctx.pipeline().context(), meizuName());
        onSetAlias(ctx.pipeline().context(),appId,appKey,alias);
    }

    @Override
    public void unSetAlias(HandlerContext ctx, String alias) {
        String appId = getAppId(ctx.pipeline().context(), meizuName());
        String appKey = getAppKey(ctx.pipeline().context(), meizuName());
        onUnsetAlias(ctx.pipeline().context(),appId,appKey,alias);
    }

    @Override
    public boolean isCurrentModel(HandlerContext ctx) {
        return !UpsUtils.isMeizu(ctx.pipeline().context()) && !UpsUtils.isHuaWei() &&  !UpsUtils.isXiaoMi();
    }

    @Override
    public String name() {
        return Company.OTHER.name();
    }

    private String meizuName(){
        return Company.MEIZU.name();
    }

    private void startPushService(Context context,String connectId){
        if(TextUtils.isEmpty(connectId)){
            connectId = MzSystemUtils.getDeviceId(context);
        }

        if(!TextUtils.isEmpty(connectId)){
            try {
                UpsLogger.e(this,"start pushService connectId "+connectId);
                Intent intent = new Intent(context, MzPushService.class);
                intent.putExtra("deviceId", connectId);
                context.startService(intent);
            } catch (Exception e){
                UpsLogger.e(this,"start PushService error",e);
            }
        } else {
            UpsLogger.e(this,"register to ups Platform error");
        }

    }
}
