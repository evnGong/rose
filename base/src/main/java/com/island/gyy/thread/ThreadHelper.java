package com.island.gyy.thread;
import android.os.Handler;
import android.os.Looper;

public class ThreadHelper {

	static final Handler sUIHandler = new Handler(Looper.getMainLooper());
	static final Handler sBackgroundHandler = BackgroundHandlerThread.getInstance().getHandler();

	public static void runOnUiThread(Runnable run) {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			run.run();
		} else {
			sUIHandler.post(run);
		}
	}

	public static void runOnUiThread(Runnable run, long delayMillis) {
		sUIHandler.postDelayed(run, delayMillis);
	}

	public static void runOnBackgroundThread(Runnable run) {
		if (Looper.myLooper() == sBackgroundHandler.getLooper()) {
			run.run();
		} else {
			sBackgroundHandler.post(run);
		}
	}

	public static void runOnBackgroundThread(Runnable run, long delayMillis) {
		sBackgroundHandler.postDelayed(run, delayMillis);
	}

	public static void cancelUiRun(Runnable runnable) {
		sUIHandler.removeCallbacks(runnable);
	}

	public static void cancelBackGroundRun(Runnable runnable) {
		sUIHandler.removeCallbacks(runnable);

	}

}
