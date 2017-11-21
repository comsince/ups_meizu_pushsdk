package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 处理小米相关接口
 * 反订阅接口由于没有相关Receiver回调,需要自己处理
 * */
public class XiaoMiHandler extends AbstractHandler{


    @Override
    public boolean isCurrentModel() {
        return UpsUtils.isXiaoMi();
    }

    @Override
    public String name() {
        return Company.XIAOMI.name();
    }

    @Override
    public void onRegister(Context context, String appId, String appKey) {
        if(TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)){
            UpsLogger.e(this,"xm appId or appKey not null");
        } else {
            MiPushClient.registerPush(context,appId,appKey);
        }
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
        //调用unregisterPush()之后，服务器不会向app发送任何消息
        //详情：https://dev.mi.com/console/doc/detail?pId=41#_2_0
        MiPushClient.unregisterPush(context);
    }

    @Override
    public void onSetAlias(Context context, String appId, String appKey, String alias) {
        MiPushClient.setAlias(context,alias,null);
    }

    @Override
    public void onUnsetAlias(Context context, String appId, String appKey, String alias) {
        MiPushClient.unsetAlias(context,alias,null);
    }

    @Override
    protected boolean dispatchToUpsReceiver(CommandType commandType) {
        boolean flag;
        switch (commandType){
            case UNREGISTER:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }
}
