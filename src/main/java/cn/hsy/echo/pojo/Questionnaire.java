package cn.hsy.echo.pojo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Questionnaire {
    private int id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String name;

    private List<Question> questions;
}
