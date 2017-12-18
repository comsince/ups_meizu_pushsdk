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

package com.meizu.upspushsdklib.receiver.dispatcher;

import com.meizu.upspushsdklib.network.Response;
import com.meizu.upspushsdklib.network.Webb;
import com.meizu.upspushsdklib.util.UpsLogger;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class UpsPushAPI {
    private static String UPS_PUSH_API_SERVER = "https://client-api-mzups.meizu.com";

    private static String GET_CPINFO_URL_PREFIX = UPS_PUSH_API_SERVER + "/ups/api/client/webservice/getCpAppInfo";
    private static String REGISTER_URL_PREFIX = UPS_PUSH_API_SERVER + "/ups/api/client/push/registerPush";
    private static String UNREGISTER_URL_PREFIX = UPS_PUSH_API_SERVER +"/ups/api/client/push/unRegisterPush";
    private static String SET_ALIAS_URL_PREFIX = UPS_PUSH_API_SERVER + "/ups/api/client/push/subscribeAlias";
    private static String UNSET_ALIAS_URL_PREFIX = UPS_PUSH_API_SERVER + "/ups/api/client/push/unSubscribeAlias";

    /**
     * 获取厂商信息
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * */
//    public static ANResponse<String> getCpInfo(String appId, String appKey,int company,String packageName){
//        HashMap<String,String> paramsMap = new LinkedHashMap<>();
//        paramsMap.put("appId",appId);
//        paramsMap.put("cp",String.valueOf(company));
//        paramsMap.put("pkg",packageName);
//        HashMap<String,String> requestMap = new LinkedHashMap<>();
//        requestMap.putAll(paramsMap);
//        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
//        UpsLogger.i(UpsPushAPI.class, "getCpInfo post map " + requestMap);
//        return AndroidNetworking.get(GET_CPINFO_URL_PREFIX)
//                .addQueryParameter(requestMap)
//                .build()
//                .executeForString();
//    }

    /**
     * 获取厂商信息
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * */
    public static Response<String> getCpInfo0(String appId, String appKey,int company,String packageName){
        HashMap<String,String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("appId",appId);
        paramsMap.put("cp",String.valueOf(company));
        paramsMap.put("pkg",packageName);
        HashMap<String,Object> requestMap = new LinkedHashMap<>();
        requestMap.putAll(paramsMap);
        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
        UpsLogger.i(UpsPushAPI.class, "getCpInfo post map " + requestMap);
        return Webb.create().get(GET_CPINFO_URL_PREFIX)
                .params(requestMap)
                .asString();
    }

    /**
     * 同步订阅接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * */
//    public static ANResponse<String> register(String appId,String appKey,int company,String packageName,String deviceId,String token){
//        HashMap<String,String> paramsMap = new LinkedHashMap<>();
//        paramsMap.put("appId",appId);
//        paramsMap.put("cp",String.valueOf(company));
//        paramsMap.put("pkg",packageName);
//        paramsMap.put("deviceId",deviceId);
//        paramsMap.put("token",token);
//        HashMap<String,String> requestMap = new LinkedHashMap<>();
//        requestMap.putAll(paramsMap);
//        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
//        UpsLogger.i(UpsPushAPI.class, "register post map " + requestMap);
//        return AndroidNetworking.get(REGISTER_URL_PREFIX)
//                .addQueryParameter(requestMap)
//                .build()
//                .executeForString();
//    }

    /**
     * 同步订阅接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * */
    public static Response<String> register0(String appId, String appKey, int company, String packageName, String deviceId, String token){
        HashMap<String,String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("appId",appId);
        paramsMap.put("cp",String.valueOf(company));
        paramsMap.put("pkg",packageName);
        paramsMap.put("deviceId",deviceId);
        paramsMap.put("token",token);
        HashMap<String,Object> requestMap = new LinkedHashMap<>();
        requestMap.putAll(paramsMap);
        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
        UpsLogger.i(UpsPushAPI.class, "register post map " + requestMap);
        return Webb.create().get(REGISTER_URL_PREFIX)
                .params(requestMap)
                .asString();
    }

    /**
     * 同步取消订阅接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * */
//    public static ANResponse<String> unRegister(String appId,String appKey,int company,String packageName,String deviceId){
//        HashMap<String,String> paramsMap = new LinkedHashMap<>();
//        paramsMap.put("appId",appId);
//        paramsMap.put("cp",String.valueOf(company));
//        paramsMap.put("pkg",packageName);
//        paramsMap.put("deviceId",deviceId);
//        HashMap<String,String> requestMap = new LinkedHashMap<>();
//        requestMap.putAll(paramsMap);
//        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
//        UpsLogger.i(UpsPushAPI.class, "unRegister post map " + requestMap);
//        return AndroidNetworking.get(UNREGISTER_URL_PREFIX)
//                .addQueryParameter(requestMap)
//                .build()
//                .executeForString();
//    }

    /**
     * 同步取消订阅接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * */
    public static Response<String> unRegister0(String appId,String appKey,int company,String packageName,String deviceId){
        HashMap<String,String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("appId",appId);
        paramsMap.put("cp",String.valueOf(company));
        paramsMap.put("pkg",packageName);
        paramsMap.put("deviceId",deviceId);
        HashMap<String,Object> requestMap = new LinkedHashMap<>();
        requestMap.putAll(paramsMap);
        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
        UpsLogger.i(UpsPushAPI.class, "unRegister post map " + requestMap);
        return Webb.create().get(UNREGISTER_URL_PREFIX)
                .params(requestMap)
                .asString();
    }

    /**
     * 同步设置别名接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * @param token ups token
     * */
//    public static ANResponse<String> setAlias(String appId,String appKey,int company,String packageName,String deviceId,String token,String alias){
//        HashMap<String,String> paramsMap = new LinkedHashMap<>();
//        paramsMap.put("appId",appId);
//        paramsMap.put("cp",String.valueOf(company));
//        paramsMap.put("pkg",packageName);
//        paramsMap.put("deviceId",deviceId);
//        paramsMap.put("token",token);
//        paramsMap.put("alias",alias);
//        HashMap<String,String> requestMap = new LinkedHashMap<>();
//        requestMap.putAll(paramsMap);
//        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
//        UpsLogger.i(UpsPushAPI.class, "setAlias post map " + requestMap);
//        return AndroidNetworking.get(SET_ALIAS_URL_PREFIX)
//                .addQueryParameter(requestMap)
//                .build()
//                .executeForString();
//    }

    /**
     * 同步设置别名接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * @param token ups token
     * */
    public static Response<String> setAlias0(String appId,String appKey,int company,String packageName,String deviceId,String token,String alias){
        HashMap<String,String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("appId",appId);
        paramsMap.put("cp",String.valueOf(company));
        paramsMap.put("pkg",packageName);
        paramsMap.put("deviceId",deviceId);
        paramsMap.put("token",token);
        paramsMap.put("alias",alias);
        HashMap<String,Object> requestMap = new LinkedHashMap<>();
        requestMap.putAll(paramsMap);
        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
        UpsLogger.i(UpsPushAPI.class, "setAlias post map " + requestMap);
        return Webb.create().get(SET_ALIAS_URL_PREFIX)
                .params(requestMap)
                .asString();
    }


    /**
     * 同步取消别名接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * */
//    public static ANResponse<String> unSetAlias(String appId,String appKey,int company,String packageName,String deviceId,String token){
//        HashMap<String,String> paramsMap = new LinkedHashMap<>();
//        paramsMap.put("appId",appId);
//        paramsMap.put("cp",String.valueOf(company));
//        paramsMap.put("pkg",packageName);
//        paramsMap.put("deviceId",deviceId);
//        paramsMap.put("token",token);
//        HashMap<String,String> requestMap = new LinkedHashMap<>();
//        requestMap.putAll(paramsMap);
//        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
//        UpsLogger.i(UpsPushAPI.class, "unSetAlias post map " + requestMap);
//        return AndroidNetworking.get(UNSET_ALIAS_URL_PREFIX)
//                .addQueryParameter(requestMap)
//                .build()
//                .executeForString();
//    }

    /**
     * 同步取消别名接口
     * @param appId ups平台申请的appid
     * @param appKey ups平台申请的appkey
     * @param company  厂商信息
     * @param packageName 订阅的包名
     * @param deviceId 手机唯一识别标志
     * */
    public static Response<String> unSetAlias0(String appId,String appKey,int company,String packageName,String deviceId,String token){
        HashMap<String,String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("appId",appId);
        paramsMap.put("cp",String.valueOf(company));
        paramsMap.put("pkg",packageName);
        paramsMap.put("deviceId",deviceId);
        paramsMap.put("token",token);
        HashMap<String,Object> requestMap = new LinkedHashMap<>();
        requestMap.putAll(paramsMap);
        requestMap.put("sign", SignUtils.getSignature(paramsMap, appKey));
        UpsLogger.i(UpsPushAPI.class, "unSetAlias post map " + requestMap);
        return Webb.create().get(UNSET_ALIAS_URL_PREFIX)
                .params(requestMap)
                .asString();
    }
}
