package cn.hsy.echo.exception;

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
}
