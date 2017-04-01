package com.tkkj.tkeyes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import com.tkkj.tkeyes.adapter.MainAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.timePicker)
    TimePicker timePicker;
    @BindView(R.id.list_set)
    ListView listSet;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;


    private Context context;



    private String[] title = {"启动设备","干预设备","测试设备","连接设备","关于我们"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("功能操作");


        initData();
    }


    private void initData() {
    context = MainActivity.this;
        MainAdapter adapter = new MainAdapter(MainActivity.this,title);
        listSet.setAdapter(adapter);
    }





}
