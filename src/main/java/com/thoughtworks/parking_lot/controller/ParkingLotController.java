package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.service.OrderServices;
import com.thoughtworks.parking_lot.service.ParkingLotServices;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    @Autowired
    ParkingLotServices parkingLotsServices;

    @Autowired
    OrderServices orderServices;

    @PostMapping(produces = {"application/json"})
    public ParkingLots addParkingLot(@RequestBody ParkingLots parkingLots) throws NotFoundException {
        return parkingLotsServices.addParkingLot(parkingLots);
    }

    @GetMapping(value = "/all", produces = {"application/json"})
    public Iterable<ParkingLots> getListOfParkingLot(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "15") Integer pageSize) {
        return parkingLotsServices.getListOfParkingLot(page, pageSize);
    }

    @GetMapping(produces = {"application/json"})
    public List<ParkingLots> getParkingLotByName(@RequestParam(required = false) String name) throws NotFoundException {
        return parkingLotsServices.getParkingLotByName(name);
    }

    @DeleteMapping(value = "/{name}", produces = {"application/json"})
    public String deleteParkingLotByName(@PathVariable String name) throws NotFoundException {
        return parkingLotsServices.deleteParkingLotByName(name);
    }

    @PatchMapping(value = "/{name}", produces = {"application/json"})
    public ParkingLots modifyCapacity(@PathVariable String name, @RequestBody ParkingLots parkingLots) throws NotFoundException {
        return parkingLotsServices.modifyCapacity(name, parkingLots);
    }

    @PostMapping(value = "/{name}/orders", produces = {"application/json"})
    public Orders createOrder(@PathVariable String name, @RequestBody Orders orders) throws NotFoundException {
        return orderServices.addOrder(name, orders);
    }

}
