package com.tkkj.tkeyes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.tkkj.tkeyes.base.SynchronousGet;
import com.tkkj.tkeyes.base.URLDemo;

import java.util.Date;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "Login Activity" ;
    private EditText text1;
    private EditText text2;
    private CheckBox box1;
    private Button login_btn;
    private Button regidter_btn;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    SynchronousGet synchronousGet;
    URLDemo urlDemo;
    Button db_Test;
    Button bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setTitle("用户登录");
        initview();
    }

    private void initview() {
        // TODO Auto-generated method stub

        text1 = (EditText) findViewById(R.id.username_edit);
        text2 = (EditText) findViewById(R.id.password_edit);
        box1 = (CheckBox) findViewById(R.id.remenber_button);
        login_btn = (Button) findViewById(R.id.signin_button);
        regidter_btn = (Button) findViewById(R.id.register);
        db_Test = (Button) findViewById(R.id.db_Test);
        bluetooth = (Button) findViewById(R.id.bluetooth);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        box1.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        regidter_btn.setOnClickListener(this);
        db_Test.setOnClickListener(this);
        bluetooth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                register();
                break;
            case R.id.remenber_button:
                rememberPassWord();
                break;
            case R.id.signin_button:
                startActivity(new Intent(this,MainActivity.class));
               /* initData();
                    //		获取用户名名和获取密码
                String account = text1.getText().toString().trim();
                String password = text2.getText().toString().trim();
                NetService.loginByPhoneOkhttp(this, account, password, new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //这是返回来的结果result
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        Toast.makeText(LoginActivity.this,"发送成功！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        //这是网络请求失败或者异常的结果;无返回结果可以
                        Toast.makeText(LoginActivity.this,"登录失败，重新登录！",Toast.LENGTH_SHORT).show();
                    }
                });*/
                break;
            case R.id.db_Test:
//                Toast.makeText(LoginActivity.this,"进入SQLite测试Activity",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,DBActivity.class));
                break;
            case R.id.bluetooth:
              /*  setTitle("Bluetooth测试");
                Toast.makeText(LoginActivity.this,"Bluetooth测试Activity",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,BluetoothGattActivity.class));*/
            default:
                break;
        }
    }
/**
 * Author:　ZYQ
 * Function：登录初始化数据
 * Data：2017/03/20　
 * */
    //登录
    private void initData() {
        //获取用户名名和获取密码
        String account = text1.getText().toString();
        String password = text2.getText().toString();
        if (account.equals("")) {
            text1.setError("用户名不能为空");
            text1.requestFocus();//用户名为空,焦点直接回来用户名
            return ;
        }
        if (password.equals("")) {
            text2.setError("密码不能为空");
            text2.requestFocus();//用户名为空焦点直接回来用户名
            return;
        }
        Toast.makeText(LoginActivity.this, "用户名: "+ account+"\n"+"密码： "+password, Toast.LENGTH_SHORT).show();
    }

    private void showDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // 根据用户名称密码查询
    private String query(String username, String password) {
        // 查询参数
        String queryString = "username=" + username + "&password=" + password;
//        synchronousGet
        // url
//        String url = HttpUtil.BASE_URL+"servlet/LoginServlet?"+queryString;
        // 查询返回结果
//        return HttpUtil.queryStringForPost(url);
        return null;
    }

    private void rememberPassWord() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = (CheckBox) findViewById(R.id.remenber_button);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            text1.setText(account);
            text2.setText(password);
            rememberPass.setChecked(true);
            if (account.equals("TK") && password.equals("123456")) {
                editor = pref.edit();
                if (rememberPass.isChecked()) {
//				检查复选框是否被选中
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.commit();
            }
            Toast.makeText(LoginActivity.this, "记住密码" + text1 + text2, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "rememberPassWord: " );
        }
    }

    //    注册逻辑
    private void register() {
        Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register_intent);
//        startActivityForResult(register_intent,1);
    }


    //    与服务器连接通道

    public static void getHttpData(String Url, JsonHttpResponseHandler responseHandler) {
        Date curDate = new Date(System.currentTimeMillis());
//        client.get(getContext(), Url, responseHandler);
        Date endDate = new Date(System.currentTimeMillis());
        long diff = endDate.getTime() - curDate.getTime();
        Log.e("tag", "网络查询数据耗时：" + diff);
    }
//
//    public static void cacelRequest(final Context context) {
//        client.cancelRequests(context,true);
//    }

    OkHttpClient okHttpClient = new OkHttpClient();



//OkHttpClient对象
    RequestBody requestBody = new FormEncodingBuilder().add("", "").build();
    Request request = new Request.Builder().url("http://192.168.1.101").get().build();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
//    注意其中括号中的url就是你请求数据的url

//    最后OkHttpClient对象去构造得到一个Call对象，
// 去异步请求（enqueue），okHttpClient.newCall(request).enqueue(new Callback() {});
//    okHttpClient.newCall(request).enqueue(new MenuBuilder.Callback() {

}
