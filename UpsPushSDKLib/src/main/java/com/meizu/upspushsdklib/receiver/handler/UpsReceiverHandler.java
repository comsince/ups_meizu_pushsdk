package com.meizu.upspushsdklib.receiver.handler;


import android.content.Intent;

interface UpsReceiverHandler {
    /**
     * 匹配消息格式
     * * 处理消息
     * 1.应用透传消息
     * 2.通知栏相关消息
     * 3.command消息
     * @param intent
     *       method 每个Intent都有一个method属性
     *       action
     *
     * @return boolean
     * */
    boolean messageMatch(Intent intent);


    /**
     * send message
     * @return
     * */
    boolean sendMessage(Intent intent);


    /**
     * 消息处理名称
     * */
    String getProcessorName();
}
