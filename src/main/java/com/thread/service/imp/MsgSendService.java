package com.thread.service.imp;

import com.thread.service.IMsgSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 发送消息实现类
 */
@Service
@Slf4j
public class MsgSendService implements IMsgSendService {

    /**
     * 发送消息
     * @param content 消息内容
     * @param phone 手机号
     */
    @Override
    @Async("msgExecutor")
    public void sendMsg(String content, String phone) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println(String.format("发送信息成功,手机号:[%s],内容:[%s],线程名:[%s]",phone,content,Thread.currentThread().getName()));
        } catch (Throwable ex) {
            log.error("消息发送异常,异常信息:[{]]",ex.getMessage(),ex);
        }
    }
}
