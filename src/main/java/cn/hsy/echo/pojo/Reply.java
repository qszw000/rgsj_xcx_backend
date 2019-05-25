package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Reply {
    private String name = "管理员";

    private String content;

    private Date replyTime;

    private int replyType;
}
