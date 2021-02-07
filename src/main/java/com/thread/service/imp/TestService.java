package com.thread.service.imp;

import com.thread.service.IMsgSendService;
import com.thread.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 业务实现层
 */
@Slf4j
@Service
public class TestService implements ITestService {


    @Autowired
    private IMsgSendService msgSendService;

    /**
     * 发送权益
     * @param custrNum:客户号
     * @param phone:手机号
     * @param svcNo:权益编号
     * @return 发放结果
     */
    @Override
    public Map<String, Object> grantEquities(String custrNum, String phone, String svcNo) {
        //同步发放权益
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //异步发送短信
        msgSendService.sendMsg(phone,"客户["+custrNum+"]权益发放成功");
        Map<String,Object> result = new HashMap<String,Object>(){{
            put("code",200);
            put("msg","发放成功");
        }};
        log.info("发放权益成功,客户号:[{}],手机号:[{}],权益编号:[{}]",custrNum,phone,svcNo);
        return result;

    }

}
