package com.tkkj.tkeyes.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.tkkj.tkeyes.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by TKKJ on 2017/3/24.
 */

public class URLDemo extends AppCompatActivity {

    Bitmap bitmap ;

    ImageView imgShow ;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
// TODO Auto-generated method stub
            if (msg. what ==0x125) {

// 显示从网上下载的图片
                imgShow .setImageBitmap( bitmap );
            }
        }

    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super .onCreate(savedInstanceState);

        setContentView(R.layout.activity_main );

        imgShow =(ImageView)findViewById(R.id.imgShow );

// 创建并启动一个新线程用于从网络上下载图片

        new Thread(){

            @Override

            public void run() {

// TODO Auto-generated method stub

                try {

// 创建一个url 对象

                    URL url= new URL( "http://avatar.csdn.net/1/1/E/1_fengyuzhengfan.jpg" );

// 打开URL 对应的资源输入流

                    InputStream is= url.openStream();

// 把InputStream 转化成ByteArrayOutputStream

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte [] buffer = new byte [1024];

                    int len;

                    while ((len = is.read(buffer)) > -1 ) {

                        baos.write(buffer, 0, len);

                    }

                    baos.flush();

                    is.close(); // 关闭输入流

// 将ByteArrayOutputStream 转化成InputStream

                    is = new ByteArrayInputStream(baos.toByteArray());

// 将InputStream 解析成Bitmap

                    bitmap = BitmapFactory. decodeStream (is);

// 通知UI 线程显示图片

                    handler .sendEmptyMessage(0x125);

// 再次将ByteArrayOutputStream 转化成InputStream

                    is= new ByteArrayInputStream(baos.toByteArray());

                    baos.close();

// 打开手机文件对应的输出流

                    OutputStream os=openFileOutput( "dw.jpg" , MODE_PRIVATE );

                    byte []buff= new byte [1024];

                    int count=0;

// 将URL 对应的资源下载到本地

                    while ((count=is.read(buff))>0) {

                        os.write(buff, 0, count);

                    }

                    os.flush();

// 关闭输入输出流

                    is.close();

                    os.close();


                } catch (Exception e) {

// TODO Auto-generated catch block

                    e.printStackTrace();

                }

            }

        }.start();

    }

}