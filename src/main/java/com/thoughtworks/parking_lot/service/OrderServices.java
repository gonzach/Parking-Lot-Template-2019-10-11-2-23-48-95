package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServices {

    private static final String ORDER_ALREADY_MADE = "Order already made!";
    private static final String NO_EXISTING_PARKINGLOT = "Invalid Parking lot name!";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Orders addOrder(String name, Orders orders) throws NotFoundException {
        ParkingLots parkingLot = parkingLotRepository.findOneByName(name);
        Orders order = orderRepository.findOneByOrderNo(orders.getOrderNo());
        if (parkingLot != null ){
            if (order == null) {
                orders.setCreationTime(new Date());
                orders.setOrderStatus("open");
                orderRepository.save(orders);
                return orders;
            }
            throw new NotFoundException(ORDER_ALREADY_MADE);
        }
        throw new NotFoundException(NO_EXISTING_PARKINGLOT);
    }

}
