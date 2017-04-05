package com.tkkj.tkeyes;


/**
 * Created by TTKJ on 2017/03/24
 * Module： Register
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, password, confirm_password,
            age, degree, astigmatism, phone, check_ID, ID;
    private Button check, confirm, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_register);
        setTitle("请注册个人信息");
        InitView();
    }

    /**
     * Author : ZYQ
     * 初始化组件,注册组件，findViewById();setOnClickListener();实现监听事件
     *
     */

    private void InitView() {
//        findViewById
        name = (EditText) findViewById(R.id.name_edit);
        password = (EditText) findViewById(R.id.password_edit);
        confirm_password = (EditText) findViewById(R.id.confirm_edit);
        age = (EditText) findViewById(R.id.age_edit);
        degree = (EditText) findViewById(R.id.degree_edit);
        astigmatism = (EditText) findViewById(R.id.astigmatism_edit);
        phone = (EditText) findViewById(R.id.phone_edit);
        check_ID = (EditText) findViewById(R.id.checkID_edit);
        ID = (EditText) findViewById(R.id.ID_edit);
        check = (Button) findViewById(R.id.check_btn);
        confirm = (Button) findViewById(R.id.register_btn);
        cancel = (Button) findViewById(R.id.cancel_btn);

//      setOnClickListener
        name.setOnClickListener(this);
        password.setOnClickListener(this);
        confirm_password.setOnClickListener(this);
        age.setOnClickListener(this);
        degree.setOnClickListener(this);
        astigmatism.setOnClickListener(this);
        phone.setOnClickListener(this);
        check_ID.setOnClickListener(this);
        ID.setOnClickListener(this);
        check.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    /**
     * Created by TKKJ on 2017/03/25
     * 退出系统提示
    */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
////            创建对话框
//          /*  AlertDialog isExit = new AlertDialog.Builder(this).create();
////            设置标题
//            isExit.setTitle("系统提示");
////             设置对话框消息
//            isExit.setMessage("确定要退出吗");
////           添加选择按钮并注册监听
////Button does not exist
//            isExit.setButton(R.id.cancel, "确定", listener);
//            isExit.setButton(R.id.confirm_button,"取消",listener);
////          显示对话框
//            isExit.show();*/
//        }
//        return false;
//    }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
//                点击注册的逻辑
                register_Confirm();
                break;
            case R.id.cancel_btn:
                cancel_register();
                break;
            case R.id.check_btn:
                //            手机验证码
                checkMobile();
                break;
            default:
                break;
        }
    }


    private void checkMobile() {
        String flag = "1234";
        String checkNum = check_ID.getText().toString().trim();
        if (checkNum!= flag){
            check_ID.setError("验证码不正确，请重新输入");
            check_ID.requestFocus();
            return;
        }
    }

    private void cancel_register() {
        Toast.makeText(RegisterActivity.this,"未保存注册信息，确定退出？",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Created by TKKJ on 2017/03/24
     * 注册信息检验
     * */
    private void register_Confirm() {
        String name_edit = name.getText().toString().trim();
        String password_edit = password.getText().toString().trim();
        String confirm_password_edit = confirm_password.getText().toString().trim();
        String age_edit = age.getText().toString().trim();
        String degree_edit = degree.getText().toString().trim();
        String astigmatism_edit = astigmatism.getText().toString().trim();
        String phone_edit = phone.getText().toString().trim();
        String cheeked_edit = check_ID.getText().toString().trim();
        String ID_edit = ID.getText().toString().trim();


        /**
         * 注册判断逻辑
         * */
            if (name_edit.equals("")) {
                name.setError("用户名不能为空！");
                name.requestFocus();
                return;
            } else if (password_edit.equals("") || confirm_password_edit.equals("")) {
                password.setError("密码不能为空!");
                password.requestFocus();
                if (!password_edit.equals("") && confirm_password_edit.equals("")) {
                    confirm_password.setError("确认密码不能为空!");
                    confirm_password.requestFocus();
                    return;
                } else if (password_edit.equals("") || !confirm_password_edit.equals("")) {
                    password.setError("密码不能为空!");
                    password.requestFocus();
                    return;
                } else if (!password.equals(confirm_password)) {
//                  密码校验成功与否
                    Toast.makeText(RegisterActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                return;
            } else if (age_edit.equals("")) {
                age.setError("请填写真实年龄！");
                age.requestFocus();
                return;
            } else if (degree_edit.equals("")) {
                degree.setError("请填写近视度数！");
                degree.requestFocus();
                return;
            } else if (astigmatism_edit.equals("")) {
                astigmatism.setError("请填写散光度数！");
                astigmatism.requestFocus();
                return;
            } else if (ID_edit.equals("")) {
                ID.setError("请填写ID！");
                ID.requestFocus();
                return;
            } else if (phone_edit.equals("")) {
                phone.setError("请填写手机号！");
                phone.requestFocus();
                return;
            } else if (check_ID.equals("")) {
                check_ID.setError("请填写验证码！");
                check_ID.requestFocus();
                return;
            } else if (!check_ID.equals("")) {
                return;
            }
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
