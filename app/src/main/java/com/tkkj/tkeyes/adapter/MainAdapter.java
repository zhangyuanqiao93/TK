package com.tkkj.tkeyes.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.suke.widget.SwitchButton;
import com.tkkj.tkeyes.R;
import com.tkkj.tkeyes.SearchActivity;
import com.tkkj.tkeyes.model.OperationModel;
import com.tkkj.tkeyes.utils.DialogUtil;
import com.tkkj.tkeyes.utils.GlobalObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by TKKJ on 2017/3/25.
 */

public class MainAdapter extends BaseAdapter {
    private Context context;
    ArrayList<OperationModel> list;

    private static String TAG = "MainAdapter";

    private Intent freshIntent = new Intent();

    public MainAdapter(Context context, ArrayList<OperationModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        holder.tvNameItemMine.setText(list.get(position).getTitle());
        if (position == list.size() - 1) {
            holder.toggleAutoPlay.setVisibility(View.GONE);
        }
//        if (!BluetoothUtil.isBluetoothSupported() && position == list.size() - 1) {
//            if (list.get(position).isCheck() == 0) {
//                holder.toggleAutoPlay.setEnabled(false);
//            } else {
//                holder.toggleAutoPlay.setEnabled(true);
//            }
//            holder.linItemMine.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DialogUtil.getInstance().errmessage(context, "错误", "当前设备不支持蓝牙");
//                }
//            });

//        }
        holder.toggleAutoPlay.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                switch (position) {
                    case 0:
                        Log.e(TAG, "onCheckedChanged: 启动设备");
                        startOrEnd();
                        break;
                    case 1:
                        Log.e(TAG, "onCheckedChanged: 干预设备");
                        break;
                    case 2:
                        Log.e(TAG, "onCheckedChanged: 测试设备");
                        if (isChecked) {
                            sendBrocast();
                        }
                        break;
                    case 3:
                        Log.e(TAG, "onCheckedChanged: 连接设备");
                        context.startActivity(new Intent(context, SearchActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
        holder.linItemMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == list.size() - 1) {
                    callMe("10086");//联系我们，Pad不支持，会报错。
                }
            }
        });
        return convertView;

    }

    public void sendBrocast() {

        freshIntent.setAction(GlobalObject.GLOBAL_BROADCAST);
        freshIntent.putExtra("msg", GlobalObject.TEST_DEVICE);
        freshIntent.putExtra("data", "测试");
        context.sendBroadcast(freshIntent);
    }

    private void diveceTest() {
        return;
    }

    public void callMe(String phone) {
        /*Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:"+ phone);
        intent.setData(data);
        context.startActivity(intent);*/
    }

    private void startOrEnd() {
        RequestParams params = new RequestParams();//这里装参数

        return;
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

