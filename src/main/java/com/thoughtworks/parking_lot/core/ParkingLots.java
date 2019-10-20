package com.thoughtworks.parking_lot.core;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
public
class ParkingLots {

    @Id
    private String name;

    @Min(1)
    private int capacity;

    private String location;

    @OneToOne
    private Orders orders;

    public ParkingLots() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
