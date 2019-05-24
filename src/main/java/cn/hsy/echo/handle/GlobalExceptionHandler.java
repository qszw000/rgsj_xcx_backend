package cn.hsy.echo.handle;

import cn.hsy.echo.exception.CodeErrorException;
import cn.hsy.echo.exception.ParameterIllegalException;
import cn.hsy.echo.exception.TokenExpireException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(ClassCastException.class)
    private Map<String, Object> classCastExceptionHandle() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", -1001);
        result.put("errMsg", "输入类型不正确");
        result.put("data", null);
        return result;
    }

    @ExceptionHandler(TokenExpireException.class)
    private Map<String, Object> tokenExpireExceptionHandle() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", -1002);
        result.put("errMsg", "toke已过期");
        result.put("data", null);
        return result;
    }

    @ExceptionHandler(CodeErrorException.class)
    private Map<String, Object> codeErrorExceptionHandle() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", -1005);
        result.put("errMsg", "code无效");
        result.put("data", null);
        return result;
    }

    @ExceptionHandler(ParameterIllegalException.class)
    private Map<String, Object> parameterIllegalExceptionHandle(ParameterIllegalException px) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", -1007);
        result.put("errMsg", px.getErrMsg());
        result.put("data", null);
        return result;
    }
}
