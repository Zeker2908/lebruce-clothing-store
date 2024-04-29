package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.Characteristic;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    void deleteByCharacteristicId(Long characteristicId);

    void deleteByCharacteristicName(String characteristicName);

    Characteristic findByCharacteristicName(String characteristicName);

}