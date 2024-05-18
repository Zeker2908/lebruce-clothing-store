package ru.lebruce.store.service;

import ru.lebruce.store.domain.model.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAllAddress();

    Address findByCity(String city);

    Address findByCountry(String country);

    Address findByZipCode(String zipCode);

    Address saveAddress(Address address);

    Address updateAddress(Address address);

    void deleteAddress(Long addressId);
}
