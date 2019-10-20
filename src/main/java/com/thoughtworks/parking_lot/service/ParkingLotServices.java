package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotServices {

    public static final String PARKING_LOT_ALREADY_EXISTING = "Parking lot already existing!";
    private static final String NO_PARKING_LOT_WAS_FOUND = "No Parking lot found!";

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

    public Iterable<ParkingLots> getListOfParkingLot(Integer page, Integer pageSize) {
        return parkingLotRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public List<ParkingLots> getParkingLotByName(String name) throws NotFoundException {
        if (name == null) {
            if (parkingLotRepository.findAll().size() == 0)
            {
                throw new NotFoundException(NO_PARKING_LOT_WAS_FOUND);
            }
            else
                return parkingLotRepository.findAll();
        }

        else if(parkingLotRepository.findByFirstNameLike(name).isEmpty()) {
            throw new NotFoundException(NO_PARKING_LOT_WAS_FOUND);
        }

        return parkingLotRepository.findByFirstNameLike(name);
    }

}