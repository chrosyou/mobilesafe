package com.example.macalyou.mobilesafe.welcompage.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.macalyou.mobilesafe.MainActivity;
import com.example.macalyou.mobilesafe.R;
import com.example.macalyou.mobilesafe.welcompage.entity.VersionEntity;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2015/11/28.
 */
public class VersionUpdateUtils {

    private static final int MESSAGE_NET_ERROR = 101;
    private static final int MESSAGE_IO_ERROR = 102;
    private static final int MESSAGE_JSON_ERROR = 103;
    private static final int MESSAGE_SHOW_DIALOG = 104;
    private static final int MESSAGE_ENTERHOME = 105;

    private String mlocalVersion;
    private Activity mContext;
    private VersionEntity mVersionEntity;
    private ProgressDialog mProgressDialog;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MESSAGE_IO_ERROR:
                    Toast.makeText(mContext, "IO异常", Toast.LENGTH_SHORT).show();
                    //enterHome();
                    break;
                case MESSAGE_NET_ERROR:
                    Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                    //enterHome();
                    break;
                case MESSAGE_JSON_ERROR:
                    Toast.makeText(mContext, "json解析异常", Toast.LENGTH_SHORT).show();
                    //enterHome();
                    break;
                case MESSAGE_SHOW_DIALOG:
                    Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    ShowUpdateDialog(mVersionEntity);
                    break;
                case MESSAGE_ENTERHOME:
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    mContext.finish();
                    break;
            }
        }
    };

    public VersionUpdateUtils(String version, Activity activity) {
        mlocalVersion = version;
        mContext = activity;
    }

    public void getCloudVersion () {
        String urlDate="http://update.4-zip.com/Yacapi/returnExec?version=1.5.121&pid=wz&lang=zh&nation=cn&ptid=org&channel=&guid=st500dm002-1bd142_z2ajvn46xxxxz2ajvn46&lmt=0";

        try {
            URL url = new URL(urlDate);
            //打开对服务器的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //连接服务器
            conn.connect();
            /**读入服务器数据的过程**/
            //得到输入流
            InputStream is=conn.getInputStream();
            //创建包装流
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            //定义String类型用于储存单行数据
            String line=null;
            //创建StringBuffer对象用于存储所有数据
            StringBuffer sbuffer = new StringBuffer();
            while((line = br.readLine() ) != null) {
                sbuffer.append(line);
            }

            mVersionEntity = new VersionEntity();
            JSONObject jsonobject = new JSONObject(sbuffer.toString());
            mVersionEntity.versioncode = jsonobject.getString("country");
            mVersionEntity.apkurl = urlDate;
            mVersionEntity.description = "加速优化50%";
            handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
        } catch (MalformedURLException e) {
            handler.sendEmptyMessage(MESSAGE_NET_ERROR);
            e.printStackTrace();
        } catch (IOException e) {
            handler.sendEmptyMessage(MESSAGE_IO_ERROR);
            e.printStackTrace();
        } catch (JSONException e) {
            handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * 弹出提示更新窗
     * @param versionentity
     */
    public void ShowUpdateDialog (VersionEntity versionentity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本：" + versionentity.versioncode);
        builder.setMessage(versionentity.description);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initProgressDialog();
                downloadNewApk(mVersionEntity.apkurl);
            }
        });

        builder.show();

    }

    /**
     * 初始化进度条
     */
    public void initProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("准备下载...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    public void downloadNewApk(String apkurl) {
        DownLoadUtils downloadutils = new DownLoadUtils();
        downloadutils.downapk(apkurl, "", new DownLoadUtils.DownCallback() {
            @Override
            public void onSuccess() {
                mProgressDialog.dismiss();
                MyUtils.installApk(mContext);
                enterhome();
            }

            @Override
            public void onFailure(HttpException e, String arg1) {
                mProgressDialog.setMessage("下载失败");
                mProgressDialog.dismiss();
                enterhome();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                mProgressDialog.setMessage("正在下载...");
                mProgressDialog.setMax((int) total);
                mProgressDialog.setProgress((int) current);
            }
        });
    }

    private void enterhome() {
        handler.sendEmptyMessageDelayed(MESSAGE_ENTERHOME, 300);
    }
}
