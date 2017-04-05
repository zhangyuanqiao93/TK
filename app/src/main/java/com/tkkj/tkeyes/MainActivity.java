package com.tkkj.tkeyes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tkkj.tkeyes.adapter.MainAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{

    @BindView(R.id.list_set)
    ListView listSet;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;


    private Context context;
    private ListView listView;


    private String[] title = {"启动设备","干预设备","测试设备","连接设备","联系我们"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("功能操作");
        initData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String text = listView.getItemAtPosition();
            }
        });
    }

    public void initView(){
        //获取xml文件中listView控件
        ListView listView = (ListView) findViewById(R.id.toggle_AutoPlay);

    }
    private void initData() {
    context = MainActivity.this;
        MainAdapter adapter = new MainAdapter(MainActivity.this,title);
        listSet.setAdapter(adapter);
    }
}
