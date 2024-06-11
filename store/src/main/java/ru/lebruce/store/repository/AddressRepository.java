package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Address;
import ru.lebruce.store.domain.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUser(User user);

    void deleteByAddressId(Long addressId);
}
