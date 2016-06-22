package com.caidongdong.aestheticism.net.okhttp.callback;

import android.support.annotation.Nullable;

import com.caidongdong.aestheticism.net.okhttp.L;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 抽象的回调接口，用户下载文件的进度监听
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.caidongdong.aestheticism.net.okhttp.callback.AbsCallback.java
 * @author: caidongdong
 * @date: 2016-04-08 16:12
 */
public abstract class AbsCallback<T> {
    /** 请求网络开始前，UI线程 */
    public void onBefore(OkHttpRequest request) {
    }

    /** 请求网络结束后，UI线程 */
    public void onAfter(@Nullable T t, Call call, Response response, @Nullable Exception e) {
    }

    /**
     * Post执行上传过程中的进度回调，get请求不回调，UI线程
     *
     * @param currentSize  当前上传的字节数
     * @param totalSize    总共需要上传的字节数
     * @param progress     当前上传的进度
     * @param networkSpeed 当前上传的速度   字节/秒
     */
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }

    /**
     * 执行下载过程中的进度回调，UI线程
     *
     * @param currentSize  当前下载的字节数
     * @param totalSize    总共需要下载的字节数
     * @param progress     当前下载的进度
     * @param networkSpeed 当前下载的速度   字节/秒
     */
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }

    /** 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作 */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    /** 对返回数据进行操作的回调， UI线程 */
    public abstract void onResponse(T t);

    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
        if (e != null) e.printStackTrace();
        else if (response != null) L.e("服务器内部错误，或者找不到页面等");
    }

    public static final AbsCallback CALLBACK_DEFAULT = new AbsCallback() {

        @Override
        public Response parseNetworkResponse(Response response) throws Exception {
            return response;
        }

        @Override
        public void onResponse(Object response) {
        }
    };

}
