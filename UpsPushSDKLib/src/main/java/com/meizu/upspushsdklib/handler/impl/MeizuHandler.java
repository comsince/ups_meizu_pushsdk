package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;

import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.upspushsdklib.handler.Company;


public class MeizuHandler extends AbstractHandler{

    @Override
    public void onRegister(Context context, String appId, String appKey) {
        PushManager.register(context,appId,appKey);
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
        PushManager.unRegister(context,appId,appKey);
    }

    @Override
    public void onSetAlias(Context context, String appId, String appKey, String alias) {
        PushManager.subScribeAlias(context,appId,appKey,PushManager.getPushId(context),alias);
    }

    @Override
    public void onUnsetAlias(Context context, String appId, String appKey, String alias) {
        PushManager.unSubScribeAlias(context,appId,appKey,PushManager.getPushId(context),alias);
    }

    @Override
    public boolean isCurrentModel() {
        return true;
    }

    @Override
    public String name() {
        return Company.MEIZU.name();
    }
}
