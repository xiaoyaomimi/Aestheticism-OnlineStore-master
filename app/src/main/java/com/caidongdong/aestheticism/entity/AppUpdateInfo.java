package com.caidongdong.aestheticism.entity;

/**
 * 应用更新bean
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.caidongdong.aestheticism.entity.AppUpdateInfo.java
 * @author: caidongdong
 * @date: 2016-05-26 18:19
 */
public class AppUpdateInfo {
    private String versionCode;
    private String versionName;
    private String appFileName;
    private String appDownloadUrl;
    private String fileMD5;
    private int statusCode;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppFileName() {
        return appFileName;
    }

    public void setAppFileName(String appFileName) {
        this.appFileName = appFileName;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
