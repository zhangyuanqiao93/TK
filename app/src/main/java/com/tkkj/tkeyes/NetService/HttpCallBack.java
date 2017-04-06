package com.tkkj.tkeyes.NetService;

import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TKKJ on 2017/4/6.
 */

public abstract  class HttpCallBack<T> extends BaseJsonHttpResponseHandler<Void> {

    private static final String  TAG = "HttpCallBack";
    @Override
    public abstract void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Void response);

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Void errorResponse) {
        Log.i(TAG, "onFailure");
        Log.i("onFailure", throwable.getMessage());
    }

    @Override
    public void onUserException(Throwable error) {
        super.onUserException(error);
    }

    @Override
    protected Void parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
        Log.i("rawJsonData", rawJsonData);
        Log.i("isFailure", isFailure + "");

        //T类型
//        T t = new Gson().fromJson(rawJsonData, getSuperclassTypeParameter(getClass()));
//        return t;
        return null;
    }

    /**
     * 返回gson类型
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
