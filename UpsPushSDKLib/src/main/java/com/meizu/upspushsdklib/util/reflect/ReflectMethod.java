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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;


public class ReflectMethod {
    private String TAG = "ReflectMethod";
    private static HashMap<String, Method> mCachedMethods = new HashMap<>();
    private ReflectClass mReflectClass;
    private String mMethodName;
    private Class<?>[] mTypes;

    ReflectMethod(ReflectClass reflectClass, String name, Class<?>... types) {
        mReflectClass = reflectClass;
        mMethodName = name;
        mTypes = types;
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class)
                    continue;
                if (wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i])))
                    continue;
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isSimilarSignature(Method possiblyMatchingMethod,
                                       String desiredMethodName,
                                       Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) &&
                match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    private Method similarMethod()
            throws NoSuchMethodException, ClassNotFoundException {
        Class<?> clz = mReflectClass.getRealClass();
        for (Method method : clz.getMethods()) {
            if (isSimilarSignature(method, mMethodName, mTypes)) {
                return method;
            }
        }
        for (Method method : clz.getDeclaredMethods()) {
            if (isSimilarSignature(method, mMethodName, mTypes)) {
                return method;
            }
        }
        throw new NoSuchMethodException("No similar method " + mMethodName +
                " with params " + Arrays.toString(mTypes) +
                " could be found on type " + clz);
    }

    private String getKey() throws
            ClassNotFoundException {
        StringBuffer buffer = new StringBuffer(mReflectClass.getRealClass().getName());
        buffer.append(mMethodName);
        for (Class<?> type : mTypes) {
            buffer.append(type.getName());
        }
        return buffer.toString();
    }

    private Method recursiveFindMethod() throws
            NoSuchMethodException, ClassNotFoundException {
        for (Class<?> clz = mReflectClass.getRealClass(); clz != Object.class; clz = clz.getSuperclass()) {
            try {
                return clz.getDeclaredMethod(mMethodName, mTypes);
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        throw new NoSuchMethodException("in recursiveFindMethod");
    }

    /**
     * 用于静态或非静态方法的调用
     *
     * @param args 调用参数
     * @param <T>  调用结果的参数类型
     * @return 反射调用的结果
     */
    public <T> Result<T> invoke(Object... args) {
        Result<T> result = new Result<>();
        try {
            String key = getKey();
            Method method = mCachedMethods.get(key);
            if (method == null) {
                if (mTypes.length == args.length) {
                    method = recursiveFindMethod();
                } else {
                    if (args.length > 0) {
                        mTypes = new Class<?>[args.length];
                        for (int i = 0; i < args.length; ++i) {
                            mTypes[i] = args[i].getClass();
                        }
                    }
                    method = similarMethod();
                }
                mCachedMethods.put(key, method);
            }
            method.setAccessible(true);
            result.value = (T) method.invoke(mReflectClass.getReceiver(), args);
            result.ok = true;
        } catch (Exception e) {
            UpsLogger.e(TAG, "invoke", e);
        }
        return result;
    }

    private Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }
        return type;
    }

    private class NULL {
    }
}
