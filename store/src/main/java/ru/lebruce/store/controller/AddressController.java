package ru.lebruce.store.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.AddressRequest;
import ru.lebruce.store.domain.model.Address;
import ru.lebruce.store.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@AllArgsConstructor
public class AddressController {
    private final AddressService service;


    @GetMapping
    public Address getAddress() {
        return service.getAddresses();
    }

    @GetMapping("/all")
    public List<Address> getAllAddress() {
        return service.findAll();
    }


    @PostMapping()
    public Address saveAddress(@RequestBody AddressRequest addressRequest) {
        return service.createAddress(addressRequest);
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
