package com.dr.navigationapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.baidu.mapapi.SDKInitializer;
import com.dr.navigationapplication.R;
import com.dr.navigationapplication.costant_interface.Constant;

public class ReadyActivity extends Activity {

    private static final String TAG = "ReadyActivity";

    //延时 handler
    private static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //百度地图SDK初始化 必须在 setContentView 之前
        SDKInitializer.initialize(getApplicationContext());

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


    /**
     * 主进程的handler
     * @return Handler
     */
    public static Handler getHandler(){
        if(handler != null) return handler;
        else return null;
    }

}
