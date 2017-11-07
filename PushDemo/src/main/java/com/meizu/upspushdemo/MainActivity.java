package com.meizu.upspushdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.hw.HwPushClient;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btnRegister;
    private Button btnUnRegister;
    private Button btnSetAlias;
    private Button btnUnsetAlias;

    private String xmAppId;
    private String xmAppKey;
    private String mzAppId;
    private String mzAppKey;

    private HwPushClient hwPushClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMetaData();
        hwPushClient = new HwPushClient(this);
        hwPushClient.connect();
    }

    private void initView(){
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnUnRegister = (Button) findViewById(R.id.btn_unregister);
        btnUnRegister.setOnClickListener(this);
        btnSetAlias = (Button) findViewById(R.id.btn_set_alias);
        btnSetAlias.setOnClickListener(this);
        btnUnsetAlias = (Button) findViewById(R.id.btn_unset_alias);
        btnUnsetAlias.setOnClickListener(this);
    }

    private void initMetaData(){
        xmAppId = UpsUtils.getMetaStringValueByName(this, UpsConstants.XM_APP_ID);
        xmAppKey = UpsUtils.getMetaStringValueByName(this,UpsConstants.XM_APP_KEY);
        mzAppId = UpsUtils.getMetaIntValueByName(this, UpsConstants.MZ_APP_ID);
        mzAppKey = UpsUtils.getMetaStringValueByName(this,UpsConstants.MZ_APP_KEY);
        UpsUtils.getMetaIntValueByName(this,"com.huawei.hms.client.appid");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_unregister:
                UpsPushManager.unRegister(this);
                break;
            case R.id.btn_register:
//                MiPushClient.registerPush(this,xmAppId,xmAppKey);
//                PushManager.register(this,mzAppId,mzAppKey);
//                hwPushClient.getTokenSync();
                UpsPushManager.register(this,"","");
                break;
            case R.id.btn_set_alias:
                UpsPushManager.setAlias(this,"ups");
                break;
            case R.id.btn_unset_alias:
                UpsPushManager.unSetAlias(this,"ups");
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        hwPushClient.disconnect();
    }

}
