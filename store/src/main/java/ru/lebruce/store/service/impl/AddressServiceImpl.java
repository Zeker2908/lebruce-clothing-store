package ru.lebruce.store.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.AddressRequest;
import ru.lebruce.store.domain.model.Address;
import ru.lebruce.store.repository.AddressRepository;
import ru.lebruce.store.service.AddressService;
import ru.lebruce.store.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;
    private final UserService userService;

    @Override
    public Address getAddresses() {
        return repository.findByUser(userService.getCurrentUser());
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }


    @Override
    public Address saveAddress(Address address) {
        return repository.save(address);
    }

    @Override
    public Address createAddress(AddressRequest addressRequest) {
        Address address = Address.builder()
                .user(userService.getCurrentUser())
                .country(addressRequest.getCountry())
                .state(addressRequest.getState())
                .city(addressRequest.getCity())
                .street(addressRequest.getStreet())
                .houseNumber(addressRequest.getHouseNumber())
                .apartmentNumber(addressRequest.getApartmentNumber())
                .zipCode(addressRequest.getZipCode())
                .build();
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
