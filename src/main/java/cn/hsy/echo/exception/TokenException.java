package cn.hsy.echo.exception;

import cn.hsy.echo.enums.ErrorEnum;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
    private int code;

    public TokenException(String message, int code) {
        super(message);
        this.code = code;
    }

    public TokenException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
    }
}
