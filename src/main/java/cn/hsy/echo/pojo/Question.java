package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private int id;

    private String content;

    private int type;

    private List<Option> options;
}
