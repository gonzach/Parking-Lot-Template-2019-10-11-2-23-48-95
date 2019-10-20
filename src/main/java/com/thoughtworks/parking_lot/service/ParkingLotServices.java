package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServices {

    public static final String PARKING_LOT_ALREADY_EXISTING = "Parking lot already existing!";

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLots addParkingLot(ParkingLots parkingLots) throws NotFoundException {
        ParkingLots parkingLot = parkingLotRepository.findOneByName(parkingLots.getName());
        if (parkingLot == null) {
            parkingLotRepository.save(parkingLots);
            return parkingLots;
        }
        throw new NotFoundException(PARKING_LOT_ALREADY_EXISTING);
    }
}