package com.caidongdong.aestheticism.helper;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.caidongdong.aestheticism.entity.AppUpdateInfo;
import com.caidongdong.aestheticism.enums.StatusType;
import com.caidongdong.aestheticism.manager.AppUpdateManager;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.squareup.okhttp.Request;

import java.io.File;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/4/7 16:03
 * 邮箱：mircaidong@163.com
 */
public class AppUpdateHelper {
    private Context context;
    private boolean checkStatus = true;
    final String fileDir =  Environment.getExternalStorageDirectory() + "/";
    public AppUpdateHelper(Context context) {
        this.context = context;
    }

    /**
     * 向服务器发送返回信息
     */
    public void checkAppUpdate() {
        if (!checkStatus) {
            return;
        }
        String pkName = context.getPackageName();
        String versionName = null;
        Integer versionCode = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(pkName,0).versionName;
            versionCode = context.getPackageManager().getPackageInfo(pkName,0).versionCode;
        }catch (Exception e) {
            e.printStackTrace();
        }
        checkStatus = false;
        new OkHttpRequest.Builder()
                .url("http://172.16.193.74:8080/Aestheticism/checkUpdate")
                .addParams("versionName",versionName)
                .addParams("versionCode",versionCode+"")
                .post(new ResultCallback<AppUpdateInfo>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                        checkStatus = true;
                    }

                    @Override
                    public void onResponse(AppUpdateInfo response) {
                        if (response.getStatusCode() == StatusType.YES.getValue()) {
                            AppUpdateManager manager = new AppUpdateManager(context,response);
                            manager.initOptionDialog();
                        }else {

                        }
                        checkStatus = true;
                    }
                });

    }

    public void downLoadApp(String url,String apkName) {

        new OkHttpRequest.Builder()
                .url(url)
                .addParams("downloadApk","file")
                .destFileDir(fileDir)
                .destFileName(apkName)
                .download(new ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"下载完成",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
