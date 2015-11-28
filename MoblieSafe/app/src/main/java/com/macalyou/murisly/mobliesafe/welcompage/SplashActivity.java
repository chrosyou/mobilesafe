package com.macalyou.murisly.mobliesafe.welcompage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.macalyou.murisly.mobliesafe.R;
import com.macalyou.murisly.mobliesafe.welcompage.utils.MyUtils;
import com.macalyou.murisly.mobliesafe.welcompage.utils.VersionUpdateUtils;

/**
 * Created by Administrator on 2015/11/28.
 */
public class SplashActivity extends Activity {

    /** 使用的版本号 */
    private TextView mVersionTV;
    /** 本地的版本号 */
    private String mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置没有标题栏，在加载布局之前使用
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_slpash);

        TextView version = (TextView)findViewById(R.id.welcomepage_version);
        version.setText(MyUtils.getVersion(this));
        final VersionUpdateUtils updateutils = new VersionUpdateUtils("de", SplashActivity.this);
        new Thread() {
            public void run() {
                updateutils.getCloudVersion();
            }
        }.start();

    }
}
