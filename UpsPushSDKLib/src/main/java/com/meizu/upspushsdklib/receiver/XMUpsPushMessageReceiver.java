package com.meizu.upspushsdklib.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.PushType;
import com.meizu.upspushsdklib.R;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageType;
import com.meizu.upspushsdklib.receiver.dispatcher.CommandMessageDispatcher;
import com.meizu.upspushsdklib.receiver.dispatcher.UpsPushMessageDispatcher;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public final class XMUpsPushMessageReceiver extends PushMessageReceiver {


    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        UpsLogger.i(this,
                "onReceivePassThroughMessage is called. " + message.toString());
        UpsPushMessageDispatcher.dispatch(context, UpsPushMessage.builder()
                        .title(message.getTitle())
                        .content(message.getDescription())
                        .company(Company.XIAOMI)
                        .extra(message)
                        .pushType(PushType.THROUGH_MESSAGE)
                        .build(),
                UpsPushMessageType.NOTIFICATION_CLICK);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        UpsLogger.e(UpsPushManager.TAG,
                "onNotificationMessageClicked is called. " + message.toString());

        UpsPushMessageDispatcher.dispatch(context, UpsPushMessage.builder()
                        .title(message.getTitle())
                        .content(message.getDescription())
                        .company(Company.XIAOMI)
                        .extra(message)
                        .pushType(PushType.NOTIFICATION_MESSAGE)
                        .build(),
                UpsPushMessageType.NOTIFICATION_CLICK);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        UpsLogger.e(UpsPushManager.TAG,
                "onNotificationMessageArrived is called. " + message.toString());
        UpsPushMessageDispatcher.dispatch(context, UpsPushMessage.builder()
                        .title(message.getTitle())
                        .content(message.getContent())
                        .company(Company.XIAOMI)
                        .extra(message)
                        .pushType(PushType.NOTIFICATION_MESSAGE)
                        .build(),
                UpsPushMessageType.NOTIFICATION_ARRIVED);

    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        UpsLogger.e(this,
                "onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);

        UpsCommandMessage upsCommandMessage = new UpsCommandMessage();
        upsCommandMessage.setExtra(message);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            upsCommandMessage.setCode((int) message.getResultCode());
            upsCommandMessage.setCommandResult(cmdArg1);
            upsCommandMessage.setCommandType(CommandType.REGISTER);

        } else if(MiPushClient.COMMAND_UNREGISTER.equals(command)){
            upsCommandMessage.setCode((int) message.getResultCode());
            upsCommandMessage.setCommandResult(cmdArg1);
            upsCommandMessage.setCommandType(CommandType.UNREGISTER);

        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            upsCommandMessage.setCommandType(CommandType.SUBALIAS);
            upsCommandMessage.setCode((int) message.getResultCode());
            upsCommandMessage.setCommandResult(cmdArg1);

        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            upsCommandMessage.setCommandType(CommandType.UNSUBALIAS);
            upsCommandMessage.setCode((int) message.getResultCode());
            upsCommandMessage.setCommandResult(cmdArg1);

        }

        CommandMessageDispatcher.create(context,upsCommandMessage).dispatch();
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        UpsLogger.e(UpsPushManager.TAG,
                "onReceiveRegisterResult is called. " + message.toString());
    }

    @SuppressLint("SimpleDateFormat")
    private static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }
}
