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

import java.util.HashMap;

/**
 * Created by zbin on 17-2-16.
 */

public class ReflectClass {
    private static HashMap<String, Class<?>> mCachedClasses = new HashMap<>();
    private String mClassName;
    private Object mClassObject;

    private ReflectClass() {

    }

    Class<?> getRealClass() throws ClassNotFoundException {
        Class<?> clz;
        if (mClassObject != null) {
            clz = mClassObject.getClass();
        } else {
            clz = mCachedClasses.get(mClassName);
            if (clz == null) {
                clz = Class.forName(mClassName);
                mCachedClasses.put(mClassName, clz);
            }
        }
        return clz;
    }

    Object getReceiver() throws ClassNotFoundException {
        if (mClassObject != null) {
            return mClassObject;
        }
        return getRealClass();
    }

    /**
     * 通过类名获取ReflectClass对象
     *
     * @param className 要反射的类的全限定名
     * @return 返回ReflectClass对象
     */
    public static ReflectClass forName(String className) {
        ReflectClass reflectClass = new ReflectClass();
        reflectClass.mClassName = className;
        return reflectClass;
    }

    /**
     * 通过对象获取ReflectClass对象
     *
     * @param classObject 要反射的对象
     * @return 返回ReflectClass对象
     */
    public static ReflectClass forObject(Object classObject) {
        ReflectClass reflectClass = new ReflectClass();
        reflectClass.mClassObject = classObject;
        return reflectClass;
    }

    /**
     * 通过要反射方法的名字与方法签名来获取ReflectMethod对象
     *
     * @param methodName 要反射的方法名
     * @param types      要反射的方法的参数签名
     * @return 返回ReflectMethod对象
     */
    public ReflectMethod method(String methodName, Class<?>... types) {
        return new ReflectMethod(this, methodName, types);
    }

    /**
     * 通过要反射的成员的名字来获取ReflectField对象
     *
     * @param fieldName 要反射的成员变量的名字
     * @return 返回ReflectField对象
     */
    public ReflectField field(String fieldName) {
        return new ReflectField(this, fieldName);
    }

    /**
     * 通过构造函数方法参数签名获取ReflectConstructor对象
     *
     * @param types 构造函数的方法签名
     * @return 返回ReflectConstructor对象
     */
    public ReflectConstructor constructor(Class<?>... types) {
        return new ReflectConstructor(this, types);
    }
}
