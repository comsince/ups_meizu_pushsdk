package com.meizu.upspushsdklib;


public enum PushType {

    NOTIFICATION_MESSAGE(0),
    THROUGH_MESSAGE(1);

    int code;

    PushType(int code){
        this.code = code;
    }
}
