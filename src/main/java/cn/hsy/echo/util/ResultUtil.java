package cn.hsy.echo.util;

import cn.hsy.echo.pojo.Result;

public class ResultUtil {

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setSuccess(true);
        result.setErrMsg("");
        result.setData(data);
        return result;
    }

    public static Result succcess() {
        return success(null);
    }

    public static Result error(int code, String errMsg) {
        Result result = new Result();
        result.setCode(code);
        result.setSuccess(false);
        result.setErrMsg(errMsg);
        result.setData(null);
        return result;
    }
}
