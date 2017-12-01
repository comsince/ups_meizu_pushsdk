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
            UpsLogger.e(this,"current message "+message);
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
