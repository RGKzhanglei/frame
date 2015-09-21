package com.example.business.mybusiness.communication.thread;

import com.example.business.mybusiness.communication.model.ResponseModel;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhang.la on 2015/9/8.
 */
public class ThreadPool extends ThreadPoolExecutor {

    private static ThreadPool mInstance;
    private static SynchronousQueue<Runnable> workQueue = null;
    private final BlockingQueue<Runnable> rejectTasks = new LinkedBlockingQueue<Runnable>();
    private HashMap<String, ArrayBlockingQueue<ResponseModel>> nowExeTask = new HashMap<>();

    private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        // 如果當前线程池没有空闲线程并拒绝了这个任务的话就会把任务放到拒绝队列里面
        super.setRejectedExecutionHandler(new RejectedExecutionHandler() {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                rejectTasks.offer(r);
            }
        });
    }

    public static ThreadPool getInstance() {
        if (null == mInstance) {
            /*核心线程数*/
            int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
            /*最大线程数*/
            int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 10;
            long keepAliveTime = 60;
            /*线程队列*/
            workQueue = new SynchronousQueue<Runnable>();
            mInstance = new ThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        }
        return mInstance;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Runnable rejectRunnable = rejectTasks.poll();
        if (null != rejectRunnable) {
            super.execute(rejectRunnable);
        }
    }

    /**
     * 执行task
     * @param task
     * @return
     */
    public boolean executeTask(ThreadTask task) {
        if (task == null || !isNowExeTask(task.getToken())) return false;
        putNowExeTask(task.getToken());
        super.execute(task);
        return true;
    }

    private boolean isNowExeTask(String token) {
        if (token == null) return false;
        if (nowExeTask.get(token) != null) {
            return false;
        }
        return true;
    }

    private void putNowExeTask(String token) {
        nowExeTask.put(token, new ArrayBlockingQueue<ResponseModel>(1));
    }

    public HashMap<String, ArrayBlockingQueue<ResponseModel>> getNowExeTask() {
        return nowExeTask;
    }

//    /**
//     * 获取response
//     * @param token
//     * @param handler
//     * @param message
//     */
//    public void getResponseModel(String token, Handler handler, Message message) {
//        ThreadReadData readData = new ThreadReadData(token, handler, message);
//        readData.start();
//    }
}
