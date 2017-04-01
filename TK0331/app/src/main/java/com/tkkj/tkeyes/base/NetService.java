package com.tkkj.tkeyes.base;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TKKJ on 2017/03/26
 */
public class NetService {

    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        //设置网络超时时间
        client.setTimeout(5000);
    }

    public static final String LOGIN_URL = "http://www.baidu.com";
    public static final String REGISTER_URL = "http://www.baidu.com";

    public static Context getContext() {
        return BasicApplication.getContext();
    }

    public static void getHttpData(String Url, JsonHttpResponseHandler responseHandler) {
        Date curDate = new Date(System.currentTimeMillis());
        client.get(getContext(), Url, responseHandler);
        Date endDate = new Date(System.currentTimeMillis());
        long diff = endDate.getTime() - curDate.getTime();
        Log.e("tag", "网络查询数据耗时：" + diff);
    }

    public static void cacelRequest(final Context context) {
        client.cancelRequests(context, true);
    }



    /**
     * 通过手机号注册
     *
     * @param phone
     * @param pass
     * @param responseHandler
     */
    public static void registerByPhone(String phone, String pass, JsonHttpResponseHandler responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("password", pass);
        RequestParams params = new RequestParams(map);
        client.post(getContext(), REGISTER_URL, params, responseHandler);
    }
    public static void loginByPhone(String phone, String pass, JsonHttpResponseHandler responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("password", pass);
        RequestParams params = new RequestParams(map);
        client.post(getContext(), LOGIN_URL, params, responseHandler);
    }

    public static void loginByPhoneOkhttp(Context context,String username,String password,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.url("http://192.168.1.101:10015/")
                .addFormParam("username",username)
                .addFormParam("password",password)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

}
