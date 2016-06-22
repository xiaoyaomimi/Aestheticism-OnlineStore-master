package com.caidongdong.aestheticism.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.caidongdong.aestheticism.dao.DaoMaster;

/**
 * AestheticismApplication
 * Created by caidong on 15-12-11.
 * email:mircaidong@163.com
 */
public class OpenHelper extends DaoMaster.OpenHelper {

    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                //创建新表，注意createTable()是静态方法
                // SchoolDao.createTable(db, true);
                // 加入新字段
                // db.execSQL("ALTER TABLE 'moments' ADD 'audio_path' TEXT;");

                // TODO
                break;
            default:
                break;
        }
    }
}
