package com.tkkj.tkeyes.NetService;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;

import javax.net.ssl.SSLException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NoHttpResponseException;

/**
 * AsyncHttpClientUtils的单例
 * @Author TKKJ
 * @Address http://blog.csdn.net/finddreams
 * @Time 2017/4/6
 * */


public class AsyncHttpClientUtils {
    public static final String TAG = AsyncHttpClientUtils.class.getSimpleName();
    public static final int SOCKET_TIMEOUT = 20 * 1000;//默认超时时间
    //    public static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;
    private static AsyncHttpClientUtils instance = new AsyncHttpClientUtils();
    // 实例化对象
    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setConnectTimeout(SOCKET_TIMEOUT);  //连接时间
        client.setResponseTimeout(SOCKET_TIMEOUT); //响应时间
        client.setTimeout(SOCKET_TIMEOUT); // 设置连接超时，如果不设置，默认为10s
    }

    /**
     * cookie
     * */
    private PersistentCookieStore cookieStore;

    private AsyncHttpClientUtils() {
    }

    public static AsyncHttpClientUtils getInstance() {
        return instance;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    /**
     * get方法带参数
     */
    public RequestHandle get(String url, RequestParams params,
                             HttpCallBack httpCallBack) {
        Log.i(TAG, client.getUrlWithQueryString(true, url, params));

        RequestHandle requestHandle = client.get(url, params, httpCallBack);
        return requestHandle;
    }

    /**
     * post请求，带参数
     *
     * HttpCallBack这是一个什么类
     */
    public RequestHandle post(String url, RequestParams params,
                              HttpCallBack httpCallBack) {
        Log.i(TAG, client.getUrlWithQueryString(true, url, params));
        RequestHandle requestHandle = client.post(url, params, httpCallBack);
        return requestHandle;
    }

    /**
     * 设置Cookie
     *
     * @param context
     */
    public void setCookie(Context context) {
        cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    /**
     * 清除Cookie
     */
    public void clearSession() {
        if (cookieStore != null) {
            cookieStore.clear();
        }
    }
    /**
     * 设置重试机制(次数和时间)
     */
    public void setRetry() {
        client.setMaxRetriesAndTimeout(2, SOCKET_TIMEOUT);
        client.allowRetryExceptionClass(SocketTimeoutException.class);
        //allowRetryExceptionClass,设置出现异常的后重试的白名单，
        // 意思是当网络异常是SocketTimeoutException则可以重试。

        client.blockRetryExceptionClass(SSLException.class);
        //则是添加重试机制的黑名单，当出现SSLException异常的时候，则网络请求不会重连。
    }

    /**
     * 取消所有请求
     *
     * @param context
     */
    public void cancelAllRequests(Context context) {
        if (client != null) {
            Log.i(TAG, "cancel");
            client.cancelRequests(context, true); //取消请求
            client.cancelAllRequests(true);
        }
    }

    /**
     * JsonHttpResponseHandler
     * */
    private JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    };

    /**
     *  请求的响应处理Handler 有很多种BinaryHttpResponseHandler，JsonHttpResponseHandler，TextHttpResponseHandler
     *  他们都是继承自AsyncHttpResponseHandler
     *  TextHttpResponseHandler直接把字节流包装成String来返回，使用起来也很方便；
     * */
    private TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {

        }
    };
    /**
     * 文件下载
     *
     * @param paramString
     * @param paramBinaryHttpResponseHandler
     */
    public void downFile(String paramString,
                         BinaryHttpResponseHandler paramBinaryHttpResponseHandler) {
        try {
            client.get(paramString, paramBinaryHttpResponseHandler);
            return;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            Log.d("TKKJ", "URL路径不正确！！");
        }
    }

    private final static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();
    private final static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();

    static {
        // Retry if the server dropped connection on us
        exceptionWhitelist.add(NoHttpResponseException.class);
        // retry-this, since it may happens as part of a Wi-Fi to 3G failover
        exceptionWhitelist.add(UnknownHostException.class);
        // retry-this, since it may happens as part of a Wi-Fi to 3G failover
        exceptionWhitelist.add(SocketException.class);

        // never retry timeouts
        exceptionBlacklist.add(InterruptedIOException.class);
        // never retry SSL handshake failures
        exceptionBlacklist.add(SSLException.class);
    }
}
