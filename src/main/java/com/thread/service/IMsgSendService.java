package com.thread.service;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 消息发送
 */
public interface IMsgSendService {

    /**
     * 发送消息
     * @param content 消息内容
     * @param phone 手机号
     */
    void sendMsg(String content,String phone);
}
