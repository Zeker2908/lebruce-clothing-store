package ru.lebruce.store.service;

import ru.lebruce.store.domain.dto.AddressRequest;
import ru.lebruce.store.domain.model.Address;

import java.util.List;

public interface AddressService {

    Address getAddresses();

    List<Address> findAll();

    Address saveAddress(Address address);

    Address createAddress(AddressRequest addressRequest);

    Address updateAddress(Address address);

    void deleteAddress(Long addressId);
}
