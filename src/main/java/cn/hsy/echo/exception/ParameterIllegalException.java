package cn.hsy.echo.exception;

public class ParameterIllegalException extends RuntimeException {
    private String errMsg;

    public ParameterIllegalException(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
