package com.meizu.upspushsdklib;


public enum UpsPushMessageType {

    NOTIFICATION_ARRIVED(1),
    NOTIFICATION_CLICK(2),
    NOTIFICATION_DELETE(3),
    THROUGH_MESSAGE(4);

    int code;

    UpsPushMessageType(int code){
        this.code = code;
    }
}
