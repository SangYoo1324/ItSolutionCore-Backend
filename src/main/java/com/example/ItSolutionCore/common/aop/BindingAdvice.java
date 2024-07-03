package com.example.ItSolutionCore.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class BindingAdvice {

@Around("execution(* com.example.ItSolutionCore..service..*Service.*(..))")
    public Object CommonServiceExecutionTimer(ProceedingJoinPoint joinPoint) throws Throwable {
    //record
    // record the start time of the method
    long startTime = System.currentTimeMillis();

    try{
        //proceed the actual method without closing the AOP method
        return joinPoint.proceed();
    }catch (Throwable e){
        e.printStackTrace();
        throw e;
    }finally {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime-startTime;
        log.info("AOP::::: "+ joinPoint.getSignature()+ " executed in "+executionTime);
    }

}


}
