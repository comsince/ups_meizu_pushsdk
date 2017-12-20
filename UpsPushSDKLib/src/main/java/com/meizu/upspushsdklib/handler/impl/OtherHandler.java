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

import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.cloud.pushsdk.pushservice.MzPushService;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.network.Response;
import com.meizu.upspushsdklib.receiver.dispatcher.UpsPushAPI;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 支持非flyme手机的推送， 使用魅族自有通道
 */

public class OtherHandler extends MeizuHandler {


    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
        nonFlymeRegister(ctx.pipeline().context(),appId,appKey);
    }

    @Override
    public void unRegister(HandlerContext ctx) {
        String appId = getAppId(ctx.pipeline().context(), meizuName());
        String appKey = getAppKey(ctx.pipeline().context(), meizuName());
        nonFlymeUnRegister(ctx.pipeline().context(),appId,appKey);
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

    /**
     * 启动推送服务
     * @param context 应用context
     * @param connectId 标记与服务端的长连接标记
     * */
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

    /**
     * 非Flyme订阅请求
     * */
    private void nonFlymeRegister(Context context,String appId,String appKey){
        String deviceId = MzSystemUtils.getDeviceId(context);
        Response<String> response = UpsPushAPI.registerNonFlyme(appId,appKey,Company.OTHER.code(),deviceId);
        if(response.isSuccess()){
            NonFlymeRegisterStatus nonFlymeRegisterStatus = new NonFlymeRegisterStatus(response.getBody());
            dispatcherToUpsReceiver(context,Company.OTHER,CommandType.REGISTER,nonFlymeRegisterStatus.getPushId());
            String connectId = nonFlymeRegisterStatus.getConnectId();
            startPushService(context,connectId);
        } else {
            //订阅失败则走魅族正常订阅，使用imei作为链接Id
            startPushService(context,null);
            onRegister(context,appId,appKey);
            //
            //dispatcherToUpsReceiver(context,Company.OTHER,CommandType.REGISTER,"");
        }
    }

    /**
     * 非Flyme反订阅请求
     * */
    private void nonFlymeUnRegister(Context context,String appId,String appKey){
        String deviceId = MzSystemUtils.getDeviceId(context);
        Response<String> response = UpsPushAPI.unRegisterNonFlyme(appId,appKey,Company.OTHER.code(),deviceId);
        if(response.isSuccess()){
            UnRegisterStatus unRegisterStatus = new UnRegisterStatus(response.getBody());
            dispatcherToUpsReceiver(context,Company.OTHER,CommandType.UNREGISTER,String.valueOf(unRegisterStatus.isUnRegisterSuccess()));
        } else {
            dispatcherToUpsReceiver(context,Company.OTHER,CommandType.UNREGISTER,"false");
        }
    }

    private class NonFlymeRegisterStatus extends RegisterStatus{
        String connectId;

        public NonFlymeRegisterStatus(String json){
            super(json);
        }

        @Override
        public void parseValueData(JSONObject jsonObject) throws JSONException {
            if(!jsonObject.isNull("pushId")){
                setPushId(jsonObject.getString("pushId"));
            }
            if(!jsonObject.isNull("expireTime")){
                setExpireTime(jsonObject.getInt("expireTime"));
            }
            if(!jsonObject.isNull("connectId")){
                setConnectId(jsonObject.getString("connectId"));
            }
        }

        public String getConnectId() {
            return connectId;
        }

        public void setConnectId(String connectId) {
            this.connectId = connectId;
        }
    }
}
