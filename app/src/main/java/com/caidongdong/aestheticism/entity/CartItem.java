package com.caidongdong.aestheticism.entity;

/**
 * Aestheticism
 * Created by caidong on 16-2-29.
 * email:mircaidong@163.com
 */
public class CartItem {
    private String itemId;
    private String imgStr;
    private String itemName;
    private String oldPrice;
    private String sellPrice;
    private String num;
    private int status;

    public CartItem(String itemId, String imgStr, String itemName, String oldPrice, String sellPrice, String num,int status) {
        this.itemId = itemId;
        this.imgStr = imgStr;
        this.itemName = itemName;
        this.oldPrice = oldPrice;
        this.sellPrice = sellPrice;
        this.num = num;
        this.status = status;
    }

    public CartItem() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
