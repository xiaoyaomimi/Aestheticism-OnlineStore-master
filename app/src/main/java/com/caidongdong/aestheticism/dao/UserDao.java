package com.caidongdong.aestheticism.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.caidongdong.aestheticism.entity.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table User.
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "User";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property User_Name = new Property(1, String.class, "User_Name", false, "USER__NAME");
        public final static Property PassWord = new Property(2, String.class, "PassWord", false, "PASS_WORD");
        public final static Property Gender = new Property(3, String.class, "Gender", false, "GENDER");
        public final static Property Phone_Num = new Property(4, Integer.class, "Phone_Num", false, "PHONE__NUM");
        public final static Property True_name = new Property(5, String.class, "True_name", false, "TRUE_NAME");
        public final static Property ID_Card = new Property(6, String.class, "ID_Card", false, "ID__CARD");
        public final static Property Create_Time = new Property(7, String.class, "Create_Time", false, "CREATE__TIME");
        public final static Property Update_Time = new Property(8, String.class, "Update_Time", false, "UPDATE__TIME");
        public final static Property Last_Login_Time = new Property(9, String.class, "Last_Login_Time", false, "LAST__LOGIN__TIME");
        public final static Property Enable = new Property(10, Boolean.class, "Enable", false, "ENABLE");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'User' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'USER__NAME' TEXT," + // 1: User_Name
                "'PASS_WORD' TEXT," + // 2: PassWord
                "'GENDER' TEXT," + // 3: Gender
                "'PHONE__NUM' INTEGER," + // 4: Phone_Num
                "'TRUE_NAME' TEXT," + // 5: True_name
                "'ID__CARD' TEXT," + // 6: ID_Card
                "'CREATE__TIME' TEXT," + // 7: Create_Time
                "'UPDATE__TIME' TEXT," + // 8: Update_Time
                "'LAST__LOGIN__TIME' TEXT," + // 9: Last_Login_Time
                "'ENABLE' INTEGER);"); // 10: Enable
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_User__id ON User" +
                " (_id);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'User'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String User_Name = entity.getUser_Name();
        if (User_Name != null) {
            stmt.bindString(2, User_Name);
        }
 
        String PassWord = entity.getPassWord();
        if (PassWord != null) {
            stmt.bindString(3, PassWord);
        }
 
        String Gender = entity.getGender();
        if (Gender != null) {
            stmt.bindString(4, Gender);
        }
 
        Integer Phone_Num = entity.getPhone_Num();
        if (Phone_Num != null) {
            stmt.bindLong(5, Phone_Num);
        }
 
        String True_name = entity.getTrue_name();
        if (True_name != null) {
            stmt.bindString(6, True_name);
        }
 
        String ID_Card = entity.getID_Card();
        if (ID_Card != null) {
            stmt.bindString(7, ID_Card);
        }
 
        String Create_Time = entity.getCreate_Time();
        if (Create_Time != null) {
            stmt.bindString(8, Create_Time);
        }
 
        String Update_Time = entity.getUpdate_Time();
        if (Update_Time != null) {
            stmt.bindString(9, Update_Time);
        }
 
        String Last_Login_Time = entity.getLast_Login_Time();
        if (Last_Login_Time != null) {
            stmt.bindString(10, Last_Login_Time);
        }
 
        Boolean Enable = entity.getEnable();
        if (Enable != null) {
            stmt.bindLong(11, Enable ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // User_Name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // PassWord
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Gender
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // Phone_Num
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // True_name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ID_Card
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Create_Time
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // Update_Time
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // Last_Login_Time
            cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0 // Enable
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUser_Name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassWord(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGender(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone_Num(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setTrue_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setID_Card(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCreate_Time(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUpdate_Time(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLast_Login_Time(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setEnable(cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
