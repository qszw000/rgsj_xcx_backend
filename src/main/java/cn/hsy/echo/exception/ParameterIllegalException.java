package cn.hsy.echo.exception;

import cn.hsy.echo.enums.ErrorEnum;
import lombok.Getter;

@Getter
public class ParameterIllegalException extends RuntimeException {
    private int code;

    public ParameterIllegalException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ParameterIllegalException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
    }
}
