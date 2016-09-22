package com.island.gyy.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements UncaughtExceptionHandler {

	/**
	 * 保存地址
	 */
	private static final String path = Environment.getExternalStorageDirectory().getPath() + "/log";

	private static final String file_name = "crash";

	private static final String file_name_suffix = ".trace";

	private static CrashHandler crashHandler = new CrashHandler();

	private static boolean DEBUG = true;

	private Context mContext;

	private UncaughtExceptionHandler uncaughtExceptionHandler;

	public static synchronized CrashHandler getInstance() {
		return crashHandler;
	}

	/**
	 * 初始化
	 * @param mContext 上下文
	 */
	public void init(Context context) {
		uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		mContext = context.getApplicationContext();
	}

	public CrashHandler() {
	}

	/**
	 * 当有异常会调用此方法
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
		//写入SD卡
		dumpException(ex);
		//上传至服务器
		
		
		ex.printStackTrace();
		if(uncaughtExceptionHandler != null){
			uncaughtExceptionHandler.uncaughtException(thread, ex);
		}else {
			Process.killProcess(Process.myPid());
		}
		
		
	}

	private void dumpException(Throwable throwable) {

		// 判断存储卡是否可用
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (DEBUG) {
				return;
			}
		}

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		long timeMillis = System.currentTimeMillis();
		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis));
		File file2 = new File(path + file_name + format + file_name_suffix);

		try {
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
			printWriter.println(format);
			//将错误信息及手机信息写如本地
			dumPhoneInfo(printWriter);

			printWriter.println();
			throwable.printStackTrace(printWriter);
			printWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	/**
	 * 获取手机信息
	 * @param printWriter
	 * @throws NameNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	private void dumPhoneInfo(PrintWriter pw) throws NameNotFoundException {
		PackageManager packageName = mContext.getPackageManager();
		PackageInfo packageInfo = packageName.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
		pw.print("App Version:　");
		pw.print(packageInfo.versionName);
		pw.print("_");
		pw.print(packageInfo.versionCode);
		
		pw.print("OS Version:　");
		pw.print(Build.VERSION.RELEASE);
		pw.print("_");
		pw.print(Build.VERSION.SDK_INT);
		
		//手机制作商
		pw.print("Vendor:　");
		pw.print(Build.MANUFACTURER);
		//手机型号
		pw.print("Model:　");
		pw.print(Build.MODEL);
		//CPU架构
		pw.print("CPU ABI:　");
		pw.print(Build.CPU_ABI);
		
	}
}


































