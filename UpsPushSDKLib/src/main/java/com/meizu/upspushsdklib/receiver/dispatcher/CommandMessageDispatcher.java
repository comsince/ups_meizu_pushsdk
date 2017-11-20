package com.meizu.upspushsdklib.receiver.dispatcher;


import android.content.Context;
import android.content.Intent;

import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.handler.impl.AbstractHandler;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;

public abstract class CommandMessageDispatcher<T> {
    protected Context context;
    protected UpsCommandMessage upsCommandMessage;


    public CommandMessageDispatcher(Context context,UpsCommandMessage upsCommandMessage){
        this.context = context.getApplicationContext();
        this.upsCommandMessage = upsCommandMessage;
    }

    public static CommandMessageDispatcher create(Context context,UpsCommandMessage upsCommandMessage){
        CommandType commandType = upsCommandMessage.getCommandType();
        CommandMessageDispatcher commandMessageDispatcher = null;
        switch (commandType){
            case REGISTER:
                commandMessageDispatcher = new UpsPlatformRegister(context,upsCommandMessage);
                break;
            case UNREGISTER:
                commandMessageDispatcher = new UpsPlatformUnRegister(context,upsCommandMessage);
                break;
            case SUBALIAS:
                commandMessageDispatcher = new UpsPlatformSetAlias(context,upsCommandMessage);
                break;
            case UNSUBALIAS:
                commandMessageDispatcher = new UpsPlatformUnSetAlias(context,upsCommandMessage);
                break;
            default:
                commandMessageDispatcher = new DefaultCommandMessageDispatcher(context,upsCommandMessage);
                break;
        }
        return commandMessageDispatcher;
    }

    /**
     * 根据消息类型上报到ups平台并将消息发送至upsPushReceiver
     * */
    public void dispatch(){
        T upsPlatformMessage = upsPlatformMessage();
        UpsLogger.i(this,"current upsCommandType "+upsCommandMessage.getCommandType() + " upsPlatformMessage "+upsPlatformMessage);
        sendMessageToUpsReceiver();
    }



    /**
     * 向平台发起订阅请求,发起网络请求属于同步接口
     * */
    public abstract T upsPlatformMessage();


    public String getUpsAppId(){
        return AbstractHandler.getAppId(context, Company.DEFAULT.name());
    }

    public String getUpsAppKey(){
        return AbstractHandler.getAppKey(context,Company.DEFAULT.name());
    }


    /**
     * 发送消息至UpsPushMessageReceiver
     * */
    private void sendMessageToUpsReceiver(){
        UpsLogger.i(this,"send message "+upsCommandMessage+" to UpsReceiver");
        Intent intent = new Intent(UpsConstants.UPS_MEIZU_PUSH_ON_MESSAGE_ACTION);
        intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD,UpsConstants.UPS_MEIZU_PUSH_METHOD_ON_COMMAND_RESULT);
        intent.putExtra(UpsConstants.UPS_MEIZU_PUSH_EXTRA_UPS_MESSAGE,upsCommandMessage);
        PlatformMessageSender.sendMessageFromBroadcast(context,intent,UpsConstants.UPS_MEIZU_PUSH_ON_MESSAGE_ACTION,context.getPackageName());
    }

    static class DefaultCommandMessageDispatcher extends CommandMessageDispatcher{

        public DefaultCommandMessageDispatcher(Context context, UpsCommandMessage upsCommandMessage) {
            super(context, upsCommandMessage);
        }

        @Override
        public Object upsPlatformMessage() {
            return null;
        }
    }
}
