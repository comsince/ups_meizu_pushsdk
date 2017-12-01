/*
 * MIT License
 *
 * Copyright (c) [2017] [Meizu.inc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
