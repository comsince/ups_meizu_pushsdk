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
     * 反订阅, 在魅族平台即是反订阅,在小米平台即是反订阅push
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
