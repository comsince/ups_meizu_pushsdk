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


abstract class AbstractHandlerContext implements HandlerContext{



    volatile AbstractHandlerContext prev;
    volatile AbstractHandlerContext next;
    //handler context name
    private final String name;
    private final DefaultHandlerPipeline pipeline;

    public AbstractHandlerContext(String name, DefaultHandlerPipeline pipeline) {
        this.name = name;
        this.pipeline = pipeline;
    }

    @Override
    public void fireRegister(String appId, String appKey) {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeRegister(appId,appKey);
    }

    @Override
    public void fireUnRegister() {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeUnRegister();
    }

    @Override
    public void fireSetAlias(String alias) {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeSetAlias(alias);
    }

    @Override
    public void fireUnSetAlias(String alias) {
        AbstractHandlerContext next = findNextHandlerContext();
        next.invokeUnSetAlias(alias);
    }

    @Override
    public HandlerPipeline pipeline() {
        return pipeline;
    }

    private AbstractHandlerContext findNextHandlerContext() {
        AbstractHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!ctx.isCurrentModel());
        return ctx;
    }

    private void invokeRegister(String appId,String appKey){
          handler().register(this,appId,appKey);
    }


    private void invokeUnRegister(){
        handler().unRegister(this);
    }

    private void invokeSetAlias(String alias){
        handler().setAlias(this,alias);
    }

    private void invokeUnSetAlias(String alias){
        handler().unSetAlias(this,alias);
    }

}
