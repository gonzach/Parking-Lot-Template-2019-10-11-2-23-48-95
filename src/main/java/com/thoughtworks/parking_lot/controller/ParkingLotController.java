package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.service.ParkingLotServices;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    @Autowired
    ParkingLotServices parkingLotsServices;

    @PostMapping(produces = {"application/json"})
    public ParkingLots addParkingLot(@RequestBody ParkingLots parkingLots) throws NotFoundException {
        return parkingLotsServices.addParkingLot(parkingLots);
    }
}
