package com.tkkj.tkeyes.NetService;

import com.loopj.android.http.RequestParams;

/**
 * Created by TKKJ on 2017/4/6.
 */

public class NetDao {
    public static void dataLoad(RequestParams params,HttpCallBack callBack){
        AsyncHttpClientUtils utils = AsyncHttpClientUtils.getInstance();
        utils.post("你的网络接口URL",params,callBack);
    }
}
