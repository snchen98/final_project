package com.capgemini.service;

import java.util.List;

import com.capgemini.entity.Address;

public interface AddressServiceInterface {
    public Address addAddress(Address address);
    public List<Address> getAllAddress();
    public Address getAddressById(int id);
    public Address setAddressDetails(Address address);
    public void deleteAddressById(int id);
}
