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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorProxy {
    private static ExecutorService executor;
    private static int threadCount = 2; // Minimum amount of threads.

    /**
     * If the executor is null creates a
     * new executor.
     *
     * @return the executor
     */
    public static ExecutorService getExecutor() {
        synchronized (ExecutorProxy.class) {
            if (executor == null) {
                executor = Executors.newScheduledThreadPool(threadCount);
            }
        }
        return executor;
    }

    /**
     * Sends a runnable to the executor service.
     *
     * @param runnable the runnable to be queued
     */
    public static void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    /**
     * Sends a callable to the executor service and
     * returns a Future.
     *
     * @param callable the callable to be queued
     * @return the future object to be queried
     */
    public static Future futureCallable(Callable callable) {
        return getExecutor().submit(callable);
    }

    /**
     * Shuts the executor service down and resets
     * the executor to a null state.
     */
    public static void shutdown() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }

    /**
     * Returns the status of the executor.
     *
     * @return executor is alive or not
     */
    public static boolean status() {
        return !(executor == null || executor.isShutdown());
    }

    /**
     * Changes the amount of threads the
     * scheduler will be able to use.
     *
     * NOTE: This can only be set before the
     * scheduler is first accessed, after this
     * point the function will not effect anything.
     *
     * @param count the thread count
     */
    public static void setThreadCount(final int count) {
        threadCount = count;
    }
}
