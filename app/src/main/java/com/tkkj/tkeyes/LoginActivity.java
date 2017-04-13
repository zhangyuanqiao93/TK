package com.tkkj.tkeyes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tkkj.tkeyes.CacheManger.CacheManager;
import com.tkkj.tkeyes.Entity.UserInfoEntity;
import com.tkkj.tkeyes.GsonUtils.GsonUtils;
import com.tkkj.tkeyes.NetWorkUtils.NetService;
import com.tkkj.tkeyes.utils.DataEncryptUtil;
import com.tkkj.tkeyes.utils.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "Login Activity";
    @BindView(R.id.login_user_input)
    TextView loginUserInput;
    @BindView(R.id.username_edit)
    EditText usernameEdit;
    @BindView(R.id.login_password_input)
    TextView loginPasswordInput;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.remenber_button)
    CheckBox remenberButton;
    @BindView(R.id.signin_button)
    Button signinButton;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login_div)
    RelativeLayout loginDiv;


    private SharedPreferences.Editor editor;

    private boolean isRemember = true;//标记是否记住密码

    private String account, password;
    private Context context;

    private static  final  int  RESOLVE_SUCCESS=1;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case RESOLVE_SUCCESS:
                    startActivity(new Intent(context,MainActivity.class));
              break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle("用户登录");

        initView();
    }

    private void initView() {

        context = LoginActivity.this;
         editor=context.getSharedPreferences("login_data", MODE_PRIVATE).edit();
        remenberButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isRemember = true;
                } else {
                    isRemember = false;
                }
                /*if (!b){
                    isRemember=false;
                }*/
            }
        });

    }

    @OnClick({R.id.signin_button, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                register();
                break;
            case R.id.signin_button:
                loginEvent();
//                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    /**
     * Author:　ZYQ
     * Function：登录初始化数据
     * Data：2017/03/20
     */
    //登录
    private void loginEvent() {
        //获取用户名名和获取密码
        account = usernameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        if (validate()) {
            updateData();
        }

    }

    private void updateData() {
        DialogUtil.getInstance().waitDialog(context, "正在登录中");
        NetService.login(account, password, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                DialogUtil.getInstance().hideWait();
                try {
                    Log.e(TAG, "onSuccess: "+ response.getString("deviceinfo").length());
                    switch (Integer.parseInt(response.getString("requeststatus"))) {

                        case 0:
                            Log.e("tag", "onSuccess: " + response );
                            //保存用户登录信息
                            editor.putString("account", account);
                            editor.putString("password", password);
                            editor.commit();

                            resolveData(response);
                            mHandler.sendEmptyMessage(RESOLVE_SUCCESS);

                            break;
                        case 39:
                            Log.e("tag", "failed: " );
                            DialogUtil.getInstance().errmessage(context, "登录失败", "密码错误");
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            public  String createJsonString(String key, Object value) {
                    JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject.toString();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                DialogUtil.getInstance().hideWait();
                //根据返回的requeststatus字段值判定登录失败的原因
            }
        });
    }

    private void resolveData(JSONObject response) throws JSONException {

        String userId = response.getString("userId");

        DataEncryptUtil.saveUserId(userId);
        String info = response.getJSONArray("deviceinfo").toString();

        CacheManager.setData(userId,info);
    }


    public boolean validate() {
        boolean flag = true;
        if (account.equals("")) {
            usernameEdit.setError("用户名不能为空");
            usernameEdit.requestFocus();//用户名为空,焦点直接回来用户名
            flag = false;
        } else if (password.equals("")) {
            passwordEdit.setError("密码不能为空");
            passwordEdit.requestFocus();//用户名为空焦点直接回来用户名
            flag = false;
        }
        return flag;
    }

    //权限申请结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    //    注册逻辑
    private void register() {
        Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register_intent);
        //startActivity(new Intent(this,RegisterActivity.class));
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
//        finish();
    }

}
