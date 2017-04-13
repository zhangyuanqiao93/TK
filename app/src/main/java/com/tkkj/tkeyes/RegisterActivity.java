package com.tkkj.tkeyes;


/**
 * Created by TTKJ on 2017/03/24
 * Module： Register
 */

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tkkj.tkeyes.utils.DialogUtil;
import com.tkkj.tkeyes.utils.OnDialogUtilListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;

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


    private String name_edit, password_edit, confirm_password_edit,
            age_edit, degree_edit, astigmatism_edit, phone_edit, cheacked_edit, ID_edit;
    private Context  context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle("请注册个人信息");

        context= RegisterActivity.this;

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
     * 获取文本框信息
     */
    public void getEditeData() {

        name_edit = nameEdit.getText().toString().trim();
        password_edit = passwordEdit.getText().toString().trim();
         confirm_password_edit = confirmEdit.getText().toString().trim();
         age_edit = ageEdit.getText().toString().trim();
         degree_edit = degreeEdit.getText().toString().trim();
        astigmatism_edit = astigmatismEdit.getText().toString().trim();
         phone_edit = phoneEdit.getText().toString().trim();
        cheacked_edit = checkIDEdit.getText().toString().trim();
         ID_edit = IDEdit.getText().toString().trim();
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
        if (name_edit.equals("")) {
            nameEdit.setError("用户名不能为空！");
            nameEdit.requestFocus();
           flag=false;
        } else if (TextUtils.isEmpty(password_edit)) {
            passwordEdit.setError("密码不能为空!");
            passwordEdit.requestFocus();
            flag=false;
        }else if(!TextUtils.isEmpty(password_edit)&&(
                password_edit.length()<6||password_edit.length()>16
                )){
            passwordEdit.setError("密码长度应为6-16位!");
            passwordEdit.requestFocus();
            flag=false;

        }else if(TextUtils.isEmpty(confirm_password_edit)){
            confirmEdit.setError("确认密码不能为空!");
            confirmEdit.requestFocus();
            flag=false;
        } else if(!TextUtils.isEmpty(password_edit)&&!TextUtils.isEmpty(confirm_password_edit)&&!confirm_password_edit.equals(password_edit)){
                confirmEdit.setError("两次密码不一致!");
                confirmEdit.requestFocus();
                flag=false;
        } else if (age_edit.equals("")) {
            ageEdit.setError("请填写真实年龄！");
            ageEdit.requestFocus();
            flag=false;
        } else if (degree_edit.equals("")) {
            degreeEdit.setError("请填写近视度数！");
            degreeEdit.requestFocus();
        flag=false;
        } else if (astigmatism_edit.equals("")) {
            astigmatismEdit.setError("请填写散光度数！");
            astigmatismEdit.requestFocus();
        flag=false;
        } else if (ID_edit.equals("")) {
            IDEdit.setError("请填写ID！");
            IDEdit.requestFocus();
        flag=false;
        } else if (phone_edit.equals("")) {
            phoneEdit.setError("请填写手机号！");
            phoneEdit.requestFocus();
        flag=false;
        } else if (cheacked_edit.equals("")) {
            checkIDEdit.setError("请填写验证码！");
            checkIDEdit.requestFocus();
            flag = false;
        }
        return flag;
    }



    @OnClick({check_btn, R.id.cancel_btn, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case check_btn:

                getEditeData();
                if (validate()){
                   // uploadData();
                }
                break;
            case R.id.cancel_btn:
                cancel_register();
                break;
            case R.id.register_btn:
                checkMobile();
                break;


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
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
