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

package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;

import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.util.UpsUtils;


public class MeizuHandler extends AbstractHandler{

    @Override
    public void onRegister(Context context, String appId, String appKey) {
        PushManager.register(context,appId,appKey);
    }

    @Override
    public void onUnRegister(Context context, String appId, String appKey) {
        PushManager.unRegister(context,appId,appKey);
    }

    @Override
    public void onSetAlias(Context context, String appId, String appKey, String alias) {
        PushManager.subScribeAlias(context,appId,appKey,PushManager.getPushId(context),alias);
    }

    @Override
    public void onUnsetAlias(Context context, String appId, String appKey, String alias) {
        PushManager.unSubScribeAlias(context,appId,appKey,PushManager.getPushId(context),alias);
    }

    @Override
    public boolean isCurrentModel(HandlerContext ctx) {
        return UpsUtils.isMeizu(ctx.pipeline().context());
    }

    @Override
    public String name() {
        return Company.MEIZU.name();
    }
}
