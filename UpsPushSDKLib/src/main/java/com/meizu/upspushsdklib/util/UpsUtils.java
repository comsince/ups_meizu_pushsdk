package com.meizu.upspushsdklib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.upspushsdklib.UpsPushManager;


public class UpsUtils {

    /**
     * 通过meta name查找value
     * @param context
     * @param name
     * */
    private String getMetaValueByName(Context context ,String name){
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
}
