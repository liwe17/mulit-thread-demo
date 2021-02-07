package com.thread.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2021/1/7
 * @Describe: 拒绝策略
 */
public class ThreadPoolExecutorHandlerDemo {


    public static void main(String[] args) {
        testAbortPolicy();
    }

    /**
     * 模拟任务耗时
     */
    public static void executeTime(){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 多余任务拒绝处理,并抛出异常
     */
    public static void testAbortPolicy(){
        final ExecutorService executorService = new ThreadPoolExecutor(1, 3, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2));
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            try{
                executorService.execute(()->{
                    //ThreadPoolExecutorHandlerDemo.executeTime();
                    System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
                });
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        executorService.shutdown(); //线程池不在接收新任务
        for(;;){
            //如果线程池进入终止状态,则说明任务全部执行完成
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完毕");
    }

    /**
     * 多余任务直接丢弃,静默处理,无感知
     */
    public  static void testDiscardPolicy(){
        final ExecutorService executorService = new ThreadPoolExecutor(1, 3,
                1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            executorService.execute(()->{
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完毕");
    }

    /**
     * 丢弃最老的任务
     */
    public static void testDiscardOldestPolicy(){
        final ExecutorService executorService = new ThreadPoolExecutor(1, 3,
                1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            executorService.execute(()->{
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完毕");
    }

    /**
     * 由当前调用者线程处理任务
     */
    public static void testCallerRunsPolicy(){
        final ExecutorService executorService = new ThreadPoolExecutor(1, 3,
                1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            executorService.execute(()->{
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完毕");
    }
}
