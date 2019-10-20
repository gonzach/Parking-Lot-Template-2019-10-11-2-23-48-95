package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.core.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Orders, Long> {
    @Query("Select o from Orders o where o.orderNo = :orderNo")
    Orders findOneByOrderNo(@Param("order") Integer order);

    @Query("Select o from Orders o where o.plateNumber= :plateNumber")
    Orders findPlateNumber(@Param("plateNumber") String plateNumber);
}
