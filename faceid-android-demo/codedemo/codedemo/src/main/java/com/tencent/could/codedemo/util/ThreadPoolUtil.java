package com.tencent.could.codedemo.util;

import android.os.Looper;
import android.util.Log;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author jerrydong
 * @since 2020/7/27
 */
public class ThreadPoolUtil {

    private static final String TAG = "ThreadPoolUtil";
    // 线程核心数
    private static final int CORE_POOL_SIZE = 8;
    // 最大线程数目
    private static final int POOL_MAX_SIZE = 64;
    // 空闲线程的存活时间（秒）
    private static final int KEEP_ALIVE_TIME = 60;
    // 界面等待的额外时间长度
    private static final int WAIT_STATE_TIME = 150;
    // 线程池对象
    private ThreadPoolExecutor executor;

    /**
     * 私有构造方法
     */
    private ThreadPoolUtil() {
        initPool();
    }

    /**
     * 静态内部类，用来实现单例
     */
    private static final class ThreadPoolUtilHolder {

        private static final ThreadPoolUtil INSTANCE = new ThreadPoolUtil();
    }

    public static ThreadPoolUtil getInstance() {
        return ThreadPoolUtilHolder.INSTANCE;
    }

    /**
     * 线程池的初始化方法
     */
    private void initPool() {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, POOL_MAX_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 添加线程池执行任务
     *
     * @param runnable 任务
     */
    public void addWork(Runnable runnable) {
        if (!executor.isShutdown()) {
            executor.execute(runnable);
        } else {
            Log.e(TAG, "Thread Pool is ready shutdown!");
        }
    }

    /**
     * 停止线程池
     */
    public void showDown() {
        if (executor.isShutdown()) {
            return;
        }
        executor.shutdown();
    }

    /**
     * 让当前线程停止一定的时间
     */
    public void waitThreadTime() {
        if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
            Log.e(TAG, "Want wait thread in main thread!");
            return;
        }
        try {
            Thread.sleep(WAIT_STATE_TIME);
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException in sleep thread!");
        }
    }

}
