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

    private static final String NO_EXISTING_PARKING_LOT = "Invalid Parking lot name!";
    public static final String PLATE_NUMBER_IS_EXISTING = "Plate number already exist";
    public static final String NO_AVAILABLE_PARKING_SPACE = "The parking lot is full!";
    public static final String PLATE_NUMBER_IS_NOT_EXISTING = "Plate number is not existing!";

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
                throw new NotFoundException(PLATE_NUMBER_IS_EXISTING);
            }
            throw new NotFoundException(NO_AVAILABLE_PARKING_SPACE);
        }
        throw new NotFoundException(NO_EXISTING_PARKING_LOT);
    }

    public Orders deleteOrder(String name, String plateNumber) throws NotFoundException {
        ParkingLots parkingLot = parkingLotRepository.findOneByName(name);
        Orders checkPlateNumber = orderRepository.findPlateNumber(plateNumber);
        if (parkingLot != null ){
                if (checkPlateNumber != null) {
                    Orders oldOrder = new Orders();
                    oldOrder.setName(name);
                    oldOrder.setPlateNumber(plateNumber);
                    oldOrder.setCreationTime(oldOrder.getCreationTime());
                    oldOrder.setCloseTime(new Timestamp(new Date().getTime()));
                    oldOrder.setOrderStatus("Close");
                    orderRepository.save(oldOrder);
                    parkingLot.setOrders(oldOrder);
                    parkingLot.setCapacity(parkingLot.getCapacity()+1);
                    parkingLotRepository.save(parkingLot);
                    parkingLotRepository.deleteOrder(oldOrder.getOrderNo());
                    return oldOrder;
                }
                throw new NotFoundException(PLATE_NUMBER_IS_NOT_EXISTING);
        }
        throw new NotFoundException(NO_EXISTING_PARKING_LOT);
    }
}
