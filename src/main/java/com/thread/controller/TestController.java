package com.thread.controller;

import com.thread.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 控制层
 */

@RestController
public class TestController {

    @Autowired
    private ITestService testService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 场景描述:
     *  1. 第三方系统给调用我方系统给客户发送权益服务
     *  2. 我方处理流程如下:
     *      1> 给客户创建权益
     *      2> 调用消息服务系统发送即使消息
     * 发送权益
     * @param custrNum:客户号
     * @param phone:手机号
     * @param svcNo:权益编号
     * @return 发放结果
     */
    @GetMapping("/grantEquities/{custrNum}/{phone}/{svcNo}")
    public Map<String,Object> grantEquities(@PathVariable("custrNum") String custrNum,@PathVariable("phone") String phone,@PathVariable("svcNo") String svcNo){
        return testService.grantEquities(custrNum,phone,svcNo);
    }



    // 同时发送100个请求
    private static final int THREAD_NUM = 100; //线程数量

    private static final CountDownLatch cdl = new CountDownLatch(THREAD_NUM); //发枪器

    @GetMapping("/testBatchGrantEquities")
    public Map<String,Object> testBatchGrantEquities(){
        for (int i = 0; i < THREAD_NUM; i++) {
            final int fi = i;
            new Thread(()->{
                try {
                    cdl.await();//阻塞直到cdl的值为0
                    restTemplate.getForObject(String.format("http://127.0.0.1:8080/grantEquities/%s/%s/%s",fi,fi,fi), Map.class);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"cdl-"+i).start();
            cdl.countDown(); //cdl的值减少1
        }
        return new HashMap<String,Object>(){{
            put("code",200);
            put("msg","执行开始");
        }};
    }


    //获取当前CPU核心数
    public static void main(String[] args) {
        System.err.println(Runtime.getRuntime().availableProcessors()); //获取CPU核心数
    }
}
