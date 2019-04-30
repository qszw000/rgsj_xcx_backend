package cn.hsy.echo.pojo;

import java.util.Date;

public class Complaint {
    private int id;
    private String name;
    private String content;
    private Date time;
    private String telephone;
    private String picture;
    private int status;

    public Complaint() {}

    public Complaint(int id, String name, String content, Date time, String telephone, String picture, int status) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.telephone = telephone;
        this.picture = picture;
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
