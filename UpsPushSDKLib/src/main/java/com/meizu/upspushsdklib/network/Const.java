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

package com.meizu.upspushsdklib.network;

/**
 * Constant values and strings.
 */
class Const {
    static final String DEFAULT_USER_AGENT = "com.goebl.david.Webb/1.0";
    static final String APP_FORM = "application/x-www-form-urlencoded";
    static final String APP_JSON = "application/json";
    static final String APP_BINARY = "application/octet-stream";
    static final String TEXT_PLAIN = "text/plain";
    static final String HDR_CONTENT_TYPE = "Content-Type";
    static final String HDR_CONTENT_ENCODING = "Content-Encoding";
    static final String HDR_ACCEPT_ENCODING = "Accept-Encoding";
    static final String HDR_ACCEPT = "Accept";
    static final String HDR_USER_AGENT = "User-Agent";
    static final String UTF8 = "utf-8";

    static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    static final Class BYTE_ARRAY_CLASS = EMPTY_BYTE_ARRAY.getClass();
    /** Minimal number of bytes the compressed content must be smaller than uncompressed */
    static final int MIN_COMPRESSED_ADVANTAGE = 80;
}
