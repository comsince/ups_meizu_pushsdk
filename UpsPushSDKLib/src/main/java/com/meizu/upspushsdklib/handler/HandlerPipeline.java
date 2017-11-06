package com.meizu.upspushsdklib.handler;

import android.content.Context;

import java.util.concurrent.Executor;


public interface HandlerPipeline {
    /**
     * 执行注册逻辑
     * */
    void fireRegister(String appId, String appKey);

    void fireUnRegister();

    void fireSetAlias(String alias);

    void fireUnSetAlias(String alias);

    HandlerPipeline addLast(UpsHandler... handlers);

    HandlerPipeline addLast(String name, UpsHandler handler);

    HandlerPipeline addFirst(UpsHandler... handlers);

    Context context();

    Executor executor();
}
