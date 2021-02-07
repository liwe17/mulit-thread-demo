package com.thread.test;

/**
 * @Author: Doug Li
 * @Date 2021/2/5
 * @Describe: 测试线程本地变量
 */
public class ThreadLocalTest01 {

    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws Exception{
        tl.set("1");
        System.out.println(String.format("当前线程名称: %s, main方法内获取线程内数据为: %s",
                Thread.currentThread().getName(), tl.get()));
        fc();
        new Thread(ThreadLocalTest01::fc).start();
        Thread.sleep(1000L); //保证下面fc执行一定在上面异步代码之后执行
        fc(); //继续在主线程内执行，验证上面那一步是否对主线程上下文内容造成影响
    }

    private static void fc() {
        System.out.println(String.format("当前线程名称: %s, fc方法内获取线程内数据为: %s",
                Thread.currentThread().getName(), tl.get()));
    }
}
