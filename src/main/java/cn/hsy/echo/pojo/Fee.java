package cn.hsy.echo.pojo;

import lombok.Data;

import java.util.Date;

@Data
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
}
