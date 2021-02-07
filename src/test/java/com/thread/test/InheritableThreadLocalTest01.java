package com.thread.test;

/**
 * @Author: Doug Li
 * @Date 2021/2/5
 * @Describe: 可继承的
 */
public class InheritableThreadLocalTest01 {

    private static final ThreadLocal<String> tl = new InheritableThreadLocal<>();

    public static void main(String[] args) throws Exception {
        tl.set("1");
        System.out.println(String.format("当前线程名称: %s, 获取线程内数据为: %s",
                Thread.currentThread().getName(), tl.get().hashCode()));
        final Thread thread = new Thread(InheritableThreadLocalTest01::fc);
        thread.start();
        Thread.sleep(1000L);
        tl.set("2");
        System.out.println(String.format("当前线程名称: %s, 获取线程内数据为: %s",
                Thread.currentThread().getName(), tl.get().hashCode())); //继续在主线程内执行，验证上面那一步是否对主线程上下文内容造成影响
        thread.join();
    }

    private static void fc() {
        for(int i =0;i<2;i++){
            System.out.println(String.format("当前线程名称: %s, 获取线程内数据为: %s",
                    Thread.currentThread().getName(), tl.get().hashCode()));
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
