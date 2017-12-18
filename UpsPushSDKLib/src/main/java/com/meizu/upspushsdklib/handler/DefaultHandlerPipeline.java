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

import android.content.Context;

import com.meizu.upspushsdklib.Company;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


final class DefaultHandlerPipeline implements HandlerPipeline {

    private Map<String, AbstractHandlerContext> name2ctx =
            new HashMap<String, AbstractHandlerContext>();
    private AbstractHandlerContext head;
    private AbstractHandlerContext tail;

    // 应用application context
    private Context context;

    public DefaultHandlerPipeline(Context context){
        this();
        this.context = context.getApplicationContext();
    }

    public DefaultHandlerPipeline(){
        head = new HeadContext(this);
        tail = new TailContext(this);

        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void fireRegister(String appId, String appKey) {
        head.fireRegister(appId,appKey);
    }

    @Override
    public void fireUnRegister() {
        head.fireUnRegister();
    }

    @Override
    public void fireSetAlias(String alias) {
        head.fireSetAlias(alias);
    }

    @Override
    public void fireUnSetAlias(String alias) {
        head.fireUnSetAlias(alias);
    }

    @Override
    public HandlerPipeline addLast(UpsHandler... handlers) {
        if (handlers == null) {
            throw new NullPointerException("handlers not null");
        }

        for (UpsHandler h: handlers) {
            if (h == null) {
                break;
            }
            addLast(h.name(),h);
        }

        return this;
    }

    @Override
    public HandlerPipeline addLast(String name, UpsHandler handler) {
        checkDuplicateName(name);
        AbstractHandlerContext newCtx = new DefaultHandlerContext(this,name,handler);
        addLast0(name,newCtx);
        return this;
    }

    @Override
    public HandlerPipeline addFirst(UpsHandler... handlers) {
        return this;
    }

    @Override
    public Context context() {
        return context;
    }

    private void addLast0(final String name, AbstractHandlerContext newCtx) {

        AbstractHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;

        name2ctx.put(name, newCtx);
    }


    @Override
    public Executor executor() {
        return null;
    }


    private void checkDuplicateName(String name) {
        if (name2ctx.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    /**
     * 处理appId appKey初始化以及存储工作
     * */
    static final class HeadContext extends AbstractHandlerContext implements UpsHandler{

        Context context;

        public HeadContext(DefaultHandlerPipeline pipeline) {
            super(Company.DEFAULT.name(), pipeline);
            this.context = pipeline.context();
        }

        @Override
        public boolean isCurrentModel(HandlerContext ctx) {
            return true;
        }

        @Override
        public boolean isNextHandlerContext() {
            return isCurrentModel(null);
        }

        @Override
        public UpsHandler handler() {
            return this;
        }

        @Override
        public void register(HandlerContext ctx, String appId, String appKey) {
            ctx.fireRegister(appId,appKey);
        }

        @Override
        public void unRegister(HandlerContext ctx) {
            ctx.fireUnRegister();
        }

        @Override
        public void setAlias(HandlerContext ctx, String alias) {
             ctx.fireSetAlias(alias);
        }

        @Override
        public void unSetAlias(HandlerContext ctx, String alias) {
             ctx.fireUnSetAlias(alias);
        }


        @Override
        public String name() {
            return Company.DEFAULT.name();
        }
    }

    static final class TailContext extends AbstractHandlerContext implements UpsHandler{


        public TailContext(DefaultHandlerPipeline pipeline) {
            super(Company.DEFAULT.name(), pipeline);
        }

        @Override
        public boolean isCurrentModel(HandlerContext ctx) {
            return false;
        }

        @Override
        public boolean isNextHandlerContext() {
            return false;
        }

        @Override
        public UpsHandler handler() {
            return this;
        }

        @Override
        public void register(HandlerContext ctx,String appId, String appKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void unRegister(HandlerContext ctx) {

        }

        @Override
        public void setAlias(HandlerContext ctx, String alias) {

        }

        @Override
        public void unSetAlias(HandlerContext ctx, String alias) {

        }

        @Override
        public String name() {
            return Company.DEFAULT.name();
        }
    }
}
