package com.meizu.upspushsdklib.handler;

public interface HandlerContext {

    /**
     * 执行注册逻辑
     * */
    void fireRegister(String appId, String appKey);

    void fireUnRegister();

    void fireSetAlias(String alias);

    void fireUnSetAlias(String alias);

    /**
     * 当前逻辑是否适配此机型或者时系统版本
     * */
    boolean isCurrentModel();

    UpsHandler handler();

    HandlerPipeline pipeline();
}
