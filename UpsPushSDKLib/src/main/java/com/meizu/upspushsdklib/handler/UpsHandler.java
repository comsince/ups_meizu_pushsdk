package com.meizu.upspushsdklib.handler;

public interface UpsHandler {
    /**
     * 订阅接口
     * @param ctx
     * @param appId 应用在各个平台申请的appId
     * @param appKey 应用在各个平台申请的appKey
     * */
    void register(HandlerContext ctx, String appId, String appKey);

    /**
     * 反订阅
     * @param ctx
     * */
    void unRegister(HandlerContext ctx);

    /**
     * 设置别名
     * @param ctx
     * @param alias 别名
     * */
    void setAlias(HandlerContext ctx, String alias);

    /**
     * 取消别名设置
     * @param ctx
     * @param alias 要取消的别名
     * */
    void unSetAlias(HandlerContext ctx, String alias);

    /**
     * 当前逻辑是否适配此机型或者时系统版本
     * */
    boolean isCurrentModel();
    /**
     * handle名称
     * */
    String name();
}
