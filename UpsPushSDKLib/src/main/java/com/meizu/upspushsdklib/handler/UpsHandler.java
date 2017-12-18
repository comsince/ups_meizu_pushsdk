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

package com.meizu.upspushsdklib.handler;

public interface UpsHandler {
    /**
     * 订阅接口
     * @param ctx
     * @param appId 应用在各个平台申请的appId
     * @param appKey 应用在各个平台申请的appKey
     * */
    void register(HandlerContext ctx, String appId, String appKey);

    /**
     * 反订阅
     * @param ctx
     * */
    void unRegister(HandlerContext ctx);

    /**
     * 设置别名
     * @param ctx
     * @param alias 别名
     * */
    void setAlias(HandlerContext ctx, String alias);

    /**
     * 取消别名设置
     * @param ctx
     * @param alias 要取消的别名
     * */
    void unSetAlias(HandlerContext ctx, String alias);

    /**
     * 当前逻辑是否适配此机型或者时系统版本
     * @param ctx
     * */
    boolean isCurrentModel(HandlerContext ctx);
    /**
     * handle名称
     * */
    String name();
}
