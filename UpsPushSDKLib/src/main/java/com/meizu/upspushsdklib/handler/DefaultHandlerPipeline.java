package com.meizu.upspushsdklib.handler;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


public final class DefaultHandlerPipeline implements HandlerPipeline {

    private final Map<String, AbstractHandlerContext> name2ctx =
            new HashMap<String, AbstractHandlerContext>(4);
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
        public boolean isCurrentModel() {
            return true;
        }

        @Override
        public UpsHandler handler() {
            return this;
        }

        @Override
        public void register(HandlerContext ctx, String appId, String appKey) {
            if(UpsUtils.isMeizu()){
                DebugLogger.e(UpsPushManager.TAG,"current device model is MEIZU");
                String mzAppId =  getAppId(context,Company.MEIZU.name());
                String mzAppKey = getAppKey(context,Company.MEIZU.name());
                if(TextUtils.isEmpty(mzAppId) || TextUtils.isEmpty(mzAppKey)){
                    //本地获取配置信息
                    mzAppId = UpsUtils.getMetaIntValueByName(context, UpsConstants.MZ_APP_ID);
                    mzAppKey = UpsUtils.getMetaStringValueByName(context,UpsConstants.MZ_APP_KEY);
                    putAppId(context,Company.MEIZU.name(),appId);
                    putAppKey(context,Company.MEIZU.name(),appKey);
                }

                if(TextUtils.isEmpty(mzAppId) || TextUtils.isEmpty(mzAppKey)){
                    //从统一push平台获取
                }

                ctx.fireRegister(mzAppId,mzAppKey);
            }
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
        public boolean isCurrentModel() {
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
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAlias(HandlerContext ctx,String alias) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void unSetAlias(HandlerContext ctx,String alias) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String name() {
            return Company.DEFAULT.name();
        }
    }
}
