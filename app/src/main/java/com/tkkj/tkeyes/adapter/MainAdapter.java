package com.tkkj.tkeyes.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.RequestParams;
import com.suke.widget.SwitchButton;
import com.tkkj.tkeyes.FacilityActivity;
import com.tkkj.tkeyes.MainActivity;
import com.tkkj.tkeyes.NetService.HttpCallBack;
import com.tkkj.tkeyes.NetService.NetDao;
import com.tkkj.tkeyes.R;
import com.tkkj.tkeyes.bluetoothutil.BluetoothUtil;
import com.tkkj.tkeyes.utils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import okhttp3.Request;


/**
 * Created by TKKJ on 2017/3/25.
 */

public class MainAdapter extends BaseAdapter implements View.OnTouchListener{
    private Context context;
    private String[] list;

    private static String TAG = "MainAdapter";

    private Intent freshIntent = new Intent();

    public MainAdapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvNameItemMine.setText(list[position]);
        if (position==list.length-1){
            holder.toggleAutoPlay.setVisibility(View.GONE);
        }
        if (!BluetoothUtil.isBluetoothSupported()&&position==list.length-1){
            holder.toggleAutoPlay.setEnabled(false);
            holder.linItemMine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.getInstance().errmessage(context,"错误","当前设备不支持蓝牙");
                }
            });

        }
        holder.toggleAutoPlay.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                switch (position){
                    case 0:
                        Log.d(TAG, "onCheckedChanged: 启动设备");
                        startOrEnd();//这里实现网络请求？Y
                        break;
                    case 1:
                        Log.d(TAG, "onCheckedChanged: 干预设备");
                        break;
                    case 2:
                        Log.d(TAG, "onCheckedChanged: 测试设备");
                        break;
                    case 3:
                        Log.d(TAG, "onCheckedChanged: 连接设备");
                        context.startActivity(new Intent(context,FacilityActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
       holder.linItemMine.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (position==list.length-1){
                   callMe("10086");
               }
           }
       });
        return convertView;

    }
    public void callMe(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:"+ phone);
        intent.setData(data);
        context.startActivity(intent);
    }
    private void startOrEnd() {
        RequestParams params = new RequestParams();//这里装参数
        NetDao.dataLoad(params, new HttpCallBack() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Void response) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }
        });
        return;
    }

    private boolean BluetoothIsBoot() {
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }


    class ViewHolder {
        @BindView(R.id.img_left_item_mine)
        ImageView imgLeftItemMine;
        @BindView(R.id.tv_name_item_mine)
        TextView tvNameItemMine;
        @BindView(R.id.toggle_AutoPlay)
        SwitchButton toggleAutoPlay;
        @BindView(R.id.layout_toggle)
        RelativeLayout layoutToggle;
        @BindView(R.id.lin_item_mine)
        LinearLayout linItemMine;
        @BindView(R.id.divider_line_mine)
        View dividerLineMine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

