package cn.hsy.echo.pojo;

import lombok.Data;

@Data
public class Result {
    private int code;

    private boolean success;

    private String errMsg;

    private Object data;
}
