package com.caidongdong.aestheticism.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.entity.AppUpdateInfo;
import com.caidongdong.aestheticism.utils.MD5Util;
import com.caidongdong.aestheticism.view.dialog.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/4/7 16:04
 * 邮箱：mircaidong@163.com
 */
public class AppUpdateManager {
    private Context context;
    private ProgressDialog progressDialog;
    private Button cancle;
    private String localFilePath;
    private AppUpdateInfo appUpdateInfo;
    private DownloadTask downloadTask;

    public AppUpdateManager(Context context,AppUpdateInfo appUpdateInfo) {
        this.context = context;
        this.appUpdateInfo = appUpdateInfo;
    }

    public void initOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window  = alertDialog.getWindow();
        window.setContentView(R.layout.custom_alart_dialog);
        TextView textView = (TextView) window.findViewById(R.id.message_info);
        Button confirm = (Button) window.findViewById(R.id.confirm_update);
        Button cancle = (Button) window.findViewById(R.id.cancle_update);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    localFilePath = Environment.getExternalStorageDirectory() + "/" + appUpdateInfo.getAppFileName() + ".apk";
                    File apkFile = new File(localFilePath);
                    if (apkFile.exists()) {
                        try {
                            String fileMD5 = MD5Util.getFileMD5String(apkFile);
                            if (fileMD5.equals(appUpdateInfo.getFileMD5())) {
                                installFile();
                            }else {
                                downFile(appUpdateInfo.getAppDownloadUrl());
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        downFile(appUpdateInfo.getAppDownloadUrl());     //在下面的代码段
                    }
                } else {
                    Toast.makeText(context, "SD卡不可用，请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        textView.setText("发现新版本" + appUpdateInfo.getVersionCode() + "是否需要升级到该版本？");
        alertDialog.setCancelable(false);
    }

    /**
     * 安装apk文件
     *
     */
    private void installFile() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(Environment.getExternalStorageDirectory(), appUpdateInfo.getAppFileName() + ".apk");
        if (file.exists()) {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "Aestheticism-master.apk")),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
    private void downFile(String url) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("正在下载新版本，请稍候...");
        progressDialog.setProgress(0);
        progressDialog.show();

        Window window = progressDialog.getWindow();
        window.setContentView(R.layout.custom_progress_dialog);
        progressDialog.mProgress=(ProgressBar) window.findViewById(R.id.progress);
        progressDialog.mProgressNumber=(TextView) window.findViewById(R.id.progress_number);
        progressDialog.mProgressPercent=(TextView) window.findViewById(R.id.progress_percent);
        progressDialog.mProgressMessage=(TextView) window.findViewById(R.id.progress_message);
        cancle = (Button) window.findViewById(R.id.btnCancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSourceFile();
                progressDialog.dismiss();
            }
        });
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        String filePath;
        if (appUpdateInfo != null && appUpdateInfo.getAppFileName() != null && !appUpdateInfo.getAppFileName().isEmpty()){
            filePath = Environment.getExternalStorageDirectory() + "/" + appUpdateInfo.getAppFileName() + ".apk";
            File nfile = new File(filePath);
            if (nfile.exists()) {
                nfile.delete();
            }
        }else {
            filePath = Environment.getExternalStorageDirectory() + "/" + "Aestheticism-master.apk";
            File nfile = new File(filePath);
            if (nfile.exists()) {
                nfile.delete();
            }
        }
        downloadTask = new DownloadTask(context,filePath);
        downloadTask.execute(url);
    }

    /**
     * 异步下载app
     */
    private class DownloadTask extends AsyncTask<String,Integer,String> {
        private Context context;
        private String filePath;

        public DownloadTask(Context context, String filePath) {
            this.context = context;
            this.filePath = filePath;
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                int fileLength = connection.getContentLength();
                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(filePath);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    if (isCancelled()) {
                        inputStream.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) fileLength, (int) total, (int) (total * 100 / fileLength));
                    outputStream.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (outputStream != null)
                        outputStream.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ignored) {

                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(values[0]);
            progressDialog.setProgress(values[1]);
            if (100 == values[2]) {
                progressDialog.dismiss();
                downloadComplite();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "下载超时，已取消", Toast.LENGTH_LONG).show();
                deleteSourceFile();
                Log.e("Download error: ", result);
            } else {
                Toast.makeText(context, "下载完成，准备安装", Toast.LENGTH_SHORT).show();
            }
        }

    }
    /**
     * 删除未下载完成的文件
     */
    private void deleteSourceFile() {
        String filePath = Environment.getExternalStorageDirectory() + "/" + appUpdateInfo.getAppFileName() + ".apk";
        File nfile = new File(filePath);
        if (nfile.exists()) {
            nfile.delete();
        }
    }

    /**
     * 下载完成
     */
    private  void downloadComplite() {
        progressDialog.cancel();
        installFile();
    }
}
