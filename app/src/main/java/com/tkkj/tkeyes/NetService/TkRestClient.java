package com.tkkj.tkeyes.NetService;

import com.loopj.android.http.*;
/**
 * Created by TKKJ on 2017/4/6.
 */


/**
 * 向后台发送数据请求，数据处理
 * */
public class TkRestClient  {
    private static final String BASE_URL = "https://api.twitter.com/1/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String url;
    private static RequestParams params;
    private static AsyncHttpResponseHandler responseHandler;


    /**
     * get 带参数请求
     * */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * post 带参数请求
     * */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        TkRestClient.url = url;
        TkRestClient.params = params;
        TkRestClient.responseHandler = responseHandler;
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    /**
     * particular request.用户名密码认证
     * */
    private static void setParticularRequest(){
        client.setBasicAuth("username","password/token");
//        client.get("https://example.com");
    }
}
