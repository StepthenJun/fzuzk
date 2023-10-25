package zk.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zk.common.GlobalConstant;
import zk.domain.Login.User;
import zk.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class OperatorLogAop {
    @Autowired
    private HttpServletRequest request;
 
    //定义切点，注解作为切入点
    @Pointcut("execution(public * zk.controller..*.*(..))")
    public void logPoinCut() {
 
    }
 
 
/*    @Around("logPoinCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader(GlobalConstant.HEADER_TOKEN);
        if(!StringUtils.isEmpty(token)){
            // 可以从session获取 / token获取
            // 从token中获取到用户ID
            User user = TokenUtil.getUser(token);
        }
        return null;
    }*/
}