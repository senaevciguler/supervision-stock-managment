package com.kiwi.services.implementation;

import com.kiwi.entities.Address;
import com.kiwi.repositories.AddressRepository;
import com.kiwi.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> update(Address address, Long id) {
        return addressRepository.findById(id).map(addressUp -> {
            addressUp.setAddressLine(address.getAddressLine());
            addressUp.setCity(address.getCity());
            addressUp.setCountry(address.getCountry());
            addressUp.setPhone(address.getPhone());
            addressUp.setPostalCode(address.getPostalCode());
            return addressRepository.save(addressUp);
        });
    }

    @Override
    public Address findById(long id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public void deleteById(long id) {
        addressRepository.deleteById(id);
    }
}

