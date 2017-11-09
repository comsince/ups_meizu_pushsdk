package com.meizu.upspushsdklib.receiver.handler;

import android.content.Context;
import android.content.Intent;

import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractUpsReceiverHandler<T> implements UpsReceiverHandler{

    private Context context;
    private Map<Integer,String> messageHandlerMap;
    private UpsReceiverListener upsReceiverListener;

    public AbstractUpsReceiverHandler(Context context,UpsReceiverListener receiverListener) {
        if(context == null){
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.context = context.getApplicationContext();
        messageHandlerMap = new HashMap<>();
        this.upsReceiverListener = receiverListener;
    }

    public abstract T getMessage(Intent intent);

    public abstract void sendMessage(T message);

    @Override
    public boolean sendMessage(Intent intent) {
        boolean flag = false;
        if(messageMatch(intent)){
            UpsLogger.e(this,"current Handler name is "+getProcessorName());
            T message = getMessage(intent);
            UpsLogger.i(this,"current message "+message);
            sendMessage(message);
            flag = true;
        }
        return flag;
    }

    protected UpsReceiverListener upsReceiverListener(){
        return upsReceiverListener;
    }

    protected Context context(){
        return context;
    }

    protected String getIntentMethod(Intent intent){
        return intent.getStringExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD);
    }

}
