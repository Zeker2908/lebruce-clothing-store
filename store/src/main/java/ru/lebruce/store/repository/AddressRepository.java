package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import ru.lebruce.store.domain.model.Address;
=======
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.Address;
>>>>>>> main

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByCity(String city);
    Address findByCountry(String country);
    Address findByZipCode(String zipCode);
    void deleteByZipCode(String zipCode);
    void deleteByCity(String city);
    void deleteByCountry(String country);
    void deleteByAddressId(Long addressId);
}
