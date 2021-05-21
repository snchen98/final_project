package com.capgemini.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.capgemini.entity.Address;
import com.capgemini.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Optional<Address> getAddressById(@RequestParam int id) {
        return addressService.getAddressById(id);
    }

    @PostMapping("/address")
    public Address addAddress(@RequestBody @Valid Address address) {
        return addressService.addAdress(address);
    }

    @PutMapping("/address/")
    public Address setAddressDetails(@RequestBody @Valid Address address) {
        return addressService.setAddressDetails(address);
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddressById(@RequestParam int id) {
        addressService.deleteAddressById(id);
    }



}
