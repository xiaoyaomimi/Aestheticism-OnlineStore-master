package com.caidongdong.aestheticism.enums;

/**
 * 类按钮选中状态
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.caidongdong.aestheticism.enums.SelectType.java
 * @author: caidongdong
 * @date: 2016-05-25 12:25
 */
public enum  StatusType {
    YES(1),NO(0);

    private int value;

    StatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static StatusType valueOf(int value) {
        for (StatusType statusType : values()) {
            if (statusType.getValue() == value) {
                return statusType;
            }
        }
        throw new IllegalArgumentException("not support SelectType");
    }
}
