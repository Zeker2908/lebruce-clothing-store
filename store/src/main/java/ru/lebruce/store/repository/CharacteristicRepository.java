package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import ru.lebruce.store.domain.model.Characteristic;
=======
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.Characteristic;
>>>>>>> main

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    void deleteByCharacteristicId(Long characteristicId);

    void deleteByCharacteristicName(String characteristicName);

    Characteristic findByCharacteristicName(String characteristicName);

}