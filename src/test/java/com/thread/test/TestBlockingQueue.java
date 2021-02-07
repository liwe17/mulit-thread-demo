package com.thread.test;

import java.util.concurrent.*;

/**
 * @Author: Doug Li
 * @Date 2021/2/3
 * @Describe: 测试阻塞队列,LinkedBlockingQueue 更适合生产者数量少于消费者的情况；
 */
public class TestBlockingQueue {

    private static final int CONSUMER_THREAD_NUM = 100; //消费线程数量
    private static final int PRODUCER_THREAD_NUM = 100; //生产线程数量

    private static final int FOR_NUM = 10000; //每个线程放入队列中的元素个数

    //阻塞队列
//    private static final BlockingQueue<String> workQueue = new ArrayBlockingQueue<>(CONSUMER_THREAD_NUM*FOR_NUM);
    private static final BlockingQueue<String> workQueue = new LinkedBlockingQueue<>(CONSUMER_THREAD_NUM*FOR_NUM);

    public static void main(String[] args) throws Exception{
        testBlockingQueue();
        //100
//        LinkedBlockingQueue 耗时:[570]
        //ArrayBlockingQueue 耗时:[665]
        // 消费100,生产10 --->需要消费者FOR_NUM/10,保证生产的和消费的数量一致
        //ArrayBlockingQueue 耗时:[547]
        //LinkedBlockingQueue 耗时:[392]

    }

    public static void testBlockingQueue() throws InterruptedException {
        //记录消费者线程数组
        final Thread[] consumers = new Thread[CONSUMER_THREAD_NUM];
        for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
            //创建消费者线程,消费者负责往阻塞队列中添加元素
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < FOR_NUM; j++) {
                    try {
                        workQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Consumer-" + i);
        }

        //记录生产者线程数组
        final Thread[] producers = new Thread[PRODUCER_THREAD_NUM];
        for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
            //创建生产者线程并从队列中添加元素
            producers[i] = new Thread(() -> {
                for (int j = 0; j < FOR_NUM; j++) {
                    final int jf = j;
                    try {
                        workQueue.put(Thread.currentThread().getName() + ":" + jf);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Producer-" + i);
        }

        //记录时间
        long start = System.currentTimeMillis();

        //启动生产者线程
        for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
            producers[i].start();
        }

        //启动消费者线程
        for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
            consumers[i].start();
        }

        //等待生产者完成
        for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
            producers[i].join();
        }

        //等待消费者完成
        for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
            consumers[i].join();
        }


        System.err.println(workQueue.getClass().getSimpleName()+" 耗时:[" + (System.currentTimeMillis() - start) + "]");

    }

}
