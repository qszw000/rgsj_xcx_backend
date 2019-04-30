package cn.hsy.echo.pojo;

import java.util.List;

public class Question {
    private int id;
    private String content;
    private int type;
    private List<Option> options;

    public Question() {}

    public Question(int id, String content, int type, List<Option> options) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.options = options;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
