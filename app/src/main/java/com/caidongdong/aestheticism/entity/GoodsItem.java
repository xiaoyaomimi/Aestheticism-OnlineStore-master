package com.caidongdong.aestheticism.entity;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/9 14:13
 * 邮箱：mircaidong@163.com
 */
public class GoodsItem {
    private String imgUrl;
    private String name;
    private Integer level;
    private Integer monthSale;
    private String describe;
    private Integer deliveryType;
    private String averageTime;

    public GoodsItem(String imgUrl, String name, Integer level, Integer monthSale, String describe, Integer deliveryType, String averageTime) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.level = level;
        this.monthSale = monthSale;
        this.describe = describe;
        this.deliveryType = deliveryType;
        this.averageTime = averageTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMonthSales() {
        return monthSale;
    }

    public void setMonthSales(Integer monthSale) {
        this.monthSale = monthSale;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }
}
