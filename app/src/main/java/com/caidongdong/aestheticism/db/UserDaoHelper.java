package com.caidongdong.aestheticism.db;



import android.text.TextUtils;
import com.caidongdong.aestheticism.dao.UserDao;
import com.caidongdong.aestheticism.entity.User;
import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by caidong on 15-12-10.
 */
public class UserDaoHelper implements DaoHelperInterface{

    private static UserDaoHelper userDaoHelper;
    private UserDao userDao;

    public static UserDaoHelper getInstance() {
        if (userDaoHelper == null) {
            userDaoHelper = new UserDaoHelper();
        }
        return userDaoHelper;
    }

    @Override
    public <T> void addData(T t) {
        if (userDao != null && t != null) {
            userDao.insertOrReplace((User)t);
        }
    }

    @Override
    public void deleteData(String id) {
        if (userDao != null && !TextUtils.isEmpty(id)) {
            userDao.deleteByKey(Long.parseLong(id));
        }
    }

    @Override
    public User  getDataById(String id) {
        if (userDao != null && !TextUtils.isEmpty(id)) {
            return userDao.load(Long.parseLong(id));
        }
        return null;
    }

    @Override
    public List getAllData() {
        if (userDao != null) {
            return userDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(String id) {
        if (userDao == null || TextUtils.isEmpty(id)) {
            return false;
        }
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Id.eq(id));
        long count = queryBuilder.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userDao == null) {
            return 0;
        }
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        return queryBuilder.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userDao != null) {
            userDao.deleteAll();
        }
    }
}
