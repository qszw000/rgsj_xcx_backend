package cn.hsy.echo.handle;

import cn.hsy.echo.enums.ErrorEnum;
import cn.hsy.echo.exception.CodeErrorException;
import cn.hsy.echo.exception.ParameterIllegalException;
import cn.hsy.echo.exception.TokenException;
import cn.hsy.echo.pojo.Result;
import cn.hsy.echo.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ClassCastException.class)
    private Result classCastExceptionHandle() {
        return ResultUtil.error(ErrorEnum.PARAM_ILLEGAL);
    }

    @ExceptionHandler(TokenException.class)
    private Result tokenExceptionHandle(TokenException e) {
        log.error("tokenException");
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(CodeErrorException.class)
    private Result codeErrorExceptionHandle() {
        return ResultUtil.error(ErrorEnum.CODE_EXPIRE);
    }

    @ExceptionHandler(ParameterIllegalException.class)
    private Result parameterIllegalExceptionHandle(ParameterIllegalException e) {
        return ResultUtil.error(e.getCode(), e.getMessage());
    }
}
