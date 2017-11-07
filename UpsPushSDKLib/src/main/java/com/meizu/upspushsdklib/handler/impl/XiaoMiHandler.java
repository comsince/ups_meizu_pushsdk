package com.meizu.upspushsdklib.handler.impl;

import com.meizu.upspushsdklib.handler.Company;

public class XiaoMiHandler extends AbstractHandler{


    @Override
    public boolean isCurrentModel() {
        return false;
    }

    @Override
    public String name() {
        return Company.XIAOMI.name();
    }
}
