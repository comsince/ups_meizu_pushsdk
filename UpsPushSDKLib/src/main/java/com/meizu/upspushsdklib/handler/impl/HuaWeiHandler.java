package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;

import com.huawei.hms.api.HuaweiApiClient;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.hw.HwPushClient;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;

public class HuaWeiHandler extends AbstractHandler implements HuaweiApiClient.ConnectionCallbacks{
    protected HwPushClient hwPushClient;
    private volatile boolean isRegister = true;

    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
        if(hwPushClient == null){
            hwPushClient = new HwPushClient(ctx.pipeline().context(),this);
        }
        isRegister = true;
        hwPushClient.getTokenSync();
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
        isRegister = false;
        hwPushClient.deleteToken();
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
        UpsLogger.e(this,"hwClient connected start "+(isRegister ? "register " : "unregister"));
        if(isRegister){
            hwPushClient.getTokenSync();
        } else {
            hwPushClient.deleteToken();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //HuaweiApiClient断开连接的时候，业务可以处理自己的事件
        UpsLogger.i(this, "HuaweiApiClient onConnectionSuspended code "+ i);
    }

    @Override
    protected boolean dispatchToUpsReceiver(CommandType commandType) {
        boolean flag;
        switch (commandType){
            case UNREGISTER:
            case SUBALIAS:
            case UNSUBALIAS:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }
}
