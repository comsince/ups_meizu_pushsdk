package com.meizu.upspushsdklib.receiver.handler;


import android.content.Context;
import android.content.Intent;

import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;

import java.util.HashMap;
import java.util.Map;

public class UpsReceiverHandlerProxy {

    static volatile UpsReceiverHandlerProxy singleton;
    private Context mContext;
    private Map<String,UpsReceiverHandler> managerHashMap = new HashMap<>();
    private UpsReceiverListener upsReceiverListenerProxy;

    public UpsReceiverHandlerProxy(Context context) {
        this.mContext = context.getApplicationContext();
        UpsReceiverListener upsReceiverListener = new DefaultReceiverListener();
        addHandler(new UpsCommandMessageHandler(mContext,upsReceiverListener));
        addHandler(new UpsNotificationClickHandler(mContext,upsReceiverListener));
        addHandler(new UpsNotificationArrivedHandler(mContext,upsReceiverListener));
        addHandler(new UpsNotificationDeleteHandler(mContext,upsReceiverListener));
        addHandler(new UpsThroughMessageHandler(mContext,upsReceiverListener));
    }

    public static UpsReceiverHandlerProxy with(Context context) {
        if (singleton == null) {
            synchronized (UpsReceiverHandlerProxy.class) {
                if (singleton == null) {
                    UpsLogger.i(UpsReceiverHandlerProxy.class,"UpsReceiverHandlerProxy init");
                    singleton = new UpsReceiverHandlerProxy(context);
                }
            }
        }
        return singleton;
    }

    private void addHandler(UpsReceiverHandler upsReceiverHandler){
        managerHashMap.put(upsReceiverHandler.getProcessorName(),upsReceiverHandler);
    }

    public UpsReceiverHandlerProxy registerListener(UpsReceiverListener upsReceiverListener){
        this.upsReceiverListenerProxy = upsReceiverListener;
        return this;
    }

    public void processMessage(Intent intent){
        try {
            String method = intent.getStringExtra(UpsConstants.UPS_MEIZU_PUSH_METHOD);
            UpsLogger.e(this,"receive method "+method);
            for (Map.Entry<String,UpsReceiverHandler> entry : managerHashMap.entrySet()){
                UpsReceiverHandler upsReceiverHandler = entry.getValue();
                boolean matchResult = upsReceiverHandler.sendMessage(intent);
                if(matchResult){
                    break;
                }
            }
        } catch (Exception e){
            UpsLogger.e(this,"process message error "+e.getMessage());
        }
    }


    class DefaultReceiverListener implements UpsReceiverListener{

        @Override
        public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {
            upsReceiverListenerProxy.onThroughMessage(context,upsPushMessage);
        }

        @Override
        public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {
            upsReceiverListenerProxy.onNotificationClicked(context,upsPushMessage);
        }

        @Override
        public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {
            upsReceiverListenerProxy.onNotificationArrived(context,upsPushMessage);
        }

        @Override
        public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {
            upsReceiverListenerProxy.onNotificationDeleted(context,upsPushMessage);
        }

        @Override
        public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
            upsReceiverListenerProxy.onUpsCommandResult(context,upsCommandMessage);
        }
    }
}
