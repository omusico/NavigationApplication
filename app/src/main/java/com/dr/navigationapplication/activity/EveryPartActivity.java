package com.dr.navigationapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dr.navigationapplication.R;
import com.dr.navigationapplication.custom_adapter.EveryPartAdapter;
import com.dr.navigationapplication.custom_view.MyGridView;
import com.dr.navigationapplication.dao.daoimpl.Data;
import com.dr.navigationapplication.util.PlacePhotoDownloadTask;


public class EveryPartActivity extends Activity {

    private static final String TAG = "EveryPartActivity";
    private ImageView imageView;
    private TextView textView;
    private TextView textViewDescription;
    private String string;
    private Button button;
    private Button videoButton;
    private MyGridView gridView;
    private EveryPartAdapter adapter;
    private int pid;
    private String imageUrl;

    public EveryPartActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_part);

        imageView = (ImageView) findViewById(R.id.activity_every_part_image_view);
        textView = (TextView) findViewById(R.id.activity_every_part_text_view);
        textViewDescription = (TextView) findViewById(R.id.activity_every_part_description_text_view);
        gridView = (MyGridView) findViewById(R.id.activity_every_part_grid_view);

        Intent intent = getIntent();
        pid = intent.getIntExtra("PlaceID", pid);
        imageUrl = intent.getStringExtra("MainPlace");
        String intro = intent.getStringExtra("intro");
        String name = intent.getStringExtra("name");


        textView.setText(name);
        textViewDescription.setText(intro);
        new PlacePhotoDownloadTask(imageView).execute(imageUrl);
        this.initAdapter();
        gridView.setAdapter(adapter);
        button = (Button) findViewById(R.id.activity_every_part_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        videoButton = (Button) findViewById(R.id.activity_every_part_video_button);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /*Intent intent = new Intent(EveryPartActivity.this, VitamioActivity.class);
                    startActivity(intent);*/
            }
        });
    }

    private void initAdapter() {
        adapter = new EveryPartAdapter(this);
        for (int i = 0; i < Data.viewTableList.size(); i++) {
            if (Data.viewTableList.get(i).getPid() == pid) {
                EveryPartAdapter.EveryPartClass everyPartClass;
                everyPartClass = adapter.new EveryPartClass();
                everyPartClass.name = Data.viewTableList.get(i).getName();
                everyPartClass.imageUrl = Data.viewTableList.get(i).getImage();
                everyPartClass.imageDownloadFlags = Integer.valueOf(0);
                adapter.everyPartClassList.add(everyPartClass);
                Log.i(TAG, Data.viewTableList.get(i).toString());
            }
        }
    }
}
