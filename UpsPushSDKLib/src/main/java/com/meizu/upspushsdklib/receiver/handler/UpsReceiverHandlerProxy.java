/*
 * MIT License
 *
 * Copyright (c) [2017] [Meizu.inc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
