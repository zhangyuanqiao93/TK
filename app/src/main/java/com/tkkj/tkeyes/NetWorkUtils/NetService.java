package com.tkkj.tkeyes.NetWorkUtils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tkkj.tkeyes.base.BasicApplication;

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
    public static final String BASE_URL = "http://192.168.1.123:8080/tkkj/";



    public static final String LOGIN = BASE_URL + "login?";//登录

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
        Log.e("tag", "数据:" + params.toString());
        client.post(getContext(), LOGIN, params, responseHandler);
    }

}
