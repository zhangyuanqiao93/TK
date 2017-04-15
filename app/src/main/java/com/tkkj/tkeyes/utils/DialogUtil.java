package com.tkkj.tkeyes.utils;

/**
 * Created by TKKJ on 2017/3/25.
 */

import android.content.Context;
import android.graphics.Color;
import android.media.MediaCodec;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogUtil {
    SweetAlertDialog dialog = null;

    /**
     * 单例对象实例
     */
    private static class DialogUtilHolder {
        static final DialogUtil INSTANCE = new DialogUtil();
    }

    public static DialogUtil getInstance() {

        return DialogUtilHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private DialogUtil() {
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
    private Object readResolve() {
        return getInstance();
    }

    // 居中显示toast提示框
    public void toast(Context ctx, String strContent) {
        Toast toast = Toast.makeText(ctx, strContent, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    // 等待框
    public boolean waitDialog(Context ctx, String title) {
        dialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        dialog.setCustomImage(R.mipmap.loading);
        if (title.isEmpty() || title == null) {
            dialog.setTitleText("加载中");
        }else{
            dialog.setTitleText(title);
        }
        dialog.setCancelable(true);
//        dialog.hide();
        dialog.show();
        return true;
    }


    public void hideWait() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void message(Context context, String title, String content) {
        new SweetAlertDialog(context)
                .setTitleText(title)
                .setContentText(content)
                .show();
    }

    /**
     * 确定对话框
     */
    public void warning(Context ctx, String strTitle, String strContent,
                        final OnDialogUtilListener callback) {
        if (TextUtils.isEmpty(strTitle)) {
            strTitle = "提示";
        }
        new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(strTitle)
                .setContentText(strContent)
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true)
                .setCancelClickListener(
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog dialog) {
                                callback.onCancel();
                                dialog.dismiss();// 隐藏弹出框
                            }
                        })
                .setConfirmClickListener(
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog dialog) {
                                callback.onConfirm();
                                dialog.dismiss();
                            }
                        }).
                show();

    }

    public void warnDialog(Context ctx, String strTitle, String strContent, String cancel, String confirm,
                           final OnDialogUtilListener callback) {
        if (TextUtils.isEmpty(strTitle)) {
            strTitle = "提示";
        }
        new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(strTitle)
                .setContentText(strContent)
                .setCancelText(cancel)
                .setConfirmText(confirm)
                .showCancelButton(true)
                .setCancelClickListener(
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog dialog) {
                                callback.onCancel();
                                dialog.dismiss();// 隐藏弹出框
                            }
                        })
                .setConfirmClickListener(
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog dialog) {
                                callback.onConfirm();
                                dialog.dismiss();
                            }
                        }).
                show();

    }

  /**
     * 同时显示标题、内容、图片
     */
    public void alert(Context ctx, String strTitle, String strContent, int icon) {
        if (TextUtils.isEmpty(strTitle)) {
            strTitle = "提示";
        }
        new SweetAlertDialog(ctx, icon).setTitleText(strTitle)
                .setContentText(strContent).show();
    }

    /**
     * 错误提示框
     */

    public void errmessage(Context context, String title, String content) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .show();
    }

    /**
     * 同时显示标题和内容
     */
    public void alert(Context ctx, String strTitle, String strContent) {
        if (TextUtils.isEmpty(strTitle)) {
            strTitle = "提示";
        }
        new SweetAlertDialog(ctx).setTitleText(strTitle)
                .setContentText(strContent).show();
    }

    public void success(Context ctx, String strTitle, String strContent) {
        new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(strTitle)
                .setContentText(strContent)
                .show();
    }

    /**
     * 判断电话号码是否符合格式.
     *
     * 手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     *
     * @param phone the input text
     * @return true, if is phone
     */
    public static boolean isPhone(String phone) {
        String str = phone;
       if ( str.length()==11) {
           Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\\\d{8}$");
           Matcher m = p.matcher(phone);
           return m.matches();
       }
        Log.e("tag", "isPhone: " + false);
        return false;
    }
}