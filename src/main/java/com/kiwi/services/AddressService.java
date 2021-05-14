package com.kiwi.services;

import com.kiwi.entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Address save(Address address);

    List<Address> findAll();

    Optional<Address> update(Address address, Long id);

    Address findById(long id);

    void deleteById(long id);
}
