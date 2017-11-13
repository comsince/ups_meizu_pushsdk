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
