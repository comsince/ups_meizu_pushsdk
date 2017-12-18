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

package com.meizu.upspushsdklib.util.reflect;

import com.meizu.upspushsdklib.util.UpsLogger;

import java.lang.reflect.Constructor;
public class ReflectConstructor {
    private String TAG = "ReflectConstructor";
    private ReflectClass mReflectClass;
    private Class<?>[] mTypes;

    ReflectConstructor(ReflectClass reflectClass, Class<?>... types) {
        mReflectClass = reflectClass;
        mTypes = types;
    }

    public <T> Result<T> newInstance(Object... args) {
        Result<T> result = new Result<>();
        try {
            Constructor<?> constructor = mReflectClass.getRealClass().getDeclaredConstructor(mTypes);
            constructor.setAccessible(true);
            result.value = (T) constructor.newInstance(args);
            result.ok = true;
        } catch (Exception e) {
            UpsLogger.e(TAG, "newInstance", e);
        }
        return result;
    }
}
