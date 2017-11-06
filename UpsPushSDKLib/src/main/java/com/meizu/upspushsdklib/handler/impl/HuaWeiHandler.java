package com.meizu.upspushsdklib.handler.impl;

import com.meizu.upspushsdklib.handler.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;

public class HuaWeiHandler extends AbstractHandler{


    @Override
    public void register(HandlerContext context, String appId, String appKey) {

    }

    @Override
    public void unRegister(HandlerContext context) {

    }

    @Override
    public void setAlias(HandlerContext context, String alias) {

    }

    @Override
    public void unSetAlias(HandlerContext context, String alias) {

    }

    @Override
    public boolean isCurrentModel() {
        return false;
    }

    @Override
    public String name() {
        return Company.HUAWEI.name();
    }
}
