package com.example.macalyou.mobilesafe.welcompage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macalyou.mobilesafe.R;

/**
 * Created by macalyou on 2015/12/16.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private View view;
    private ImageView iv_icon;
    private TextView tv_name;

    int[] imageId = { R.mipmap.safe,
            R.mipmap.callmsgsafe, R.mipmap.app, R.mipmap.trojan, R.mipmap.sysoptimize,R.mipmap.taskmanager,
            R.mipmap.netmanager,R.mipmap.atools,R.mipmap.settings };
    String[] names = { "手机防盗",   "通讯卫士","软件管家","手机杀毒","缓存清理","进程管理",
            "流量统计", "高级工具", "设置中心" };

    public HomeAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("test", String.valueOf(position));
        view = View.inflate(context, R.layout.item_home, null);
        iv_icon = (ImageView)view.findViewById(R.id.iv_icon);
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        iv_icon.setImageResource(imageId[position]);
        tv_name.setText(names[position]);
        return view;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
