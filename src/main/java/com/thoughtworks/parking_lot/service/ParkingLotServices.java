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
    private static final String PARKING_LOT_WAS_DELETED = "Parking lot was deleted!";
    private static final String NO_PARKING_LOT_WAS_DELETED = "No Parking lot was deleted!";

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
            if (parkingLotRepository.findAll().size() == 0) {
                throw new NotFoundException(NO_PARKING_LOT_WAS_FOUND);
            } else
                return parkingLotRepository.findAll();
        } else if (parkingLotRepository.findByFirstNameLike(name).isEmpty()) {
            throw new NotFoundException(NO_PARKING_LOT_WAS_FOUND);
        }

        return parkingLotRepository.findByFirstNameLike(name);
    }

    public String deleteParkingLotByName(String name) throws NotFoundException {
        ParkingLots parkingLot = parkingLotRepository.findOneByName(name);
        if (parkingLot != null) {
            parkingLotRepository.delete(parkingLot);
            return PARKING_LOT_WAS_DELETED;
        }
        throw new NotFoundException(NO_PARKING_LOT_WAS_DELETED);
    }

    public ParkingLots modifyCapacity(String name, ParkingLots parkingLots) throws NotFoundException {
        ParkingLots parkingLot = parkingLotRepository.findOneByName(name);
        if (parkingLot != null) {
            parkingLot.setCapacity(parkingLots.getCapacity());
            parkingLotRepository.save(parkingLot);
            return parkingLot;
        }
        throw new NotFoundException(NO_PARKING_LOT_WAS_FOUND);
    }
}