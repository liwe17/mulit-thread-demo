package com.thread.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Doug Li
 * @Date 2021/2/5
 * @Describe: 应用场景1
 *  SimpleDateFormat线程不安全问题
 */
public class ThreadLocalDemo02 {


    //线程不安全
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    //使用ThreadLocal
    private static final ThreadLocal<SimpleDateFormat> tlSdf = ThreadLocal.withInitial(()->new SimpleDateFormat("yyyyMMdd hh:mm:ss"));

    public static void main(String[] args) {
        testSimpleDateFormat();
    }

    private static void testSimpleDateFormat(){
        final String dateStr = "20210205 19:00:00";
        final ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(()->{
                try {
//                    System.out.println(sdf.parse(dateStr));
                    System.out.println(tlSdf.get().parse(dateStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        for(;;){
            if(executorService.isTerminated()){
                break;
            }
        }
        System.err.println("任务执行完成");
    }

}
