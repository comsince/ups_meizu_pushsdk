package com.meizu.upspushsdklib.hw;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;
import com.meizu.cloud.pushinternal.DebugLogger;

import static android.os.Looper.getMainLooper;
import static com.meizu.upspushsdklib.UpsPushManager.TAG;

public class HwPushClient implements HuaweiApiClient.ConnectionCallbacks, HuaweiApiClient.OnConnectionFailedListener {

    private Context mContext;
    /**
     * 华为Push Client
     * */
    private HuaweiApiClient client;

    public HwPushClient(Context context) {
        //检测传进的context是否为activity
        if(context == null){
            throw new IllegalArgumentException(" context not null");
        }
//        if(context instanceof Activity){
//            throw new IllegalArgumentException(" context must type of Activity");
//        }
        this.mContext = context;
        buildHwApiClient(mContext.getApplicationContext());
    }

    /**
     * 创建华为移动服务client实例用以使用华为push服务
     * 需要指定api为HuaweiId.PUSH_API
     * 连接回调以及连接失败监听
     * */
    private HuaweiApiClient buildHwApiClient(Context context){
        client = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        return client;
    }

    /**
     * 使用同步接口来获取pushtoken
     * 结果通过广播的方式发送给应用，不通过标准接口的pendingResul返回
     * CP可以自行处理获取到token
     * 同步获取token和异步获取token的方法CP只要根据自身需要选取一种方式即可
     */
    public void getTokenSync() {
        if(!client.isConnected()) {
            DebugLogger.i(TAG, "获取token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        //需要在子线程中调用函数
        new Thread() {
            public void run() {
                DebugLogger.i(TAG, "同步接口获取push token");
                PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
                TokenResult result = tokenResult.await();
                if(result.getTokenRes().getRetCode() == 0) {
                    //当返回值为0的时候表明获取token结果调用成功
                    DebugLogger.i(TAG, "获取push token 成功，等待广播");
                }
            }
        }.start();
    }

    /**
     * 建议在onDestroy的时候停止连接华为移动服务
     * 业务可以根据自己业务的形态来确定client的连接和断开的时机，但是确保connect和disconnect必须成对出现
     * */
    public void disconnect(){
        client.disconnect();
    }

    /**
     * 建议在oncreate的时候连接华为移动服务
     * 业务可以根据自己业务的形态来确定client的连接和断开的时机，但是确保connect和disconnect必须成对出现
     * */
    public void connect(){
        client.connect();
    }

    @Override
    public void onConnected() {
        DebugLogger.i(TAG,"hwClient connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        //HuaweiApiClient断开连接的时候，业务可以处理自己的事件
        DebugLogger.i(TAG, "HuaweiApiClient onConnectionSuspended code "+ i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        DebugLogger.i(TAG, "HuaweiApiClient connection failed code " + connectionResult.getErrorCode());
        if(HuaweiApiAvailability.getInstance().isUserResolvableError(connectionResult.getErrorCode())) {
            final int errorCode = connectionResult.getErrorCode();
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    // 此方法必须在主线程调用
                    HuaweiApiAvailability.getInstance().resolveError((Activity) mContext, errorCode, 1000);
                }
            });
        } else {
            //其他错误码请参见开发指南或者API文档
        }
    }
}
