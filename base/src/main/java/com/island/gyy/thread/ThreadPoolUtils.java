package com.island.gyy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * 
 * @author BD
 * 
 */
public class ThreadPoolUtils {

	private static ThreadPoolExecutor threadPoolExecutor;

	private ThreadPoolUtils() {
	}

	public static ThreadPoolExecutor getThreadPoolExecutor() {
		if (threadPoolExecutor == null) {
			synchronized (ThreadPoolUtils.class) {
				if (threadPoolExecutor == null) {
					threadPoolExecutor = new ThreadPoolExecutor(3, // 设置核心池大小
							80,                                    // 设置最大线程数
							30L,                                   // 设置没有任务时线程销毁时间
							TimeUnit.SECONDS,                      // 设置时间单位
//							new SynchronousQueue<Runnable>());     // 不会保存提交的任务，而是将直接新建一个线程来执行新来的任务
//							new ArrayBlockingQueue<Runnable>(1024));// 基于数组的先进先出队列，此队列创建时必须指定大小
							new LinkedBlockingQueue<Runnable>());  // 基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE
					initSingleCoreThread();
				}
			}
		}
		return threadPoolExecutor;
	}

	/**
	 * 初始化一个核心线程
	 */
	private static final void initSingleCoreThread() {
		threadPoolExecutor.prestartCoreThread();
	}

	/**
	 * 初始化所有核心线程
	 */
	@SuppressWarnings("unused")
	private static final void initAllCoreThread() {
		threadPoolExecutor.prestartAllCoreThreads();
	}

	/**
	 * 执行任务
	 * @param runnable
	 */
	public static final void execute(Runnable runnable) {
		createThreadPool();
		if (runnable == null) {
			throw new NullPointerException("Runnble 不能为空");
		}
		threadPoolExecutor.execute(runnable);
	}
	
	/**
	 * 提交任务给线程池执行并返回结果
	 * @param runnable
	 */
	public static final <T> Future<T> submit(Callable<T> task) {
		if (task == null) {
			throw new NullPointerException("Runnble 不能为空");
		}
		createThreadPool();
		return threadPoolExecutor.submit(task);
	}
	
	/**
	 * 提交任务给线程池执行并返回结果
	 * @param runnable
	 */
	public static final Future<?> submit(Runnable task) {
		if (task == null) {
			throw new NullPointerException("Runnble 不能为空");
		}
		createThreadPool();
		return threadPoolExecutor.submit(task);
	}

	/**
	 * 打印线程池的相关信息
	 */
	public static final void printThreadPoolInfo() {
		System.out.println("线程池中线程数目：" + threadPoolExecutor.getPoolSize()
				+ "，队列中等待执行的任务数目：" + threadPoolExecutor.getQueue().size()
				+ "，已执行别的任务数目：" + threadPoolExecutor.getCompletedTaskCount());
	}

	/**
	 * 设置核心池大小
	 * 
	 * @param size
	 */
	public static final void setCorePoolSize(int size) {
		createThreadPool();
		threadPoolExecutor.setCorePoolSize(size);
	}

	/**
	 * 设置线程池最大能创建的线程数目大小
	 * @param size
	 */
	public static final void setMaximumPoolSize(int size) {
		createThreadPool();
		threadPoolExecutor.setMaximumPoolSize(size);
	}

	/**
	 * 判断线程池是否为空,为空则创建
	 */
	private static final void createThreadPool() {
		if (threadPoolExecutor == null) {
			getThreadPoolExecutor();
		}
	}

	/**
	 * 发送终止命令,不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
	 */
	public static final void shutdown() {
		if (threadPoolExecutor != null) {
			threadPoolExecutor.shutdown();
		}
	}

	/**
	 * 立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务
	 */
	public static final void shutdownNew() {
		if (threadPoolExecutor != null) {
			threadPoolExecutor.shutdownNow();
		}
	}
}
