package com.dr.navigationapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dr.navigationapplication.R;
import com.dr.navigationapplication.costant_interface.Constant;

public class ReadyActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constant.READY_GO:
                        Intent intent = new Intent(ReadyActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };

        handler.sendEmptyMessageDelayed(Constant.READY_GO, 2000);

    }

}
