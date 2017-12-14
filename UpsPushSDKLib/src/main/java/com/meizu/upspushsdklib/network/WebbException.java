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
 * Runtime exception wrapping the real exception thrown by HttpUrlConnection et al.
 *
 * @author hgoebl
 */
public class WebbException extends RuntimeException {

    private Response response;

    public WebbException(String message) {
        super(message);
    }

    public WebbException(String message, Response response) {
        super(message);
        this.response = response;
    }

    public WebbException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebbException(Throwable cause) {
        super(cause);
    }

    /**
     * Get the Response object
     * (only available if exception has been raised by {@link com.meizu.upspushsdklib.network.Request#ensureSuccess()}.
     *
     * @return the <code>Response</code> object filled with error information like statusCode and errorBody.
     */
    public Response getResponse() {
        return response;
    }
}
