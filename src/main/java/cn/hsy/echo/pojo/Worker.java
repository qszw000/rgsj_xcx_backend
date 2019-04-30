package cn.hsy.echo.pojo;

public class Worker {
    private String name;
    private int workerId;
    private String telephone;
    private int type;

    public Worker() {}

    public Worker(String name, int workerId, String telephone, int type) {
        this.name = name;
        this.workerId = workerId;
        this.telephone = telephone;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
