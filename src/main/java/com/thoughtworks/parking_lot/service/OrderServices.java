package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.Car;
import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class OrderServices {

    private static final String NO_EXISTING_PARKINGLOT = "Invalid Parking lot name!";
    public static final String PLATENUMBER_IS_EXISTING = "Plate number already exist";
    public static final String NO_AVAILABLE_PARKING_SPACE = "No available parking space!";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Orders addOrder(String name, Car car) throws NotFoundException {
        ParkingLots parkingLot = parkingLotRepository.findOneByName(name);
        Orders plateNumber = orderRepository.findPlateNumber(car.getPlateNumber());
        if (parkingLot != null ){
            if(parkingLot.getCapacity() >= 0){
                if (plateNumber == null) {
                    Orders newOrders = new Orders();
                    newOrders.setName(name);
                    newOrders.setPlateNumber(car.getPlateNumber());
                    newOrders.setCreationTime(new Timestamp(new Date().getTime()));
                    newOrders.setCloseTime(null);
                    newOrders.setOrderStatus("Open");
                    orderRepository.save(newOrders);
                    parkingLot.setOrders(newOrders);
                    parkingLot.setCapacity(parkingLot.getCapacity()-1);
                    parkingLotRepository.save(parkingLot);
                    return newOrders;
                }
                throw new NotFoundException(PLATENUMBER_IS_EXISTING);
            }
            throw new NotFoundException(NO_AVAILABLE_PARKING_SPACE);
        }
        throw new NotFoundException(NO_EXISTING_PARKINGLOT);
    }

}
