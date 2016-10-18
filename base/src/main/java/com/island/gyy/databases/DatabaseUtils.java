package com.island.gyy.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.island.gyy.base.bean.ProcessBean;
import com.island.gyy.interfaces.OnBackUpdateData;
import com.island.gyy.thread.ThreadHelper;
import com.island.gyy.utils.LogUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * SQLite 数据库工具类
 * @author BD
 * 
 */
public class DatabaseUtils {
	
	private static final String TAG = "DatabaseUtils";
	
	private volatile static SQLiteDatabase db;
	
	private static String databasePath;
	
	/**
	 * 数据库默认名称
	 */
	public static String databaseName = "sqlite.db";
	
	
	/**
	 * 创建 SQLite 数据库
	 * @param path     : 数据文件路径 
	 * @param callback : 初始化回调接口
	 * @return         : SQLiteDatabase
	 */
	public static SQLiteDatabase createDatabase(String path, DatabaseInitCallback callback) {
		
		databasePath = new StringBuilder().append(path).append("/").append(databaseName).toString();
		
		if(!new File(databasePath).exists()) {
			db = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
			if(callback != null) callback.initDatabase(db);      // 初始化数据库
			
		}else {
			db = db != null && db.isOpen() ? db : SQLiteDatabase.openDatabase(databasePath, null, 0);
		}
		return db;
	}
	
	/**
	 * 获取 SQLite 数据库
	 * @return 处于打开状态的 SQLite 数据库
	 */
	public static SQLiteDatabase getSQLiteDatabase() {
		if(db == null) {
			throw new NullPointerException("SQLiteDatabase 为空");
		}
		return db = db.isOpen() ? db : SQLiteDatabase.openDatabase(databasePath, null, 0);
	}
	
	/**
	 * 插入数据方法
	 * @param values    : 内容值
	 * @return          : 插入数据的id
	 */
	public static int insert(String tableName, ContentValues values) {
		try {
			db = DatabaseUtils.getSQLiteDatabase();
			return (int) db.insert(tableName, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}


	
	/**
	 * 删除数据的方法
	 * @param tableName : 表名
	 * @param where     : 删除条件
	 * @return
	 */
	public static int delete(String tableName, String where) {
		try {
			db = DatabaseUtils.getSQLiteDatabase();
			return (int) db.delete(tableName, where, null);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 更新数据方法
	 * @param tableName : 表名
	 * @param values    : 更新的内容值
	 * @param where     : 更新的条件
	 * @return
	 */
	public static int updata(String tableName,ContentValues values,String where) {
		try {
			db = DatabaseUtils.getSQLiteDatabase();
			return (int) db.update(tableName, values, where, null);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 查询数据
	 * @param sql    : 查询的SQL
	 * @param values : 占位符值
	 * @return
	 */
	public static Cursor query(String sql, String[] values){
		try {
			db = DatabaseUtils.getSQLiteDatabase();
			return db.rawQuery(sql, values);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询数据
	 * @param sql    : 查询的SQL
	 * @param values : 占位符值
	 * @return
	 */
	public static void execute(String sql){
		try {
			DatabaseUtils.getSQLiteDatabase().execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 事物操作
	 * @param sqls : sql 语句
	 * @return     : 是否成功
	 */
	public static boolean newieGymnastic(String[] sqls) {
		List<String> list =	Arrays.asList(sqls);
		return newieGymnastic(list,null);
	}

  static ProcessBean lProcessBean;

	/**
	 * 事物操作
	 * @param sqlList : sql 语句
	 * @return     : 是否成功
	 */
	public static boolean newieGymnastic(List<String> sqlList, final OnBackUpdateData<ProcessBean> onBackUpdateData) {
		lProcessBean = new ProcessBean();

		try {
			lProcessBean.total = sqlList.size();
			db = DatabaseUtils.getSQLiteDatabase();
			db.beginTransaction();  	     // 开始事物
			for(int i= 0; i < sqlList.size();i++){
				db.execSQL(sqlList.get(i));
				if(null != onBackUpdateData) {
					lProcessBean.process = i;
					onBackUpdateData.updata(lProcessBean);
				}
			}
			db.setTransactionSuccessful();   // 设置事物标记为成功
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(TAG, "事物操作失败");
			lProcessBean.process = -1;
			if(null != onBackUpdateData){
				ThreadHelper.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onBackUpdateData.updata(lProcessBean);
					}
				});
			}
			return false;
		}finally{
			 db.endTransaction();
		}
	}


	/**
	 * 关闭数据库
	 * @param db
	 */
	public static void closeDatabase(SQLiteDatabase db) {
		if(db != null && db.isOpen()) db.close(); 
	}
	
	/**
	 * 关闭数据库
	 */
	public static void closeDatabase() {
		if(db != null && db.isOpen()) db.close(); 
	}
	
	/**
	 * 数据库初始化回调接口
	 * @author BD
	 */
	public interface DatabaseInitCallback {
		
		/**
		 * 初始化数据库
		 * @param db 
		 */
		void initDatabase(SQLiteDatabase db);
	}
}
