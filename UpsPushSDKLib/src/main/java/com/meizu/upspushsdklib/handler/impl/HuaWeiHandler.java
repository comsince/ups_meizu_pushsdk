package com.meizu.upspushsdklib.handler.impl;

import com.meizu.upspushsdklib.Company;

public class HuaWeiHandler extends AbstractHandler{


    @Override
    public boolean isCurrentModel() {
        return false;
    }

    @Override
    public String name() {
        return Company.HUAWEI.name();
    }
}
