package com.thread.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: Doug Li
 * @Date 2021/1/31
 * @Describe: 系统日志切面
 */
@Component
@Aspect
@Slf4j
public class SystemLogAspect {

    @Pointcut("execution(public * com.thread.service..*.*(..))") //切入点描述
    public void pointCut(){}

    /**
     * 前置通知
     */
    @Before("pointCut()")
    public void beforeAdvice(JoinPoint joinPoint){
//        log.info("AOP Before");
        final Object[] args = joinPoint.getArgs(); //获取目标参数信息
        joinPoint.getThis();//AOP代理类信息
        joinPoint.getTarget();//代理目标类
        final Signature signature = joinPoint.getSignature();//获取签名
        signature.getName();//代理方法名
        signature.getDeclaringTypeName();//AOP代理类名
        signature.getDeclaringType();//AOP代理类(class)信息
    }

    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
//            log.info("AOP Around");
            result = proceedingJoinPoint.proceed();
        }catch (Throwable throwable){
            System.out.println("AOP Around Exception:"+throwable);
        }
        log.info("方法执行完成,方法名:[{}],耗时:[{}]",proceedingJoinPoint.getSignature().getName(),System.currentTimeMillis()-startTime);
        return result;
    }

    /**
     * 后置通知
     */
    @After("pointCut()")
    public void returnAfter(JoinPoint joinPoint){
        //log.info("AOP After");
    }

    /**
     * 返回通知
     */
    @AfterReturning(pointcut = "pointCut()",returning = "result")
    public void afterReturning(JoinPoint joinPoint,Object result){
//        log.info("AOP AfterReturning:"+result);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "pointCut()",throwing = "exception")
    public void AfterThrowing(JoinPoint joinPoint,Exception exception){
//        log.info("AOP AfterThrowing:"+exception);
    }

}
