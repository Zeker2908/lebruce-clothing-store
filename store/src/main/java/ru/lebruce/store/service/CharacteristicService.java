package ru.lebruce.store.service;

import ru.lebruce.store.domain.dto.CharacteristicRequest;
import ru.lebruce.store.domain.model.Characteristic;

import java.util.List;

public interface CharacteristicService {
    List<Characteristic> findAllCharacteristic();

    Characteristic create(CharacteristicRequest characteristic);

    Characteristic update(Characteristic characteristic);

    void deleteCharacteristic(Long characteristicId);

}
