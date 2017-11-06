package com.meizu.upspushsdklib.handler;

import android.content.Context;

import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;

public abstract class AbstractHandlerContext implements HandlerContext{

    private static final String APP_PUSH_SETTING_PREFERENCE_NAME = "app_push_setting";
    private static final String KEY_APP_ID_PRFIX = ".app_id";
    private static final String KEY_APP_KEY_PREIX = ".app_key";

    volatile AbstractHandlerContext prev;
    volatile AbstractHandlerContext next;
    //handler context name
    private final String name;
    private final DefaultHandlerPipeline pipeline;

    public AbstractHandlerContext(String name, DefaultHandlerPipeline pipeline) {
        this.name = name;
        this.pipeline = pipeline;
    }

    @Override
    public void fireRegister(String appId, String appKey) {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeRegister(appId,appKey);
    }

    @Override
    public void fireUnRegister() {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeUnRegister();
    }

    @Override
    public void fireSetAlias(String alias) {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeSetAlias(alias);
    }

    @Override
    public void fireUnSetAlias(String alias) {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeUnSetAlias(alias);
    }

    @Override
    public HandlerPipeline pipeline() {
        return pipeline;
    }

    private AbstractHandlerContext findNextHandlerContext() {
        AbstractHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!isCurrentModel());
        return ctx;
    }

    private void invokeRegister(String appId,String appKey){
          handler().register(this,appId,appKey);
    }


    private void invokeUnRegister(){
        handler().unRegister(this);
    }

    private void invokeSetAlias(String alias){
        handler().setAlias(this,alias);
    }

    private void invokeUnSetAlias(String alias){
        handler().unSetAlias(this,alias);
    }


    protected void putAppId(Context context,String deviceModel, String appId){
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
