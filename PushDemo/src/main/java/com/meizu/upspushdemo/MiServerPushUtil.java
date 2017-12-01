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

package com.meizu.upspushdemo;


import com.meizu.cloud.pushsdk.base.ExecutorProxy;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;

public class MiServerPushUtil {

    public static final String APP_SECRET_KEY =" oKVpFtlZnX4gRdYW6PQjLw==";
    private static final String MY_PACKAGE_NAME = "com.meizu.upspushdemo";

    public static void openLauncherActivity(final String registerId){
        ExecutorProxy.get().execute(new Runnable() {
            @Override
            public void run() {
                UpsLogger.e(this,"ups registerid "+registerId);
                Constants.useOfficial();
                Sender sender = new Sender(APP_SECRET_KEY);

                Message message = null;
                try {
                    message = buildCustomMessage();
                    sender.send(message, registerId, 3); //根据regID，发送消息到指定设备上
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static Message buildLauncherMessage() throws Exception {
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(MY_PACKAGE_NAME)
                .passThrough(0)  //消息使用通知栏方式
                .notifyType(1)
                .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_LAUNCHER_ACTIVITY)
                .build();
        return message;
    }

    /**
     * 创建自定义消息内容的格式
     * **/
    private static Message buildCustomMessage() throws Exception {
        //自定义消息体
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description)
                .payload(messagePayload)
                .restrictedPackageName(MY_PACKAGE_NAME)
                .passThrough(0)  //消息使用通知栏方式
                .notifyType(1)
                .build();
        return message;
    }
}
