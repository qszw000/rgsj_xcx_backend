package cn.hsy.echo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Option {
    private int id;

    private String content;

    @JsonIgnore
    private int selectNumber;
}
