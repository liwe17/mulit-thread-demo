package com.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2021/1/7
 * @Describe: 通过Executors线程池的创建
 */
public class ExecutorsDemo {

    public static void main(String[] args) {
        testNewScheduledThreadPool();
    }


    /**
     * 创建只有一个线程的线程池,任务队列大小为Integer.MAX
     * 由于任务队列过大-->存在内存溢出的问题
     */
    public static void testNewSingleThreadExecutor(){
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i=0;i<10;i++){
            final int fi=i;
            executorService.execute(()->{
                System.err.printf("第[%s]个任务开始执行,线程名称:[%s] \n",fi,Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            });
        }
        //关闭线程池<=>不在接收新任务
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完成");
    }


    /**
     * 创建一个初始线程个数为0,最大线程个数为Integer.MAX_VALUE线程池
     * 由于线程数过大-->导致资源耗尽
     */
    public static void testNewCachedThreadPool(){
        final ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<10;i++){
            final int fi=i;
            executorService.execute(()->{
                System.err.printf("第[%s]个任务开始执行,线程名称:[%s] \n",fi,Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            });
        }
        //关闭线程池<=>不在接收新任务
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完成");
    }

    /**
     * 创建一个固定线程数的线程,任务队列大小为Integer.MAX
     * 由于队列过大->导致内存溢出
     */
    public static void testNewFixedThreadPool(){
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i=0;i<10;i++){
            final int fi=i;
            executorService.execute(()->{
                System.err.printf("第[%s]个任务开始执行,线程名称:[%s] \n",fi,Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            });
        }
        //关闭线程池<=>不在接收新任务
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完成");
    }

    /**
     * 创建一个1个线程数的线程池,最大线程数为Integer.MAX
     * 周期性的执行队列中的任务
     */
    public static void testNewScheduledThreadPool(){
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        for(int i=0;i<17;i++){
            final int fi=i;
            executorService.schedule(()->{
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n",fi,Thread.currentThread().getName());
            },10,TimeUnit.SECONDS);
        }
        //关闭线程池<=>不在接收新任务
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完成");
    }

}