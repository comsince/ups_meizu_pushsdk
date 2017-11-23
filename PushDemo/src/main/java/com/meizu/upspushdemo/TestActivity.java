package com.meizu.upspushdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.meizu.upspushsdklib.util.UpsLogger;

public class TestActivity extends Activity{

    TextView tvExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvExtra = (TextView) findViewById(R.id.tv_extra);
        tvExtra.setText("key "+getIntent().getStringExtra("key"));

        Uri uri =getIntent().getData();
        if(uri != null){
            UpsLogger.e(this, "scheme: "+uri.getScheme());
            UpsLogger.e(this, "host: "+uri.getHost());
            UpsLogger.e(this, "port: "+uri.getPort());
            UpsLogger.e(this, "path: "+uri.getPath());
            UpsLogger.e(this, "queryString: "+uri.getQuery());
            UpsLogger.e(this, "queryParameter: "+uri.getQueryParameter("key"));

            tvExtra.setText("scheme: "+uri.getScheme() +"\n" +
                    "host: "+uri.getHost()+"\n" +
                    "path: "+uri.getPath()+"\n" +
                    "queryString: "+uri.getQuery()
            );
        }

    }

}
