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

package com.meizu.upspushsdklib;

import android.content.Context;
import android.content.Intent;

import com.meizu.cloud.pushsdk.base.IntentReceiver;
import com.meizu.upspushsdklib.receiver.handler.UpsReceiverHandlerProxy;
import com.meizu.upspushsdklib.receiver.handler.UpsReceiverListener;
import com.meizu.upspushsdklib.util.UpsLogger;


public abstract class UpsPushMessageReceiver extends IntentReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context,intent);
        } catch (Exception e){
            UpsLogger.e(this,"UpsPushMessageReceiver Event core error "+e.getMessage());
        }
    }

    @Override
    protected void onHandleIntent(Context context, Intent intent) {
        UpsReceiverHandlerProxy.with(context).registerListener(new UpsReceiverListener() {
            @Override
            public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onThroughMessage "+upsPushMessage);
                UpsPushMessageReceiver.this.onThroughMessage(context,upsPushMessage);
            }

            @Override
            public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onNotificationClicked "+upsPushMessage);
                UpsPushMessageReceiver.this.onNotificationClicked(context,upsPushMessage);
            }

            @Override
            public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onNotificationArrived "+upsPushMessage);
                UpsPushMessageReceiver.this.onNotificationArrived(context,upsPushMessage);
            }

            @Override
            public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onNotificationDeleted "+upsPushMessage);
                UpsPushMessageReceiver.this.onNotificationDeleted(context,upsPushMessage);
            }

            @Override
            public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
                UpsLogger.i(UpsPushMessageReceiver.class,"UpsPushMessageReceiver onUpsCommandResult "+upsCommandMessage);
                UpsPushMessageReceiver.this.onUpsCommandResult(context,upsCommandMessage);
            }
        }).processMessage(intent);
    }

    /**
     * 透传消息
     * @param context
     * @param upsPushMessage
     * */
    public abstract void onThroughMessage(Context context,UpsPushMessage upsPushMessage);

    /**
     * 接收通知栏消息点击回调
     * @param upsPushMessage
     * @param context
     * */
    public abstract void onNotificationClicked(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收通知栏消息到达回调
     * @param upsPushMessage
     * @param context
     * */
    public abstract void onNotificationArrived(Context context, UpsPushMessage upsPushMessage);


    /**
     * 通知栏删除回调
     * @param upsPushMessage
     * @param context
     * */
    public abstract void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage);

    /**
     * 接收订阅，反订阅，别名订阅，取消别名订阅回调
     * @param upsCommandMessage
     * @param context
     * */
    public abstract void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage);
}
