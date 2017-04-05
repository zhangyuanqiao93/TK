package com.tkkj.tkeyes.DataBase;

/**
 * Created by TKKJ on 2017/3/30.
 */


import org.json.JSONObject;

/**
 * 缓存管理
 * */
public class CacheManager {
    private static final String TAG = CacheManager.class.getSimpleName();
    private static final String APP_CACHE = "cache_manager_file";
    private static final String APP_CACHE_TIME = "cache_manager_file_time";
    private static final long maxTime = 60 * 60 * 24 * 1000;


    //数据保存
    public static void setData(String url, JSONObject response){
        if (hasCache(url)) {
            updateData(url, response);
        } else {
            insert(url, response);
        }
    }

    /**
     * 插入数据
     *
     * @param url
     * @param response
     */
    public static void insert(final String url, final JSONObject response) {
//        CacheModelDao modelDao = GreenDaoManager.getInstance().getSession().getCacheModelDao();
//        CacheModel model = new CacheModel();
//        model.setCode(DataEncryptUtil.getMd5(url));
//        model.setDate(System.currentTimeMillis());
//        model.setUserId(DataEncryptUtil.getUserId());
//        model.setJsonData(String.valueOf(response));
//        modelDao.insert(model);

//        UserDao userDao = DaoManager.getInstance();
    }

    private static void updateData(String url, JSONObject response) {
    }

    public static boolean hasCache(String url) {

//        CacheModel model = (CacheModel) getQuery(url).unique();
//        if (model != null) {
//            return true;
//        } else {
            return false;
//        }
    }
}
