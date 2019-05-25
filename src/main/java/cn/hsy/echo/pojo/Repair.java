package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Repair {
    private int id;

    private String name;

    private String content;

    private Date createTime;

    private Date updateTime;

    private List<Reply> replyList;

    private String telephone;

    private String picture;

    private int status;
}
