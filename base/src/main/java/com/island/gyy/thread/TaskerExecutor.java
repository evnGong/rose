package com.island.gyy.thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskerExecutor extends ThreadPoolExecutor {

	public static final String TAG = "TaskerExecutor";
	
	private static TaskerExecutor mTaskerExecutor;
	
	private BlockingQueue<Runnable> mBlockingQueue;
	
	
	public static final TaskerExecutor getInstance() {
		if(mTaskerExecutor == null) {
			synchronized (TAG) {
				if(mTaskerExecutor == null) {
					mTaskerExecutor = new TaskerExecutor();
				}
			}
		}
		return mTaskerExecutor;
	}
	
	
	public TaskerExecutor() {
		this(3, 80, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
	
	public TaskerExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		this.prestartCoreThread();       // 初始化一个核心线程
		this.mBlockingQueue = workQueue;
	}
	
	/**
	 * 添加一个异步任务
	 * @param index : -1 表示清空原有队列
	 * @param runnable
	 */
	public synchronized final boolean add(int index, Runnable runnable) {
		if (runnable == null)
            throw new NullPointerException();
		
		if(index < 0 && mBlockingQueue.size() > 0) {
			mBlockingQueue.clear();
		}
//		如果线程池已关闭或正在关闭则不添加
		if(isShutdown() || isTerminating()) return false;
		return mBlockingQueue.add(runnable);
	}
	
	
	@Override
	public synchronized void execute(Runnable command) {
		super.execute(command);
	}
	
	
	public final BlockingQueue<Runnable> getBlockingQueue() {
		return mBlockingQueue;
	}
	
	/**
	 * 打印线程池的相关信息
	 */
	public void printThreadPoolInfo() {
		Log.e(TAG, new StringBuilder("线程池中线程数目 : ").append(this.getPoolSize())
				   .append("\r\n队列中等待执行的任务数目 : ").append(mBlockingQueue.size())
				   .append("\r\n已执行别的任务数目 : ").append(this.getCompletedTaskCount()).toString());
	}
}
