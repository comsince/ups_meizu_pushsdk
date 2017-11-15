package com.meizu.upspushsdklib.hw;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.PushException;
import com.huawei.hms.support.api.push.TokenResult;
import com.meizu.upspushsdklib.handler.UpsBootstrap;
import com.meizu.upspushsdklib.util.UpsLogger;

import static android.os.Looper.getMainLooper;

public class HwPushClient implements HuaweiApiClient.OnConnectionFailedListener {

    private Context mContext;
    /**
     * 华为Push Client
     * */
    private HuaweiApiClient client;

    public HwPushClient(Context context){
        this(context,null);
    }

    public HwPushClient(Context context, HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        //检测传进的context是否为activity
        if(context == null){
            throw new IllegalArgumentException(" context not null");
        }
//        if(context instanceof Activity){
//            throw new IllegalArgumentException(" context must type of Activity");
//        }
        this.mContext = context;
        buildHwApiClient(mContext.getApplicationContext(),connectionCallbacks);
    }

    /**
     * 创建华为移动服务client实例用以使用华为push服务
     * 需要指定api为HuaweiId.PUSH_API
     * 连接回调以及连接失败监听
     * */
    private HuaweiApiClient buildHwApiClient(Context context, HuaweiApiClient.ConnectionCallbacks connectionCallbacks){
        client = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(connectionCallbacks)
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
    public synchronized void getTokenSync() {
        if(!client.isConnected()) {
            UpsLogger.i(this, "获取token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        UpsBootstrap.executor().execute(new Runnable() {
            @Override
            public void run() {
                UpsLogger.i(this, "同步接口获取push token");
                PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
                TokenResult result = tokenResult.await();
                if(result.getTokenRes().getRetCode() == 0) {
                    //当返回值为0的时候表明获取token结果调用成功
                    UpsLogger.i(this, "获取push token 成功，等待广播 并关闭链接");
                    client.disconnect();
                }
            }
        });


    }


    public synchronized void deleteToken(){
        if(!client.isConnected()) {
            UpsLogger.i(this, "注销token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        //调用删除token需要传入通过getToken接口获取到token，并且需要对token进行非空判断
        /*if (!TextUtils.isEmpty(token)){
            try {
                HuaweiPush.HuaweiPushApi.deleteToken(client, token);
            } catch (PushException e) {
                UpsLogger.i(this, "删除Token失败:" + e.getMessage());
            }
        }*/

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
        if(!client.isConnected() && !client.isConnecting()){
            client.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        UpsLogger.i(this, "HuaweiApiClient connection failed code " + connectionResult.getErrorCode());
//        if(HuaweiApiAvailability.getInstance().isUserResolvableError(connectionResult.getErrorCode())) {
//            final int errorCode = connectionResult.getErrorCode();
//            new Handler(getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    // 此方法必须在主线程调用
//                    HuaweiApiAvailability.getInstance().resolveError((Activity) mContext, errorCode, 1000);
//                }
//            });
//        } else {
//            //其他错误码请参见开发指南或者API文档
//        }
    }
}
