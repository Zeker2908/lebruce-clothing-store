package ru.lebruce.store.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.Address;
import ru.lebruce.store.repository.AddressRepository;
import ru.lebruce.store.service.AddressService;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

    @Override
    public List<Address> findAllAddress() {
        return repository.findAll();
    }

    @Override
    public Address findByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public Address findByCountry(String country) {
        return repository.findByCountry(country);
    }

    @Override
    public Address findByZipCode(String zipCode) {
        return repository.findByZipCode(zipCode);
    }

    @Override
    public Address saveAddress(Address address) {
        return repository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return repository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        repository.deleteByAddressId(addressId);
    }
    
}
