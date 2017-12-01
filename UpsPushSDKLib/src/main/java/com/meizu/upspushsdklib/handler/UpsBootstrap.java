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

package com.meizu.upspushsdklib.handler;

import android.content.Context;

import com.meizu.cloud.pushsdk.base.ExecutorProxy;
import com.meizu.upspushsdklib.handler.impl.AppSettingHandler;
import com.meizu.upspushsdklib.handler.impl.HuaWeiHandler;
import com.meizu.upspushsdklib.handler.impl.MeizuHandler;
import com.meizu.upspushsdklib.handler.impl.XiaoMiHandler;

import java.util.concurrent.Executor;

public class UpsBootstrap {

    private static UpsBootstrap upsBootstrap;
    private DefaultHandlerPipeline defaultHandlerPipeline;
    private Executor executor;
    public static UpsBootstrap getInstance(Context context){
         if(upsBootstrap == null){
             synchronized (UpsBootstrap.class){
                 if(upsBootstrap == null){
                     upsBootstrap = new UpsBootstrap(context);
                 }
             }
         }
         return upsBootstrap;
    }

    public static Executor executor(){
        return ExecutorProxy.get();
    }

    private UpsBootstrap(Context context){
        executor = ExecutorProxy.get();
        defaultHandlerPipeline = new DefaultHandlerPipeline(context);
        defaultHandlerPipeline
                .addLast(new AppSettingHandler())
                .addLast(new MeizuHandler())
                .addLast(new XiaoMiHandler())
                .addLast(new HuaWeiHandler());
    }

    /**
     * 订阅接口
     * @param appId 应用在各个平台申请的appId
     * @param appKey 应用在各个平台申请的appKey
     * */
    public void register(final String appId, final String appKey){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                defaultHandlerPipeline.fireRegister(appId,appKey);
            }
        });
    }

    /**
     * 反订阅
     * */
    public void unRegister(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                defaultHandlerPipeline.fireUnRegister();
            }
        });
    }

    /**
     * 设置别名
     * @param alias 别名
     * */
    public void setAlias(final String alias){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                defaultHandlerPipeline.fireSetAlias(alias);

            }
        });
    }

    /**
     * 取消别名设置
     * @param alias 要取消的别名
     * */
    public void unSetAlias(final String alias){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                defaultHandlerPipeline.fireUnSetAlias(alias);
            }
        });
    }
}
