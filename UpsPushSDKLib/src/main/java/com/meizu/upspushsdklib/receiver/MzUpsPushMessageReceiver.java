package com.meizu.upspushsdklib.receiver;

import android.content.Context;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.upspushsdklib.UpsPushManager;


public final class MzUpsPushMessageReceiver extends MzPushMessageReceiver{
    @Override
    public void onRegister(Context context, String pushId) {

    }

    @Override
    public void onUnRegister(Context context, boolean success) {

    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {

    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        DebugLogger.i(UpsPushManager.TAG,"registerStatus "+registerStatus);
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        DebugLogger.i(UpsPushManager.TAG," unRegisterStatus "+unRegisterStatus);
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
         DebugLogger.i(UpsPushManager.TAG,"subAliasStatus "+subAliasStatus);
    }
}
