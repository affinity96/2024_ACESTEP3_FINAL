package com.example.kbfinal.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.core.ParameterNameDiscoverer;
import java.util.Base64;
import java.util.Map;

@Aspect
@Component
public class EncAndDec {

    private final ParameterNameDiscoverer parameterNameDiscoverer;

    @Autowired
    public EncAndDec(@Qualifier("parameterNameDiscoverer")  ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }


    public String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    // 비밀번호를 Base64로 복호화
    public String decryptPassword(String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }
}