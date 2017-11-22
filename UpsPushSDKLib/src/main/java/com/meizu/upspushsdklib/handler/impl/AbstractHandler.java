package com.meizu.upspushsdklib.handler.impl;


import android.content.Context;

import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.handler.UpsHandler;
import com.meizu.upspushsdklib.hw.HwPushClient;
import com.meizu.upspushsdklib.receiver.dispatcher.CommandMessageDispatcher;
import com.meizu.upspushsdklib.util.UpsConstantCode;
import com.meizu.upspushsdklib.util.UpsLogger;


public abstract class AbstractHandler implements UpsHandler{

    private static final String APP_PUSH_SETTING_PREFERENCE_NAME = "app_push_setting";
    private static final String KEY_APP_ID_PRFIX = ".app_id";
    private static final String KEY_APP_KEY_PREIX = ".app_key";
    private static final String KEY_APP_UPS_PUSH_ID = ".ups_push_id";
    private static final String KEY_APP_UPS_PUSH_ID_EXPIRE_TIME = ".ups_pushId_expire_time";

    private static final String KEY_APP_HW_TOKEN = ".hw_token";

    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
         onRegister(ctx.pipeline().context(),appId,appKey);
    }

    @Override
    public void setAlias(HandlerContext ctx, String alias) {
        if(Company.MEIZU.name().equals(name())){
            String appId = getAppId(ctx.pipeline().context(), name());
            String appKey = getAppKey(ctx.pipeline().context(), name());
            onSetAlias(ctx.pipeline().context(),appId,appKey,alias);
        } else {
            onSetAlias(ctx.pipeline().context(),null,null,alias);
        }
        if(dispatchToUpsReceiver(CommandType.SUBALIAS)){
            dispatcherToUpsReceiver(ctx.pipeline().context(),Company.valueOf(name()),CommandType.SUBALIAS,alias);
        }
    }

    @Override
    public void unSetAlias(HandlerContext ctx, String alias) {
        if(Company.MEIZU.name().equals(name())){
            String appId = getAppId(ctx.pipeline().context(), name());
            String appKey = getAppKey(ctx.pipeline().context(), name());
            onUnsetAlias(ctx.pipeline().context(),appId,appKey,alias);
        } else {
            onUnsetAlias(ctx.pipeline().context(),null,null,alias);
        }
        if(dispatchToUpsReceiver(CommandType.UNSUBALIAS)){
            dispatcherToUpsReceiver(ctx.pipeline().context(),Company.valueOf(name()),CommandType.UNSUBALIAS,alias);
        }
    }

    @Override
    public void unRegister(HandlerContext ctx) {
        if(Company.MEIZU.name().equals(name())){
            String appId = getAppId(ctx.pipeline().context(), name());
            String appKey = getAppKey(ctx.pipeline().context(), name());
            onUnRegister(ctx.pipeline().context(),appId,appKey);
        } else {
            onUnRegister(ctx.pipeline().context(),null,null);
        }
        if(dispatchToUpsReceiver(CommandType.UNREGISTER)){
            dispatcherToUpsReceiver(ctx.pipeline().context(),Company.valueOf(name()),CommandType.UNREGISTER,"");
        }
    }

    public void onRegister(Context context, String appId, String appKey){}

    public void onUnRegister(Context context, String appId, String appKey) {}

    public void onSetAlias(Context context, String appId, String appKey, String alias){}

    public void onUnsetAlias(Context context, String appId, String appKey, String alias){}

    /**
     * 是否将反订阅消息发送给UpsPushMessageReceiver
     * */
    protected boolean dispatchToUpsReceiver(CommandType commandType){
        return false;
    }


    /**
     * 发送消息至UpsPushReceiver,进行消息闭环
     * @param context
     * @param company cp 类型
     * @param commandType
     * @param commandResult 传递的参数
     * */
    public void dispatcherToUpsReceiver(Context context,Company company,CommandType commandType,String commandResult){
        UpsLogger.i(this,"dispatcherToUpsReceiver to company "+company +" commandType "+commandType+" commandResult "+commandResult);
        UpsCommandMessage upsCommandMessage = UpsCommandMessage.builder()
                .company(company)
                .commandType(commandType)
                .commandResult(commandResult)
                .build();
        CommandMessageDispatcher.create(context,upsCommandMessage).dispatch();
    }

    /**
     * 通过机型存储对应的平台的appId和appKey
     * @param context
     * @param appId
     * @param deviceModel 机型信息
     * */
    protected void putAppId(Context context, String deviceModel, String appId){
        PushPreferencesUtils.putStringByKey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName() + KEY_APP_ID_PRFIX,appId);
    }

    protected void putAppKey(Context context,String deviceModel,String appKey){
        PushPreferencesUtils.putStringByKey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName() + KEY_APP_KEY_PREIX,appKey);
    }

    public static String getAppId(Context context,String deviceModel){
        return PushPreferencesUtils.getStringBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName() + KEY_APP_ID_PRFIX);
    }

    public static String getAppKey(Context context,String deviceModel){
        return PushPreferencesUtils.getStringBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,deviceModel+"."+context.getPackageName() + KEY_APP_KEY_PREIX);
    }

    public static void putUpsPushId(Context context,String pushId){
        PushPreferencesUtils.putStringByKey(context,APP_PUSH_SETTING_PREFERENCE_NAME,context.getPackageName() + KEY_APP_UPS_PUSH_ID,pushId);
    }

    public static String getUpsPushId(Context context){
       return PushPreferencesUtils.getStringBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,context.getPackageName() + KEY_APP_UPS_PUSH_ID);
    }

    public static void putUpsExpireTime(Context context,int expireTime){
        PushPreferencesUtils.putIntBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,context.getPackageName()+KEY_APP_UPS_PUSH_ID_EXPIRE_TIME,expireTime);
    }

    public static int getUpsExpireTime(Context context){
        return PushPreferencesUtils.getIntBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,context.getPackageName()+KEY_APP_UPS_PUSH_ID_EXPIRE_TIME);
    }

    public static void putHWToken(Context context,String token){
        PushPreferencesUtils.putStringByKey(context,APP_PUSH_SETTING_PREFERENCE_NAME,context.getPackageName()+KEY_APP_HW_TOKEN,token);
    }

    public static String getHwToken(Context context){
        return PushPreferencesUtils.getStringBykey(context,APP_PUSH_SETTING_PREFERENCE_NAME,context.getPackageName()+KEY_APP_HW_TOKEN);
    }
}
