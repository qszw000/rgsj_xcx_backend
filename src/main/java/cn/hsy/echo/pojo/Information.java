package cn.hsy.echo.pojo;

import java.util.Date;

public class Information {
    private int id;
    private String title;
    private String content;
    private Date time;
    private String name;

    public Information() {}

    public Information(int id, String title, String content, Date time, String name) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
