package com.atguigu.crypto.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecTimeLogAspect {

    public static final Logger logger = LoggerFactory.getLogger(ExecTimeLogAspect.class);

    @Around("@annotation(ExecTimeLog)")
    public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // 获取拦截的类名和方法名
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        // 配置计时器ID，开始计算时间
        StopWatch stopWatch = new StopWatch(className + "->" + methodName);
        stopWatch.start(methodName);
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        // 打印方法执行时间
        if (logger.isInfoEnabled()) {
            logger.info("方法：{}，执行时间：{}秒", stopWatch.getId(), stopWatch.getTotalTimeSeconds());
        }

        return result;
    }
}
