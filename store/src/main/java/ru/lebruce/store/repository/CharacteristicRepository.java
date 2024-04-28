package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.model.Characteristic;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    void deleteByCharacteristicId(Long characteristicId);

    void deleteByCharacteristicName(String characteristicName);

    Characteristic findByCharacteristicName(String characteristicName);

}