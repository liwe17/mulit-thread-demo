package com.thread.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 业务层
 */
public interface ITestService {

    /**
     * 发送权益
     * @param custrNum:客户号
     * @param phone:手机号
     * @param svcNo:权益编号
     * @return 发放结果
     */
    Map<String,Object> grantEquities(String custrNum,String phone,String svcNo);
}
