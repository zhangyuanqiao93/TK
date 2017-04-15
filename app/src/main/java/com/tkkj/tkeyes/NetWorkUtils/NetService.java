package com.tkkj.tkeyes.NetWorkUtils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tkkj.tkeyes.base.BasicApplication;
import com.tkkj.tkeyes.utils.DataEncryptUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TKKJ on 2017/4/8.
 */

public class NetService {

    private static AsyncHttpClient client = new AsyncHttpClient();


    static {
        //设置网络超时时间
        client.setTimeout(3000);
    }

    //http://192.168.1.101:8080/tkkj/
    //http://tongkangkeji.com.cn/tkkj/
    public static final String BASE_URL = "http://192.168.1.101:8080/tkkj/";



    public static final String LOGIN = BASE_URL + "login?";//登录
    public static final String REGISTER_WAY = BASE_URL + "register?";//注册
    public static final String PERSONAL_INFO = BASE_URL + "get_userinfo?";//个人信息
    public static final String UPDATE_PERSONINFO = BASE_URL + "update_userinfo";//提交个人信息
    public static final String UPDATE_PASSWORD = BASE_URL + "passwd";//修改密码

    public static Context getContext() {
        return BasicApplication.getContext();
    }

    public static void getHttpData(String Url, JsonHttpResponseHandler responseHandler) {
        client.get(getContext(), Url, responseHandler);
    }

    public static void cacelRequest(final Context context) {
        client.cancelRequests(context, true);
    }


    /**
     * 检查手机号是否已注册
     *
     * @param phone
     * @param responseHandler
     */
    public static void registerCheck(String phone, JsonHttpResponseHandler responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("key", DataEncryptUtil.keyEncryp(phone));
        RequestParams params = new RequestParams(map);
        client.post(getContext(), REGISTER_WAY, params, responseHandler);
    }

    /**
     * 上传个人信息
     *
     * @param key
     * @param value
     * @param responseHandler
     */
    public static void upPersonInfo(String key, String value, JsonHttpResponseHandler responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DataEncryptUtil.getUserId());
        map.put("type", key);
        map.put("value", value);
        map.put("key", DataEncryptUtil.keyEncryp(key + "|" + DataEncryptUtil.getUserId() + "|" + value));
        RequestParams params = new RequestParams(map);
        Log.e("tag", params.toString());
        client.post(getContext(), UPDATE_PERSONINFO, params, responseHandler);
    }




    /**
     * 通过用户名登录
     *
     * @param name
     * @param pass
     * @param responseHandler
     */
    public static void login(String name, String pass, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("name_login", name);
        params.put("password_login", pass);
        Log.d("tag", "数据:" + params.toString());
        client.post(getContext(), LOGIN, params, responseHandler);
    }

    /**
     * @author TKKJ
     * @function 用户注册
     * @Time 2017/04/14
     *
     * @param name 用户名
     * @param password 密码
     * @param confirmPassword 确认密码
     * @param age 年龄
     * @param degree 近视度数
     * @param astigmatism 散光度数
     * @param accessID 准入ID
     * @param phone 手机号
//     * @param verification  手机验证码 ,暂时不做验证码功能         */

    public static void register(String name, String password, String confirmPassword, String age, String degree,
                                String astigmatism, String accessID, String phone, JsonHttpResponseHandler responseHandler ){
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("password",password);
        params.put("confirmPassword",confirmPassword);
        params.put("age",age);
        params.put("degree",degree);
        params.put("astigmatism",astigmatism);
        params.put("accessID",accessID);
        params.put("phone",phone);
        client.post(getContext(), REGISTER_WAY, params, responseHandler);
    }

}
