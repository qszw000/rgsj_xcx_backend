package cn.hsy.echo.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Questionnaire {
    private int id;

    @JsonIgnore
    private int zone;

    private String title;

    private Date startTime;

    private Date endTime;

    private String name;

    private List<Question> questions;
}
