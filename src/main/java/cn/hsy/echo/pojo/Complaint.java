package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Complaint {
    private int id;

    private String name;

    private String content;

    private Date createTime;

    private Date updateTime;

    private String telephone;

    private String picture;

    private List<Reply> replyList;

    private int status;
}
