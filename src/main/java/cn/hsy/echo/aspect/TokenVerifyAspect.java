package cn.hsy.echo.aspect;

import cn.hsy.echo.enums.ErrorEnum;
import cn.hsy.echo.exception.TokenException;
import cn.hsy.echo.util.AspectUtil;
import cn.hsy.echo.util.TokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Aspect
@Component
public class TokenVerifyAspect {

    @Pointcut("execution(public * cn.hsy.echo.controller.WeChatController.*(..)) && !execution(public * cn.hsy.echo.controller.WeChatController.login(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify(JoinPoint joinPoint) {
        Map<String, Object> param = AspectUtil.getParamMap(joinPoint);
        String token = param.get("token").toString();
        if (StringUtils.isEmpty(token)) {
            throw new TokenException((ErrorEnum.TOKEN_NOT_EXIT));
        }
        if (!TokenUtil.verify(token)) {
            throw new TokenException(ErrorEnum.TOKEN_EXPIRE);
        }
    }
}
