package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLots, Long> {
    @Query("Select p from ParkingLots p where p.name = :name")
    ParkingLots findOneByName(@Param("name") String name);

    @Query("Select p from ParkingLots p where p.name LIKE %:name%")
    List<ParkingLots> findByFirstNameLike(@Param("name") String name);

    @Query("Delete from ParkingLots p where p.orderNo = :orderNo")
    List<ParkingLots> deleteOrder(@Param("orderNo") Integer orderNo);

}
