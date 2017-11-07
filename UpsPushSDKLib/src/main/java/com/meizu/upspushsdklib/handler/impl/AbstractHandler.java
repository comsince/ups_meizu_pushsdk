package com.meizu.upspushsdklib.handler.impl;


import android.content.Context;

import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.handler.UpsHandler;


public abstract class AbstractHandler implements UpsHandler{

    private static final String APP_PUSH_SETTING_PREFERENCE_NAME = "app_push_setting";
    private static final String KEY_APP_ID_PRFIX = ".app_id";
    private static final String KEY_APP_KEY_PREIX = ".app_key";

    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
         onRegister(ctx.pipeline().context(),appId,appKey);
    }

    @Override
    public void setAlias(HandlerContext ctx, String alias) {
        String mzAppId = getAppId(ctx.pipeline().context(), name());
        String mzAppKey = getAppKey(ctx.pipeline().context(), name());
        onSetAlias(ctx.pipeline().context(),mzAppId,mzAppKey,alias);
    }

    @Override
    public void unSetAlias(HandlerContext ctx, String alias) {
        String mzAppId = getAppId(ctx.pipeline().context(), name());
        String mzAppKey = getAppKey(ctx.pipeline().context(), name());
        onUnsetAlias(ctx.pipeline().context(),mzAppId,mzAppKey,alias);
    }

    @Override
    public void unRegister(HandlerContext ctx) {
        String mzAppId = getAppId(ctx.pipeline().context(), name());
        String mzAppKey = getAppKey(ctx.pipeline().context(), name());
        onUnRegister(ctx.pipeline().context(),mzAppId,mzAppKey);
    }

    public void onRegister(Context context, String appId, String appKey){}

    public void onUnRegister(Context context, String appId, String appKey) {}

    public void onSetAlias(Context context, String appId, String appKey, String alias){}

    public void onUnsetAlias(Context context, String appId, String appKey, String alias){}

    protected void putAppId(Context context, String deviceModel, String appId){
        PushPreferencesUtils.putStringByKey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName()+"."+KEY_APP_ID_PRFIX,appId);
    }

    protected void putAppKey(Context context,String deviceModel,String appKey){
        PushPreferencesUtils.putStringByKey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName()+"."+KEY_APP_KEY_PREIX,appKey);
    }

    protected String getAppId(Context context,String deviceModel){
        return PushPreferencesUtils.getStringBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName()+"."+KEY_APP_ID_PRFIX);
    }

    protected String getAppKey(Context context,String deviceModel){
        return PushPreferencesUtils.getStringBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName()+"."+KEY_APP_KEY_PREIX);
    }

}
