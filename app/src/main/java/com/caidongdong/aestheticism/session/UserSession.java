package com.caidongdong.aestheticism.session;

/**
 * Aestheticism
 * 作者：caidongdong on 2015/12/15 15:40
 * 邮箱：mircaidong@163.com
 */
public class UserSession {
    private String userId;
    private String sessionId;
    //私有化构造方法
    private UserSession(){

    }
    private static UserSession userSession = null;
    public static UserSession getInstance() {
        if (userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
