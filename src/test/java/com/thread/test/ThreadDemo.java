package com.thread.test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: Doug Li
 * @Date 2021/1/6
 * @Describe: 线程的创建方式
 *
 *
 *  public class Thread implements Runnable{
 *      // What will be run.
 *      private Runnable target;
 *
 *     @Override
 *     public void run() {
 *         if (target != null) {
 *             target.run();
 *         }
 *     }
 *
 *     //常用的两个构造方法
 *     public Thread(Runnable target, String name) {
 *         init(null, target, name, 0);
 *     }
 *
 *     public Thread(Runnable target) {
 *         init(null, target, "Thread-" + nextThreadNum(), 0);
 *     }
 *  }
 */
public class ThreadDemo{

    public static void main(String[] args) throws Exception{
        new Thread1().start(); //第一种
        new Thread(new Thread2()).start(); //第二种
        //第三种
        final FutureTask<Integer> futureTask = new FutureTask<>(new Thread3());
        new Thread(futureTask).start();
        System.err.println(futureTask.get());
    }


    /**
     * 通过继承Thread类,缺点:JAVA只支持单继承
     */
    static class Thread1 extends Thread{

        @Override
        public void run() {
            System.out.println("创建方式一:继承Thread类,重写run方法...");
        }
    }

    /**
     *  通过实现Runnable接口,不能获取返回值,无法抛出显示异常
     */
    static class Thread2 implements Runnable{

        @Override
        public void run() {
            System.out.println("创建方式二:实现Runnable接口,重写run方法...");
        }
    }

    /**
     *  可以抛出异常,也可以获取返回值
     */
    static class Thread3 implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            System.out.println("创建方式三:实现Callable接口,重写call方法...");
            return 1;
        }
    }
}
