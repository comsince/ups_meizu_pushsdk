package com.meizu.upspushsdklib.handler;

/**
 * Created by liaojinlong on 17-11-6.
 */

public interface UpsHandler {
    /**
     * 订阅接口
     * @param context  应用application context ,如果是华为手机的话,此处context时activity类型的
     * @param appId 应用在各个平台申请的appId
     * @param appKey 应用在各个平台申请的appKey
     * */
    void register(HandlerContext context, String appId, String appKey);

    /**
     * 反订阅
     * @param context
     * */
    void unRegister(HandlerContext context);

    /**
     * 设置别名
     * @param context
     * @param alias 别名
     * */
    void setAlias(HandlerContext context,String alias);

    /**
     * 取消别名设置
     * @param context
     * @param alias 要取消的别名
     * */
    void unSetAlias(HandlerContext context,String alias);

    /**
     * 当前逻辑是否适配此机型或者时系统版本
     * */
    boolean isCurrentModel();
    /**
     * handle名称
     * */
    String name();
}
