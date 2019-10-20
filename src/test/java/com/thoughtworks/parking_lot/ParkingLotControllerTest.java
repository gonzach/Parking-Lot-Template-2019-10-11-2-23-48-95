package com.thoughtworks.parking_lot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.core.ParkingLots;
import com.thoughtworks.parking_lot.service.ParkingLotServices;
import javassist.NotFoundException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void should_throw_parking_lot_not_found_when_no_parking_lot_get() throws Exception {

        when(parkingLotServices.getParkingLotByName(anyString())).thenThrow(new NotFoundException("No Parkinglot was found!"));
        ResultActions result = mvc.perform(get("/parkingLots?name=Tin"));
        result.andExpect(status().isNotFound());
    }

    @Test
    void should_delete_parkingLot_by_name_when_Found() throws Exception {

        when(parkingLotServices.deleteParkingLotByName("Tin")).thenReturn("Company was deleted!");
        ResultActions result = mvc.perform(delete("/parkingLots/Tin"));
        result.andExpect(status().isOk());
    }

    @Test
    void should_not_delete_parkingLot_by_name_when_no_Found() throws Exception {

        when(parkingLotServices.deleteParkingLotByName("Tubs")).thenReturn("No Company was deleted!");
        ResultActions result = mvc.perform(delete("/parkingLots/Tin"));
        result.andExpect(status().isOk());
    }

    @Test
    void should_modify_parkingLot() throws Exception {

        ParkingLots modifyCapacity = new ParkingLots();
        modifyCapacity.setCapacity(10);

        ParkingLots modifyParkinglot = new ParkingLots();
        modifyCapacity.setCapacity(10);
        modifyCapacity.setName("Tin");
        modifyCapacity.setLocation("MOA UPPER DECK");

        when(parkingLotServices.modifyCapacity(anyString(), any())).thenReturn(modifyParkinglot);
        ResultActions result = mvc.perform(patch("/parkingLots/Tin")
                .content(objectMapper.writeValueAsString(modifyCapacity))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void should_not_modify_parkingLot() throws Exception {

        ParkingLots modifyCapacity = new ParkingLots();
        modifyCapacity.setCapacity(10);

        when(parkingLotServices.modifyCapacity(anyString(), any())).thenThrow(new NotFoundException("No Parking lot found"));
        ResultActions result = mvc.perform(patch("/parkingLots/Tin")
                .content(objectMapper.writeValueAsString(modifyCapacity))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

}
