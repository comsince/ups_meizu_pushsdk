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

package com.meizu.upspushsdklib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.SystemProperties;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
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




    /**
     * 返回机型信息
     * */
    public static String deviceModel(){
        return android.os.Build.MODEL;
    }

    /**
     * 是否为魅族手机
     * */
    public static boolean isMeizu(){
        return MzSystemUtils.isBrandMeizu();
    }

    /**
     * 判断当前手机是否为华为手机
     * */
    public static boolean isHuaWei(){
        String emui = SystemProperties.get("ro.build.version.emui");
        UpsLogger.e(UpsUtils.class,"huawei eui "+emui);
        return !TextUtils.isEmpty(emui);
    }

    /**
     * 判断是否为小米手机
     * <a href = "https://dev.mi.com/doc/p=254/index.html"/>具体参见<a/>
     * */
    public static boolean isXiaoMi(){
        return "Xiaomi".equals(Build.MODEL) || "Xiaomi".equals(Build.MANUFACTURER);
    }
}
