package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;

import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.util.UpsUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

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
        MiPushClient.registerPush(context,appId,appKey);
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
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
}
