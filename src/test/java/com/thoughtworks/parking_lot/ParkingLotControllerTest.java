package com.thoughtworks.parking_lot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.service.ParkingLotServices;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ParkingLotControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParkingLotServices parkingLotServices;

    @Test
    void should_add_parking() throws Exception {

        when(parkingLotServices.addParkingLot(any())).thenReturn(new ParkingLots());
        ResultActions result = mvc.perform(post("/parkingLots")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ParkingLots())));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(0)));
    }

    @Test
    void should_get_list_of_parkingLots() throws Exception {
        List<ParkingLots> parkingLotsList = new ArrayList<>();
        parkingLotsList.add(new ParkingLots());
        parkingLotsList.add(new ParkingLots());

        when(parkingLotServices.getListOfParkingLot(1, 15)).thenReturn(parkingLotsList);
        ResultActions result = mvc.perform(get("/parkingLots/all")
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void should_get_parking_lot_by_name_when_Found() throws Exception {
        ParkingLots parkingLot = new ParkingLots();
        parkingLot.setName("Tin");

        List<ParkingLots> parkingLotsList = new ArrayList<>();
        parkingLotsList.add(parkingLot);

        when(parkingLotServices.getParkingLotByName("Tin")).thenReturn(parkingLotsList);
        ResultActions result = mvc.perform(get("/parkingLots?name=Tin")
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }


}
