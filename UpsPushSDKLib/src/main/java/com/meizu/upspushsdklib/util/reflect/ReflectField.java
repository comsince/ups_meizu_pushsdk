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

import java.lang.reflect.Field;

public class ReflectField {
    private String TAG = "ReflectField";
    private ReflectClass mReflectClass;
    private String mFieldName;

    ReflectField(ReflectClass reflectClass, String fieldName) {
        mReflectClass = reflectClass;
        mFieldName = fieldName;
    }

    private Field getField() throws ClassNotFoundException, NoSuchFieldException {
        Field field = mReflectClass.getRealClass().getDeclaredField(mFieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * 设置静态/非静态成员变量的值
     *
     * @param value 　被设置的值
     * @param <T>   field的数据类型
     * @return 反射调用的结果
     */
    public <T> Result<T> set(T value) {
        Result<T> result = new Result<>();
        try {
            Field field = getField();
            field.set(mReflectClass.getReceiver(), value);
            result.value = value;
            result.ok = true;
        } catch (Exception e) {
            UpsLogger.e(TAG, "set", e);
        }
        return result;
    }

    /**
     * 获取静态/非静态成员变量的值
     *
     * @param <T> 成员的类型
     * @return 反射调用的结果
     */
    public <T> Result<T> get() {
        Result<T> result = new Result<>();
        try {
            Field field = getField();
            result.value = (T) field.get(mReflectClass.getReceiver());
            result.ok = true;
        } catch (Exception e) {
            UpsLogger.e(TAG, "get", e);
        }
        return result;
    }
}
