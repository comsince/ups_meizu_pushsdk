package com.meizu.upspushsdklib.handler;

/**
 * Created by liaojinlong on 17-11-6.
 */

public enum Company {
    DEFAULT(0),
    MEIZU(1),
    XIAOMI(2),
    HUAWEI(3);

    private int code;

    Company(int code){
        this.code = code;
    }

    public int code(){
        return code;
    }
}
