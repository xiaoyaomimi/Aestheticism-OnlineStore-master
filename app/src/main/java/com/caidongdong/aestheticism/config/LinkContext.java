package com.caidongdong.aestheticism.config;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/9 10:44
 * 邮箱：mircaidong@163.com
 */
public class LinkContext {
    private static String SERVER_ADDRESS = "http://172.16.193.74:8080/Aestheticism";
    public static String HOME_IMG_ADDRESS = SERVER_ADDRESS + "/homeImg";
    public static String HOME_HEADER_MENU = SERVER_ADDRESS + "/homeMenu";
    public static String HOME_GOODS_LIST = SERVER_ADDRESS + "/homeGoodsList";
    public static String MORE_HOME_GOODS_LIST = SERVER_ADDRESS + "/moreHomeGoodsList";
    public static String UPLOAD_HEAD_IMG = SERVER_ADDRESS + "/changeHeaderImg";
    public static String MODIFY_NICK_NAME = SERVER_ADDRESS + "/changeNickName";
    public static String MODIFY_PHONE_NUMBER = SERVER_ADDRESS + "/changePhoneNumber";
    public static String MODIFY_GENDER = SERVER_ADDRESS + "/changeGender";
    public static String CHANGE_ADDRESS = SERVER_ADDRESS + "/changeDefautAddress";
    public static String MODIFY_PASSWORD = SERVER_ADDRESS + "/changePassword";
    public static String GET_CART_LIST = SERVER_ADDRESS + "/getMyCartList";
    public static String PAY_FOR_THE_BILL = SERVER_ADDRESS + "/goToPay";


}
