package com.thoughtworks.parking_lot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.controller.ParkingLotController;
import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.service.OrderServices;
import com.thoughtworks.parking_lot.service.ParkingLotServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class OrdersControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderServices orderServices;

    @MockBean
    private ParkingLotServices parkingLotServices;

    @Test
    void should_add_order() throws Exception {
        ParkingLots parkinglot = new ParkingLots();
        parkinglot.setCapacity(10);
        parkinglot.setName("ParkingLot-1");
        parkinglot.setLocation("MOA UPPER DECK");

        when(orderServices.addOrder(anyString(), any())).thenReturn(new Orders());
        ResultActions result = mvc.perform(post("/parkingLots/{name}/orders", "ParkingLot-1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Orders())));

        result.andExpect(status().isOk());
    }

}
