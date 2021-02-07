package com.thread.test;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @Author: Doug Li
 * @Date 2021/1/22
 * @Describe: 任务执行方式
 *
 * execute用于提交Runnable,单个任务异常不会影响其他任务执行,异常会被抛出
 * submit用于提交Callable,单个任务异常不会影响其他任务执行,异常不会显示抛出
 *
 */
public class TheadPoolExecutorExceptionDemo {

    public static void main(String[] args) {

    }

    /**
     * 提交方式一:出现异常抛出
     */
    public static void testExecute() {
        final ExecutorService executorService = new ThreadPoolExecutor(1, 3, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            executorService.execute(() -> {
                System.out.printf("第[%s]个任务执行完毕,线程名称:[%s] \n", fi, Thread.currentThread().getName());
                if (fi == 4) throw new RuntimeException("抛出异常");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        for (; ; ) {
            if (executorService.isTerminated()) {
                break;
            }
        }
        System.err.println("任务执行完毕");
    }


    /**
     * 异常不抛出
     */
    public static void testSubmit() {
        final ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 3, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            executorService.submit(() -> {
                System.out.printf("第[%s]个任务执行完毕,线程名称:[%s] \n", fi, Thread.currentThread().getName());
                if (fi == 4) throw new RuntimeException("抛出异常");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        for (; ; ) {
            if (executorService.isTerminated()) {
                break;
            }
        }
        System.err.println("任务执行完毕");
    }


    /**
     * 重新afterExecute方法
     */
    public static void testSubmitGetException() {
        final ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 3, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10)) {
            //执行后进行的操作
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (t == null && r instanceof Future<?>) {
                    try {
                        Object result = ((Future<?>) r).get();
                    } catch (CancellationException ce) {
                        t = ce;
                    } catch (ExecutionException ee) {
                        t = ee.getCause();
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt(); // ignore/reset
                    }
                }
                if (t != null)
                    System.err.println(t);
            }
        };
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            executorService.submit(() -> {
                System.err.printf("第[%s]个任务执行完毕,线程名称:[%s] \n", fi, Thread.currentThread().getName());
                if (fi == 4) throw new RuntimeException("抛出异常");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        for (; ; ) {
            if (executorService.isTerminated()) {
                break;
            }
        }
        System.err.println("任务执行完毕");
    }

}
