package com.meizu.upspushdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends Activity{

    TextView tvExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvExtra = (TextView) findViewById(R.id.tv_extra);
        tvExtra.setText("key "+getIntent().getStringExtra("key"));
    }

}
