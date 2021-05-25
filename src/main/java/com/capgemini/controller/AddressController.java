package com.capgemini.controller;

import java.util.List;

import javax.validation.Valid;

import com.capgemini.entity.Address;
import com.capgemini.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/address")
    public List<Address> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/address/{id}")
    public Address getAddressById(@PathVariable int id) {
        return addressService.getAddressById(id);
    }

    @PostMapping("/address")
    public Address addAddress(@RequestBody @Valid Address address) {
        return addressService.addAddress(address);
    }

    @PutMapping("/address")
    public Address setAddressDetails(@RequestBody @Valid Address address) {
        return addressService.setAddressDetails(address);
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddressById(@PathVariable int id) {
        addressService.deleteAddressById(id);
    }



}
