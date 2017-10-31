package com.meizu.upspushsdklib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.upspushsdklib.UpsPushManager;


public class UpsUtils {

    /**
     * 通过meta name查找value,注意长整型的数字存在问题，需要在前面加\0 强制解析为字符串
     * @param context  应用application context
     * @param name 配置的meta 名称
     * @return string 返回string value值
     * */
    public static String getMetaStringValueByName(Context context ,String name){
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
            DebugLogger.e(UpsPushManager.TAG, name + "=" + value);
        } catch (PackageManager.NameNotFoundException e) {
            DebugLogger.e(UpsPushManager.TAG,"getMetaValueByName exception "+e);
        }
        return value;
    }

    /**
     * 通过meta name查找value
     * @param context  应用application context
     * @param name 配置的meta 名称
     * @return string 返回string value值
     * */
    public static String getMetaIntValueByName(Context context ,String name){
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = String.valueOf(appInfo.metaData.getInt(name));
            DebugLogger.e(UpsPushManager.TAG, name + "=" + value);
        } catch (PackageManager.NameNotFoundException e) {
            DebugLogger.e(UpsPushManager.TAG,"getMetaValueByName exception "+e);
        }
        return value;
    }


    /**
     * 通过meta name查找value
     * @param context  应用application context
     * @param name 配置的meta 名称
     * @return string 返回string value值
     * */
    public static String getMetaFloatValueByName(Context context ,String name){
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = String.valueOf(appInfo.metaData.getFloat(name));
            DebugLogger.e(UpsPushManager.TAG, name + "=" + value);
        } catch (PackageManager.NameNotFoundException e) {
            DebugLogger.e(UpsPushManager.TAG,"getMetaValueByName exception "+e);
        }
        return value;
    }
}
