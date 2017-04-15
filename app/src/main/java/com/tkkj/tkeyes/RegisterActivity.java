package com.tkkj.tkeyes;


/**
 * Created by TTKJ on 2017/03/24
 * Module： Register
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.tkkj.tkeyes.CacheManger.CacheManager;
import com.tkkj.tkeyes.NetWorkUtils.NetService;
import com.tkkj.tkeyes.utils.DataEncryptUtil;
import com.tkkj.tkeyes.utils.DialogUtil;
import com.tkkj.tkeyes.utils.OnDialogUtilListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import cz.msebera.android.httpclient.Header;

import static com.tkkj.tkeyes.R.id.check_btn;

public class RegisterActivity extends AppCompatActivity  {

    @BindView(R.id.name_edit)
    EditText nameEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.confirm_edit)
    EditText confirmEdit;
    @BindView(R.id.age_edit)
    EditText ageEdit;
    @BindView(R.id.degree_edit)
    EditText degreeEdit;
    @BindView(R.id.astigmatism_edit)
    EditText astigmatismEdit;
    @BindView(R.id.ID_edit)
    EditText IDEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(check_btn)
    Button checkBtn;
    @BindView(R.id.checkID_edit)
    EditText checkIDEdit;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.activity_register)
    LinearLayout activityRegister;


    private String name, password, confirm_password,phone, checked, ID;
    private String age, degree, astigmatism;
    private Context  context;
    private SharedPreferences.Editor editor;
    private static  final  int  RESOLVE_SUCCESS=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setTitle("请注册个人信息");
        context= RegisterActivity.this;
        initView();
//

        //1.初始化sdk     APPKEY：是在mob.com官网上注册的appkey
        SMSSDK.initSDK(this,"1ce8907f3df30","c4b87e4ccd169f4d4b496fbabc27d464");//也是所注册的APPSECRETE
        //2.到清单文件中配置信息 （添加网络相关权限以及一个activity信息）
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册手机号
//                RegisterPage registerPage=new RegisterPage();
                //注册回调事件

            }
        });

    }

    private void initView() {
    }



    /**
     * Created by TKKJ on 2017/03/25
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };


    private void checkMobile() {
        String flag = "";
        String checkNum = checkIDEdit.getText().toString().trim();
        if (checkNum != flag) {
            checkIDEdit.setError("验证码不正确，请重新输入");
            checkIDEdit.requestFocus();
            return;
        }
    }

    private void cancel_register() {
        DialogUtil.getInstance().warning(context,"取消注册","确定取消注册吗",new OnDialogUtilListener(){
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                super.onConfirm();
                finish();
            }
        });
    }


    /**
     * Created by TKKJ on 2017/03/24
     * 注册信息检验
     */
    private boolean validate() {
        boolean flag = true;

        /**
         * 注册判断逻辑
         * */
        if (TextUtils.isEmpty(name)) {
            nameEdit.setError("用户名不能为空！");
            nameEdit.requestFocus();
           flag=false;
        } else if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("密码不能为空!");
            passwordEdit.requestFocus();
            flag=false;
        }else if(!TextUtils.isEmpty(password)&&(
                password.length()<=6||password.length()>16
                )){
            passwordEdit.setError("密码长度至少为6位!");
            passwordEdit.requestFocus();
            flag=false;

        }else if(TextUtils.isEmpty(confirm_password)){
            confirmEdit.setError("确认密码不能为空!");
            confirmEdit.requestFocus();
            flag=false;
        } else if(!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(confirm_password)&&!confirm_password.equals(password)){
            confirmEdit.setError("两次密码不一致!");
            confirmEdit.requestFocus();
            flag=false;
        } else if (TextUtils.isEmpty(age)) {
            ageEdit.setError("请填写真实年龄！");
            ageEdit.requestFocus();
            flag=false;
        } else if (TextUtils.isEmpty(degree)) {
            degreeEdit.setError("请填写近视度数！");
            degreeEdit.requestFocus();
            flag=false;
        } else if (TextUtils.isEmpty(astigmatism)) {
            astigmatismEdit.setError("请填写散光度数！");
            astigmatismEdit.requestFocus();
            flag=false;
        } else if (ID.equals("")) {
            IDEdit.setError("请填写ID！");
            IDEdit.requestFocus();
            flag=false;
        } else if (TextUtils.isEmpty(phone)) {
            phoneEdit.setError("请填写手机号！");
            phoneEdit.requestFocus();
            flag=false;

        }else if(!TextUtils.isEmpty(phone)){
            DialogUtil.isPhone(phone);
        }
        /*else if (checked.equals("")) {
            checkIDEdit.setError("请填写验证码！");
            checkIDEdit.requestFocus();
            flag = false;
        }*/
        return flag;
    }



    @OnClick({check_btn, R.id.cancel_btn, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case check_btn://手机验证码
                //checkMobile();
                break;
            case R.id.cancel_btn:
                cancel_register();
                break;
            case R.id.register_btn:
                getEditeData();
                    if (validate()){
                        uploadData();
                    }
                break;


        }
    }


    /**
     * 获取文本框信息
     */
    public void getEditeData() {

        name = nameEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();
        confirm_password = confirmEdit.getText().toString().trim();
        age = ageEdit.getText().toString().trim();
        degree = degreeEdit.getText().toString().trim();
        astigmatism = astigmatismEdit.getText().toString().trim();
        phone = phoneEdit.getText().toString().trim();
        checked = checkIDEdit.getText().toString().trim();
        ID = IDEdit.getText().toString().trim();
    }


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case RESOLVE_SUCCESS:
                    startActivity(new Intent(context,MainActivity.class));
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 注册判断是否成功
     * */
    private void uploadData() {
        NetService.register(name,password,confirm_password,age,degree,astigmatism,ID,phone,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    //deviceinfo,requeststatus.userid
//                    Log.e("tag", "Register onSuccess: "+ response.getString("deviceinfo").length());
                    switch (Integer.parseInt(response.getString("requeststatus"))){
                        case 0:
                            Log.e("tag", "onSuccess: " +"注册成功");
                            resolveData(response);
                            mHandler.sendEmptyMessage(RESOLVE_SUCCESS);
                            break;
                        case 40://注册失败0x28
                            Log.e("tag", "onSuccess: "+ "注册失败" );
                            break;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                DialogUtil.getInstance().waitDialog(context, "注册失败，请重新注册");
                //根据返回的requeststatus字段值判定注册失败的原因


                DialogUtil.getInstance().hideWait();
            }
        });
    }


    private void resolveData(JSONObject response) throws JSONException{

        String userId = response.getString("userId");
        DataEncryptUtil.saveUserId(userId);//存储后台发送过来的userId
        String registerInfo = response.getJSONArray("deviceinfo").toString();
        CacheManager.setData(userId,registerInfo);//存储用户注册完成后返回的轨迹数据
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
