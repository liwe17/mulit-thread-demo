package com.thread.test;

/**
 * @Author: Doug Li
 * @Date 2021/2/5
 * @Describe: ThreadLocal变量的线程隔离性
 */
public class ThreadLocalDemo {

    //初始化值为5
    private static ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 5);  //构造函数

    public static void main(String[] args) throws Exception {
        //新建线程1,设置值为20
        Thread t1 = new Thread(() -> {
            tl.set(20);
            try {
                System.out.println(Thread.currentThread().getName() + "：获取tl值为" + tl.get());   // 开发者调用get()方法
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "：获取tl值为" + tl.get());  // // 开发者调用get()方法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread2");

        Thread t3 = new Thread(() -> {
            tl.set(15);
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "：获取tl值为" + tl.get());  // // 开发者调用get()方法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread3");

        //启动线程
        t1.start();
        t2.start();
        t3.start();

        //等待处理完成
        t1.join();
        t2.join();
        t3.join();
    }
}
