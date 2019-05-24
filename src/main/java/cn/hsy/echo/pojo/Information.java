package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Information {
    private int id;

    private String title;

    private String content;

    private Date time;

    private String name;
}
