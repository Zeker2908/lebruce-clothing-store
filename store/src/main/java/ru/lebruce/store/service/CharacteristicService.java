package ru.lebruce.store.service;

import ru.lebruce.store.domain.dto.CharacteristicRequest;
import ru.lebruce.store.domain.model.Characteristic;

public interface CharacteristicService {
    Characteristic create(CharacteristicRequest characteristic);

    Characteristic update(Characteristic characteristic);

    void deleteCharacteristic(Long characteristicId);

}
