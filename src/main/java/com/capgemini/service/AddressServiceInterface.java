package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Address;

public interface AddressServiceInterface {
    public Address addAdress(Address address);
    public List<Address> getAllAddress();
    public Optional<Address> getAddressById(int id);
    public Address setAddressDetails(Address address);
    public void deleteAddressById(int id);
}
