package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Complaint {
    private int id;

    private String name;

    private String content;

    private Date time;

    private String telephone;

    private String picture;

    private int status;
}
