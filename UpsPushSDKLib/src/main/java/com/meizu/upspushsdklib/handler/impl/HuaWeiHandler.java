package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;

import com.huawei.hms.api.HuaweiApiClient;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.hw.HwPushClient;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;

public class HuaWeiHandler extends AbstractHandler implements HuaweiApiClient.ConnectionCallbacks{
    protected HwPushClient hwPushClient;

    @Override
    public void onRegister(Context context, String appId, String appKey) {
        if(hwPushClient == null){
            hwPushClient = new HwPushClient(context,this);
        }
        hwPushClient.getTokenSync();
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
    }

    @Override
    public boolean isCurrentModel() {
        return UpsUtils.isHuaWei();
    }

    @Override
    public String name() {
        return Company.HUAWEI.name();
    }

    @Override
    public void onConnected() {
        UpsLogger.i(this,"hwClient connected");
        hwPushClient.getTokenSync();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //HuaweiApiClient断开连接的时候，业务可以处理自己的事件
        UpsLogger.i(this, "HuaweiApiClient onConnectionSuspended code "+ i);
    }
}
