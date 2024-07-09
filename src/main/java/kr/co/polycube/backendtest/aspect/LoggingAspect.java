package kr.co.polycube.backendtest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {
	
	@Pointcut("execution(* kr.co.polycube.backendtest.controller.UserController.*(..))")
    public void userControllerMethods() {
        
    }

    @Before("userControllerMethods()")
    public void logUserAgent(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userAgent = request.getHeader("User-Agent");
        System.out.println("Client User-Agent: " + userAgent);
    }

}
