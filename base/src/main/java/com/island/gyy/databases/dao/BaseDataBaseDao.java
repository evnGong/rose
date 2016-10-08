package com.island.gyy.databases.dao;

import android.database.sqlite.SQLiteDatabase;

import com.island.gyy.base.BaseApplication;
import com.island.gyy.base.bean.ProcessBean;
import com.island.gyy.databases.Database2BeanUtils;
import com.island.gyy.databases.DatabaseUtils;
import com.island.gyy.databases.annotation.IncreateColumn;
import com.island.gyy.interfaces.OnBackUpdateData;
import com.island.gyy.thread.ThreadHelper;
import com.island.gyy.utils.FileUtil;
import com.island.gyy.utils.NullUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/6
 * Time: 9:44
 * Describe:
 */
public class BaseDataBaseDao<T> extends BaseDao<T> {

    protected void init(final String[] str){
        try {
            DatabaseUtils.getSQLiteDatabase();
        } catch (NullPointerException e) {
            File lFile = BaseApplication.getApplication().getExternalCacheDir();
            String dataPath = lFile == null ? null : lFile.getPath();
            dataPath = dataPath == null ? BaseApplication.getApplication().getCacheDir().getPath() : dataPath;
            lFile = new File(dataPath);
            FileUtil.createDir(lFile);
            DatabaseUtils.createDatabase(dataPath, new DatabaseUtils.DatabaseInitCallback() {
                @Override
                public void initDatabase(SQLiteDatabase db) {
                    boolean ok = DatabaseUtils.newieGymnastic(str);
                }
            });
        }
    }


    public <T> List<String> getInsertSql(String tablename, List<T> list) {
        if (NullUtils.isEmptyList(list)) return null;
        List<String> sql = new ArrayList<String>();
        for (T item : list) {
            sql.add(getInsertSql(tablename, item));
        }
        return sql;
    }

    public <T> String getInsertSql(String tablename, T item) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("insert into " + tablename + "(");
        HashMap<String, String> lMap = Database2BeanUtils.getBeanMap(item);
        if (lMap != null && lMap.size() > 0) {
            for (String column : lMap.keySet()) {
                buffer.append(column).append(",");
            }
            buffer.replace(buffer.length() - 1, buffer.length(), ") values (");
            for (String column : lMap.keySet()) {
                buffer.append(lMap.get(column)).append(",");
            }
        }
        buffer.replace(buffer.length() - 1, buffer.length(), ");");
        return buffer.toString();
    }


    public <T> StringBuffer getUpdateSql( String table, T item) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("update " + table + " set ");
        HashMap<String, String> lMap = Database2BeanUtils.getBeanMap(item);
        if (lMap != null && lMap.size() > 0) {
            for (String column : lMap.keySet()) {
               if(Database2BeanUtils.containAnnatiton(item.getClass(),column, IncreateColumn.class)
                       ||column.equals("_id"))
                   continue;
                buffer.append(column).append("=").append(lMap.get(column)).append(",");
            }
        }
        buffer.delete(buffer.length() - 1, buffer.length());
        return buffer;
    }


    public void insert(final String tableName, final List<T> list) {
        ThreadHelper.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                DatabaseUtils.newieGymnastic(getInsertSql(tableName, list), null);
            }
        });
    }

    public void insert(final String tableName, final T item) {
        ThreadHelper.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                DatabaseUtils.execute(getInsertSql(tableName, item));
            }
        });
    }


    public void insertSync(String tableName, List<T> list, OnBackUpdateData<ProcessBean> onBackUpdateData) {
            DatabaseUtils.newieGymnastic(getInsertSql(tableName, list),onBackUpdateData );
    }

    public void insertSync(final String tableName, final T item) {
            DatabaseUtils.execute(getInsertSql(tableName, item));
    }

}
