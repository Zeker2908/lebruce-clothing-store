package ru.lebruce.store.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Address;
import ru.lebruce.store.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@AllArgsConstructor
public class AddressController {
    private final AddressService service;


    @GetMapping
    public List<Address> findAllAddress() {
        return service.findAllAddress();
    }

    @GetMapping("/{city}")
    public Address findByAddressByCity(@PathVariable String city) {
        return service.findByCity(city);
    }

    @GetMapping("/{country}")
    public Address findByAddressByCountry(@PathVariable String country) {
        return service.findByCountry(country);
    }

    @GetMapping("/{zipCode}")
    public Address findByAddressByZipCode(@PathVariable String zipCode) {
        return service.findByZipCode(zipCode);
    }

    @PostMapping()
    public Address saveAddress(@RequestBody Address address) {
        return service.saveAddress(address);
    }

    @PutMapping()
    public Address updateAddress(@RequestBody Address address) {
        return service.updateAddress(address);
    }

    @DeleteMapping("{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        service.deleteAddress(addressId);
    }


}
