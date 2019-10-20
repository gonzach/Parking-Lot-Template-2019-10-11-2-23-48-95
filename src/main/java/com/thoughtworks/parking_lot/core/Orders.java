package com.thoughtworks.parking_lot.core;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Orders {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private int orderNo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int plateNumber;

    @Column(nullable = false)
    private Date creationTime;

    @Column(nullable = false)
    private Date closeTime;

    @Column(nullable = false)
    private String orderStatus;

    public Orders() {
        setOrderStatus("open");
        setCreationTime(getCurrentDateTime());
    }

    private Date getCurrentDateTime() {
        return new Date();
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(int plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
