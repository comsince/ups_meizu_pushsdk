package com.meizu.upspushsdklib.util;


import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.upspushsdklib.UpsPushManager;

public class UpsLogger {

    public static void i(Object tag, String message){
        DebugLogger.i(getLogTAG(tag),message);
    }

    public static void d(Object tag, String message){
        DebugLogger.d(getLogTAG(tag),message);
    }

    public static void e(Object tag, String message){
        DebugLogger.e(getLogTAG(tag),message);
    }

    public static void w(Object tag, String message){
        DebugLogger.w(getLogTAG(tag),message);
    }


    private static String getLogTAG(Object prefix){
        String tag = "";
        if(prefix != null){
            if(prefix instanceof Class){
                tag = ((Class) prefix).getSimpleName();
            } else if(prefix instanceof String){
                tag = (String) prefix;
            } else {
                tag = prefix.getClass().getSimpleName();
            }
        }
        return UpsPushManager.TAG + "->"+tag;
    }
}
