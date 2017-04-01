package com.tkkj.tkeyes;

import android.content.Context;

import com.tkkj.tkeyes.base.NetService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static cz.msebera.android.httpclient.HttpHeaders.TIMEOUT;

/**
 * Created by TKKJ on 2017/3/24.
 */

public class RequestManagerAll {

    OkHttpClient okHttpClient = new OkHttpClient();
    public RequestManagerAll(Context context){


        init();
        Request request = new Request.Builder().url("http:192.168.1.101").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void init() {
        NetService netService= new NetService();

        okHttpClient = new OkHttpClient();
        // 设置超时时间,设置读取超时，设置写入超时
        okHttpClient.newBuilder().connectTimeout(Long.parseLong(TIMEOUT), TimeUnit.SECONDS)
                .writeTimeout(Long.parseLong(TIMEOUT), TimeUnit.SECONDS)
                .readTimeout(Long.parseLong(TIMEOUT), TimeUnit.SECONDS).build();

    }


}
