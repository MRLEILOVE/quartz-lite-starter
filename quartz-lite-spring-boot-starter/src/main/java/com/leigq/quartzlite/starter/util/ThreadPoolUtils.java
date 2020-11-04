package com.leigq.quartzlite.starter.util;

import java.util.concurrent.*;


/**
 * 线程池工具类
 * <br/>
 * 参考：<a>https://blog.csdn.net/weixin_38399962/article/details/81979295</a>
 * <br/>
 * 更多线程知识：<a>https://blog.csdn.net/u010266988/article/details/83960541</a>
 *
 * @author leigq
 * @date 2020 -06-12 17:02:27
 */
public final class ThreadPoolUtils {

	/**
	 * 根据cpu的数量动态的配置核心线程数和最大线程数
	 */
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	/**
	 * 核心线程数 = CPU核心数 + 1
	 */
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	/**
	 * 线程池最大线程数 = CPU核心数 * 2 + 1
	 */
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
	/**
	 * 非核心线程闲置时超时1s
	 */
	private static final int KEEP_ALIVE = 60;

	private ThreadPoolUtils() {
	}

	/**
	 * 无返回值直接执行
	 *
	 * @param runnable the runnable
	 * @author leigq
	 * @date 2020 -06-12 16:46:27
	 */
	public static void execute(Runnable runnable) {
		Holder.THREAD_POOL.execute(runnable);
	}


	/**
	 * 返回值直接执行,
	 *
	 * @param <T>      the type parameter
	 * @param callable the callable
	 * @return the future
	 * @author leigq
	 * @date 2020 -06-12 16:46:38
	 */
	public static <T> Future<T> submit(Callable<T> callable) {
		return Holder.THREAD_POOL.submit(callable);
	}


	/**
	 * 使用静态内部类实现单例模式获取 <code>ThreadPoolExecutor</code> 实例，线程安全。
	 *
	 * @author leigq
	 * @date 2020 -06-12 16:47:32
	 */
	private static class Holder {
		// // 使用 ThreadPoolExecutor 创建线程池，阿里 java 开发手册推荐，详见：https://blog.csdn.net/fly910905/article/details/81584675
		static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(32), new ThreadPoolExecutor.CallerRunsPolicy());
	}

}
