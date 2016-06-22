package com.caidongdong.aestheticism.db;

import java.util.List;

/**
 * AestheticismApplication
 * Created by caidong on 15-12-11.
 * email:mircaidong@163.com
 */
public interface DaoHelperInterface {
    public <T> void addData(T t);
    public void deleteData(String id);
    public <T> T getDataById(String id);
    public List getAllData();
    public boolean hasKey(String id);
    public long getTotalCount();
    public void deleteAll();
}
