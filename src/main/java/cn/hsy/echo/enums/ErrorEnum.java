package cn.hsy.echo.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ErrorEnum {
    PARAM_ILLEGAL(-1001, "输入类型不正确"),

    CODE_EXPIRE(-1002, "无效Code"),

    TOKEN_NOT_EXIT(-1003, "Token未传入"),

    TOKEN_EXPIRE(-1004, "无效Token"),

    PAGE_NUM_ILLEGAL(-1005, "参数page不合法，page最小值为1"),

    PAGE_SIZE_ILLEGAL(-1006, "参数count不合法，count最小值为1"),

    STUDENT_ID_ILLEGAL(-1007, "参数studentID不合法, studentID为9位整数"),

    STUDENT_IS_BANNED(-1008, "学号已被绑定"),

    STUDENT_ID_NOT_EXIT(-1009, "学号不存在"),

    OPENID_IS_BANNED(-1010, "openId已被绑定"),
    ;

    private int code;

    private String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
