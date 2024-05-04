package ru.lebruce.store.service;

import ru.lebruce.store.domain.model.Characteristic;

import java.util.List;

public interface CharacteristicService {
    List<Characteristic> findAllCharacteristic();

    Characteristic saveCharacteristic(Characteristic characteristic);

    Characteristic updateCharacteristic(Characteristic characteristic);

    Characteristic findByCharacteristicName(String characteristic);

    void deleteCharacteristic(Long characteristicId);
    void deleteCharacteristic(String characteristicName);






}
