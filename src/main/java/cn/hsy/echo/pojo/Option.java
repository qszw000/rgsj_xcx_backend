package cn.hsy.echo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Option {
    private int id;
    private String content;
    private int selectNumber;

    public Option() {}

    public Option(int id, String content, int selectNumber) {
        this.id = id;
        this.content = content;
        this.selectNumber = selectNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public int getSelectNumber() {
        return selectNumber;
    }

    public void setSelectNumber(int selectNumber) {
        this.selectNumber = selectNumber;
    }
}
