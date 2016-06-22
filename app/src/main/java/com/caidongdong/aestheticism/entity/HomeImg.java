package com.caidongdong.aestheticism.entity;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/9 10:58
 * 邮箱：mircaidong@163.com
 */
public class HomeImg {
    private String path;
    private String vcode;
    private long time;

    public HomeImg(String path, String vcode, long time) {
        this.path = path;
        this.vcode = vcode;
        this.time = time;
    }

    public String getUrl() {
        return path;
    }

    public void setUrl(String path) {
        this.path = path;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
