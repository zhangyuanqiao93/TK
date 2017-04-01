package com.tkkj.tkeyes.base;

/**
 * Created by TKKJ on 2017/3/24.
 */

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class SynchronousGet {
    private final OkHttpClient client = new OkHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run() throws Exception {
//        请求地址：http://192.168.1.101
        Request request = new Request.Builder()
                .url("http://192.168.1.101:10015")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().string());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String... args) throws Exception {
        new SynchronousGet().run();
    }
}
