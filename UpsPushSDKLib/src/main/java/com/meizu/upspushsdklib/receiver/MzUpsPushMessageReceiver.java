package com.meizu.upspushsdklib.receiver;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.PushType;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageType;
import com.meizu.upspushsdklib.receiver.dispatcher.CommandMessageDispatcher;
import com.meizu.upspushsdklib.receiver.dispatcher.UpsPushMessageDispatcher;
import com.meizu.upspushsdklib.util.UpsLogger;


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
        UpsLogger.i(this,"MzUpsPushMessageReceiver registerStatus "+registerStatus);
        UpsCommandMessage upsCommandMessage = UpsCommandMessage.builder()
                .code(Integer.valueOf(registerStatus.getCode()))
                .message(registerStatus.getMessage())
                .company(Company.MEIZU)
                .commandResult(registerStatus.getPushId())
                .commandType(CommandType.REGISTER)
                .extra(registerStatus)
                .build();

        CommandMessageDispatcher.create(context,upsCommandMessage).dispatch();
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        UpsLogger.i(this,"MzUpsPushMessageReceiver unRegisterStatus "+unRegisterStatus);
        UpsCommandMessage upsCommandMessage = UpsCommandMessage.builder()
                .code(Integer.valueOf(unRegisterStatus.getCode()))
                .message(unRegisterStatus.getMessage())
                .company(Company.MEIZU)
                .commandResult(String.valueOf(unRegisterStatus.isUnRegisterSuccess()))
                .commandType(CommandType.UNREGISTER)
                .extra(unRegisterStatus)
                .build();

        CommandMessageDispatcher.create(context,upsCommandMessage).dispatch();
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        UpsLogger.i(this,"MzUpsPushMessageReceiver subAliasStatus "+subAliasStatus);
        UpsCommandMessage upsCommandMessage = UpsCommandMessage.builder()
                .code(Integer.valueOf(subAliasStatus.getCode()))
                .message(subAliasStatus.getMessage())
                .company(Company.MEIZU)
                .commandResult(String.valueOf(subAliasStatus.getAlias()))
                .commandType(TextUtils.isEmpty(subAliasStatus.getAlias()) ? CommandType.UNSUBALIAS : CommandType.SUBALIAS)
                .extra(subAliasStatus)
                .build();

        CommandMessageDispatcher.create(context,upsCommandMessage).dispatch();
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        UpsLogger.i(this,"MzUpsPushMessageReceiver onNotificationClicked "+mzPushMessage);
        UpsPushMessageDispatcher.dispatch(context,
                UpsPushMessage.builder()
                        .title(mzPushMessage.getTitle())
                        .noifyId(mzPushMessage.getNotifyId())
                        .content(mzPushMessage.getContent())
                        .company(Company.MEIZU)
                        .selfDefineString(mzPushMessage.getSelfDefineContentString())
                        .pushType(PushType.NOTIFICATION_MESSAGE)
                        .extra(mzPushMessage).build(),
                UpsPushMessageType.NOTIFICATION_CLICK);
    }

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        UpsLogger.i(this,"MzUpsPushMessageReceiver onNotificationArrived "+mzPushMessage);
        UpsPushMessageDispatcher.dispatch(context,
                UpsPushMessage.builder()
                        .title(mzPushMessage.getTitle())
                        .content(mzPushMessage.getContent())
                        .noifyId(mzPushMessage.getNotifyId())
                        .company(Company.MEIZU)
                        .selfDefineString(mzPushMessage.getSelfDefineContentString())
                        .pushType(PushType.NOTIFICATION_MESSAGE)
                        .extra(mzPushMessage).build(),
                UpsPushMessageType.NOTIFICATION_ARRIVED);
    }

    @Override
    public void onMessage(Context context, String message, String platformExtra) {
        UpsLogger.i(this,"MzUpsPushMessageReceiver onMessage "+message);
        UpsPushMessageDispatcher.dispatch(context,
                UpsPushMessage.builder()
                        .content(message)
                        .company(Company.MEIZU)
                        .pushType(PushType.THROUGH_MESSAGE)
                        .extra(platformExtra).build(),
                UpsPushMessageType.THROUGH_MESSAGE);
    }
}
