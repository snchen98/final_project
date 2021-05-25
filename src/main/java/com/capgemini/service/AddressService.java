package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Address;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressServiceInterface {

    @Autowired
    AddressRepository addressRepository;

    public Address addAddress(Address address) {
        if (addressRepository.findById(address.getId()).isEmpty()) {
            return addressRepository.save(address);
        }
        throw new ResourceAlreadyExistsException("Address with id: " + address.getId() + " already exists");
    }
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }
    public Address getAddressById(int id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return address.get();
        }
        throw new ResourceNotFoundException("Can't find address with id: " + id);

    }
    public Address setAddressDetails(Address address) {
        if (addressRepository.findById(address.getId()).isPresent()) {
            return addressRepository.save(address);
        }
        throw new ResourceNotFoundException("Can't find address with id: " + address.getId());
    }
    public void deleteAddressById(int id) {
        if (addressRepository.findById(id).isPresent()) {
            addressRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("Can't find address with id: " + id);
    }
}
