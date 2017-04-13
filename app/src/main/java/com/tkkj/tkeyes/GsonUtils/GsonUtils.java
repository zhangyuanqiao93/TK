package com.tkkj.tkeyes.GsonUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by TKKJ on 2017/4/13.
 */


/**
 * 封装的GSON解析工具类，提供泛型参数
 * */
public class GsonUtils {
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(JSONObject jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(String.valueOf(jsonData), type);
        return result;
    }
}
