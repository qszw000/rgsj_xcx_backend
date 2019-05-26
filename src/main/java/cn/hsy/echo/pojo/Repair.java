package cn.hsy.echo.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Repair {
    private int id;

    @JsonIgnore
    private int dId;

    private String name;

    private String content;

    @JSONField(name = "createTime")
    private Date time;

    private Date updateTime;

    private List<Reply> replyList;

    private String telephone;

    private String picture;

    private int status;
}
