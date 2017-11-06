package com.meizu.upspushsdklib.handler.impl;

import android.text.TextUtils;

import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.upspushsdklib.handler.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;


public class MeizuHandler extends AbstractHandler{
    String appId;
    String appKey;
    @Override
    public void register(HandlerContext context, String appId, String appKey) {
        PushManager.register(context.pipeline().context(),appId,appKey);
//        this.appId = appId;
//        this.appKey = appKey;
//        putAppId(context,appId);
//        putAppKey(context,appKey);
    }

    @Override
    public void unRegister(HandlerContext context) {
//        if(TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)){
//            appId = getAppId(context);
//            appKey = getAppKey(context);
//        }
        PushManager.unRegister(context.pipeline().context(),appId,appKey);
    }

    @Override
    public void setAlias(HandlerContext context, String alias) {
//        if(TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)){
//            appId = getAppId(context);
//            appKey = getAppKey(context);
//        }
        PushManager.subScribeAlias(context.pipeline().context(),appId,appKey,PushManager.getPushId(context.pipeline().context()),alias);
    }

    @Override
    public void unSetAlias(HandlerContext context, String alias) {
//        if(TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)){
//            appId = getAppId(context);
//            appKey = getAppKey(context);
//        }
        PushManager.unSubScribeAlias(context.pipeline().context(),appId,appKey,PushManager.getPushId(context.pipeline().context()),alias);
    }

    @Override
    public boolean isCurrentModel() {
        return false;
    }

    @Override
    public String name() {
        return Company.MEIZU.name();
    }
}
