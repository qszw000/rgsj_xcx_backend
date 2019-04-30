package cn.hsy.echo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {
    private int id;
    private String name;
    private int studentId;
    private String telephone;
    private int isLeader;
    private Dormitory dormitory;

    public Student() {}

    public Student(int id, String name, int studentId, String telephone, int isLeader, Dormitory dormitory) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.telephone = telephone;
        this.isLeader = isLeader;
        this.dormitory = dormitory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @JsonIgnore
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
