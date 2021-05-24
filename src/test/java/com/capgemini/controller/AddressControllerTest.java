package com.capgemini.controller;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.entity.Address;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.service.AddressService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(value = AddressController.class)
public class AddressControllerTest {
    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressService addressService;

    @Test
    public void getAllAddressSuccess() throws Exception {
        int id = 123;
        String street = "main street";
        String city = "nyc";
        int zipcode = 12345;
        String state = "ny";
        String country = "us";
        Address address = new Address(id, street, city, zipcode, state, country);
        List<Address> addressL = new ArrayList<Address>();
        addressL.add(address);

        Mockito.when(addressService.getAllAddress()).thenReturn(addressL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/address").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = String.format("[{id:%d,street:\"%s\",city:\"%s\",zipcode:%d,state:\"%s\",country:\"%s\"}]", id, street, city, zipcode, state, country);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAddressByIdSuccess() throws Exception {
        int id = 123;
        String street = "main street";
        String city = "nyc";
        int zipcode = 12345;
        String state = "ny";
        String country = "us";
        Address address = new Address(id, street, city, zipcode, state, country);


        Mockito.when(addressService.getAddressById(id)).thenReturn(address);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/address/"+id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = String.format("{id:%d,street:\"%s\",city:\"%s\",zipcode:%d,state:\"%s\",country:\"%s\"}", id, street, city, zipcode, state, country);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAddressByIdFail() throws Exception {
        int id = 123;
        Mockito.when(addressService.getAddressById(id)).thenThrow(new ResourceNotFoundException("Can't find address with id: " + id));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/address/"+id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException);
        Assertions.assertEquals("Can't find address with id: " + id, (result.getResolvedException().getMessage()));
    }
}
