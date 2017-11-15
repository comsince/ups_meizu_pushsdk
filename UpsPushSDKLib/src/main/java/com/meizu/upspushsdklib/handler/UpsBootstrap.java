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
