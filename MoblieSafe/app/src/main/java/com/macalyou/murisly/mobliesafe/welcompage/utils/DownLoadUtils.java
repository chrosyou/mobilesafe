package com.macalyou.murisly.mobliesafe.welcompage.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by Administrator on 2015/11/28.
 */
public class DownLoadUtils {

    /**
     * 模拟的下载类
     */
    public class MyHttpHelper {
        public void download(String url, String target, final DownCallback callback) {
            int i = 0;
            while (i < 10) {
                callback.onLoading(10, 1, false);
                i++;
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            callback.onSuccess();
        }
    }
    /**
     * 下载的方法
     */
    public void downapk(String url, String targerFile, final DownCallback downCallBack) {
        /** 创建HttpUtils对象 */
        MyHttpHelper httputils = new MyHttpHelper();
        /** 调用下载方法*/
        httputils.download(url, targerFile, downCallBack);

    }

    /** 接口用于监听下载的状态 */
    interface DownCallback {
        /** 下载成功是调用 */
        void onSuccess();
        /** 下载失败是调用 */
        void onFailure(HttpException e, String arg1);
        /** 下载中调用 */
        void onLoading(long total, long current, boolean isUploading);
    }
}
