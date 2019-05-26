package cn.hsy.echo.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Complaint {
    private int id;

    @JsonIgnore
    private int sId;

    private String name;

    private String content;

    @JSONField(name = "createTime")
    private Date time;

    private Date updateTime;

    private String telephone;

    private String picture;

    private List<Reply> replyList;

    private int status;
}
