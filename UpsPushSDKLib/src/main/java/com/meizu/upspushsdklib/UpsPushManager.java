package com.meizu.upspushsdklib;

import android.content.Context;

import com.meizu.upspushsdklib.handler.UpsBootstrap;

public class UpsPushManager {
    public static final String TAG = "UpsPushManager";


    /**
     * 订阅接口
     * @param context  应用application context ,如果是华为手机的话,此处context时activity类型的
     * @param appId 应用在各个平台申请的appId
     * @param appKey 应用在各个平台申请的appKey
     * */
    public static void register(Context context, String appId, String appKey){
        UpsBootstrap.getInstance(context).register(appId,appKey);
    }

    /**
     * 反订阅
     * @param context
     * */
    public static void unRegister(Context context){
        UpsBootstrap.getInstance(context).unRegister();
    }

    /**
     * 设置别名
     * @param context
     * @param alias 别名
     * */
    public static void setAlias(Context context,String alias){
        UpsBootstrap.getInstance(context).setAlias(alias);
    }

    /**
     * 取消别名设置
     * @param context
     * @param alias 要取消的别名
     * */
    public static void unSetAlias(Context context,String alias){
        UpsBootstrap.getInstance(context).unSetAlias(alias);
    }

}
