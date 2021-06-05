package com.study.loge.betchat.aop.log;

import com.study.loge.betchat.utils.parser.StompHeaderParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

// 채팅방 출입을 출력하기 위한 클래스입니다.
@Aspect
@Component
public class RoomEntranceLogger {
    private Logger logger = LoggerFactory.getLogger(RoomEntranceLogger.class);

    @Pointcut("execution(* com.study.loge.betchat.monitor.RoomEntranceManager.*(..))")
    public void entranceLogPrint() {}

    @Around("entranceLogPrint())")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Message<?> message = (GenericMessage)joinPoint.getArgs()[0];

        SimpMessageType messageType = StompHeaderParser.getSimpMessageType(message);
        String userKey = StompHeaderParser.getUserKey(message);
        String roomKey = StompHeaderParser.getRoomKey(message);

        if(userKey!=null && roomKey!=null) logger.info(messageType + " : " + userKey + " -> " + roomKey);

        Object ret = joinPoint.proceed();

        return ret;

    }
}
