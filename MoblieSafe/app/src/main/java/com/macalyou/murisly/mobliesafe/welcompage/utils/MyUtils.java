package com.macalyou.murisly.mobliesafe.welcompage.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by Administrator on 2015/11/28.
 */
public class MyUtils {
    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 用隐式意图安装apk
     * @param activity
     */
    public static void installApk(Activity activity) {
        //Intent intent = new Intent("android.intent,action.View");
        /** 添加默认分类 */
        //intent.addCategory("android.intent.category.DEFAULT");
        /** 设置数据和类型 */
        //intent.setDataAndType(Uri.fromFile(new File("")), "application/vnd.android.package-archive");
        //如果开启的activity退出时会回调当前的activity的onActivityResult
        //activity.startActivityForResult(intent, 0);
    }
}
