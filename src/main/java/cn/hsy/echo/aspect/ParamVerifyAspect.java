package cn.hsy.echo.aspect;

import cn.hsy.echo.enums.ErrorEnum;
import cn.hsy.echo.exception.ParameterIllegalException;
import cn.hsy.echo.util.AspectUtil;
import cn.hsy.echo.util.ParamValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@Slf4j
public class ParamVerifyAspect {

    @Pointcut("@annotation(cn.hsy.echo.aspect.annotation.VerifyPageParam))")
    public void verifyPageParam() {}

    @Before("verifyPageParam()")
    public void doVerifyPageParam(JoinPoint joinPoint) {
        Map<String, Object> param = AspectUtil.getParamMap(joinPoint);
        Integer pageNum = (Integer) param.get("pageNum");
        if (!ParamValidUtil.verifyPageNum(pageNum)) {
            throw new ParameterIllegalException(ErrorEnum.PAGE_NUM_ILLEGAL);
        }
        Integer pageSize = (Integer) param.get("pageSize");
        if (!ParamValidUtil.verifyPageSize(pageSize)) {
            throw new ParameterIllegalException(ErrorEnum.PAGE_SIZE_ILLEGAL);
        }
    }
}
