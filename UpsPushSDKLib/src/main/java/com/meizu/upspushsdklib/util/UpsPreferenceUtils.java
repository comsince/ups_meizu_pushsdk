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
import android.content.SharedPreferences;

import com.meizu.cloud.pushsdk.constants.PushConstants;

import java.util.Set;

public class UpsPreferenceUtils {
    public static final String MZ_PUSH_PREFERENCE = "mz_push_preference";
    /**
     * 获取执行name的preference
     * @param context
     * @param name
     * **/
    private static SharedPreferences getSharePerferenceByName(Context context, String name){
        //http://zmywly8866.github.io/2015/09/09/sharedpreferences-in-multiprocess.html
        //多进程数据不共享,建议升级pushSDK3.5.0以上版本
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 保存String类型的数据
     * @param context
     * @param preferenceName
     * @param key
     * @param value
     *
     * */
    public static void putStringByKey(Context context,String preferenceName,String key,String value){
        SharedPreferences sharedPreferences = getSharePerferenceByName(context, preferenceName);
        sharedPreferences.edit().putString(key,value).apply();
    }

    public static String getStringBykey(Context context,String preferenceName,String key){
        return getSharePerferenceByName(context,preferenceName).getString(key,"");
    }

    public static Set<String> getStringSetBykey(Context context, String preferenceName, String key){
        return getSharePerferenceByName(context,preferenceName).getStringSet(key,null);
    }

    public static void putStringSetByKey(Context context,String preferenceName,String key,Set<String> stringSet){
        getSharePerferenceByName(context,preferenceName).edit().putStringSet(key,stringSet).commit();
    }

    public static void putIntBykey(Context context,String preferenceName,String key,int value){
        SharedPreferences sharedPreferences = getSharePerferenceByName(context, preferenceName);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getIntBykey(Context context,String preferenceName,String key){
        return getSharePerferenceByName(context,preferenceName).getInt(key, 0);
    }

    public static void putBooleanByKey(Context context,String preferenceName,String key,boolean value){
        SharedPreferences sharedPreferences = getSharePerferenceByName(context,preferenceName);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanByKey(Context context,String preferenceName,String key){
        return getSharePerferenceByName(context,preferenceName).getBoolean(key,true);
    }

    public static boolean remove(Context context,String preferenceName,String key){
        return getSharePerferenceByName(context,preferenceName).edit().remove(key).commit();
    }


    /**
     * get Default pushId
     * */
    public static String getPushId(Context context,String pkg){
        return getStringBykey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg + "_" + PushConstants.KEY_PUSH_ID);
    }

    /**
     * put register pushId
     * */
    public static void putPushId(Context context,String pushId,String pkg){
        putStringByKey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg+"_"+PushConstants.KEY_PUSH_ID, pushId);
    }

    /**
     * 缓存pushid过期时间
     * */
    public static void putPushIdExpireTime(Context context,int expireTime,String pkg){
        putIntBykey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg + "_" + PushConstants.KEY_PUSH_ID_EXPIRE_TIME, expireTime);
    }

    /**
     * 获取pushid过期时间
     * */
    public static int getPushIdExpireTime(Context context,String pkg){
        return getIntBykey(context,PushConstants.PUSH_ID_PREFERENCE_NAME,pkg+"_"+PushConstants.KEY_PUSH_ID_EXPIRE_TIME);
    }

    /**
     * get deviceId
     * */
    public static String getDeviceId(Context context){
        return getSharePerferenceByName(context,MZ_PUSH_PREFERENCE).getString(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY, null);
    }

    public static void putDeviceId(Context context,String deviceId){
        putStringByKey(context,MZ_PUSH_PREFERENCE,PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY,deviceId);
    }


}
