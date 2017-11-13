package com.meizu.upspushsdklib.handler;

final class DefaultHandlerContext extends AbstractHandlerContext{

    private UpsHandler handler;

    public DefaultHandlerContext(DefaultHandlerPipeline pipeline,String name, UpsHandler handler) {
        super(name, pipeline);
        this.handler = handler;
    }


    @Override
    public boolean isCurrentModel() {
        return handler.isCurrentModel();
    }

    @Override
    public UpsHandler handler() {
        return handler;
    }
}
