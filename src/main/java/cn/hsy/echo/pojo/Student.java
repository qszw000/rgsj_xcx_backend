package cn.hsy.echo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Student {

    @JsonIgnore
    private int id;

    private String name;

    private int studentId;

    @JsonIgnore
    private String telephone;

    private int isLeader;

    private Dormitory dormitory;
}
