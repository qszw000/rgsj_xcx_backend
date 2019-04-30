package cn.hsy.echo.pojo;

import java.util.Date;

public class Fee {
    private int id;
    private float lastMonthQuantity;
    private float currentMonthQuantity;
    private Date startTime;
    private Date endTime;
    private float unitPrice;
    private float freeQuantity;
    private float amount;
    private int status;
    private int type;

    public Fee() {}

    public Fee(int id, float lastMonthQuantity, float currentMonthQuantity, Date startTime, Date endTime,
               float unitPrice, float freeQuantity, float amount, int status, int type) {
        this.id = id;
        this.lastMonthQuantity = lastMonthQuantity;
        this.currentMonthQuantity = currentMonthQuantity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.unitPrice = unitPrice;
        this.freeQuantity = freeQuantity;
        this.amount = amount;
        this.status = status;
        this.type = type;
    }



    public float getLastMonthQuantity() {
        return lastMonthQuantity;
    }

    public void setLastMonthQuantity(float lastMonthQuantity) {
        this.lastMonthQuantity = lastMonthQuantity;
    }

    public float getCurrentMonthQuantity() {
        return currentMonthQuantity;
    }

    public void setCurrentMonthQuantity(float currentMonthQuantity) {
        this.currentMonthQuantity = currentMonthQuantity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getFreeQuantity() {
        return freeQuantity;
    }

    public void setFreeQuantity(float freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
