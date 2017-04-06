package com.tkkj.tkeyes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tkkj.tkeyes.NetService.AsyncHttpClientUtils;
import com.tkkj.tkeyes.adapter.MainAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {

    // 注意：控件的修饰类型不能是：private 或 static
    // 否则会报错误： @BindView fields must not be private or static.
    @BindView(R.id.list_set)
    ListView listSet;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;


    private Context context;
    private ListView listView;
    private AsyncHttpClientUtils clientUtils;

    private String[] title = {"启动设备","干预设备","测试设备","连接设备","联系我们"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //必须在setContentView()之后使用
        //你是不是改过这个方法？

        ButterKnife.bind(this);
        setTitle("功能操作");
        initData();
        clientUtils = AsyncHttpClientUtils.getInstance();
    }

    private void initData() {
        context = MainActivity.this;
        MainAdapter adapter = new MainAdapter(MainActivity.this,title);
        listSet.setAdapter(adapter);

    }




}
