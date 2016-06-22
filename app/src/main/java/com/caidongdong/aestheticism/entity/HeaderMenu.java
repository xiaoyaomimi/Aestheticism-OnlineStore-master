package com.caidongdong.aestheticism.entity;

/**
 * Aestheticism
 * 作者：caidongdong on 2015/12/17 15:30
 * 邮箱：mircaidong@163.com
 */
public class HeaderMenu {
    private String imgUrl;      //菜单图片
    private String describe;    //菜单文字描述
    private boolean enable;     //是否可用
    private float version;      //版本号

    public HeaderMenu() {
    }

    public HeaderMenu(String imgUrl, String describe, boolean enable, float version) {
        this.imgUrl = imgUrl;
        this.describe = describe;
        this.enable = enable;
        this.version = version;
    }

    public HeaderMenu(String imgUrl, String describe, boolean enable) {
        this.imgUrl = imgUrl;
        this.describe = describe;
        this.enable = enable;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }
}
