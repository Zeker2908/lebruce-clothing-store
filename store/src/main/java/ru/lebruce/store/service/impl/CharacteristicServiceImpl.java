package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.model.Characteristic;
import ru.lebruce.store.repository.CharacteristicRepository;
import ru.lebruce.store.service.CharacteristicService;

import java.util.List;

@Service
@AllArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {
    private final CharacteristicRepository repository;

    @Override
    public List<Characteristic> findAllCharacteristic() {
        return repository.findAll();
    }

    @Override
    public Characteristic saveCharacteristic(Characteristic characteristic) {
        return repository.save(characteristic);
    }

    @Override
    public Characteristic updateCharacteristic(Characteristic characteristic) {
        return repository.save(characteristic);
    }

    @Override
    public Characteristic findByCharacteristicName(String characteristic) {
        return repository.findByCharacteristicName(characteristic);
    }

    @Override
    @Transactional
    public void deleteCharacteristic(Long characteristicId) {
        repository.deleteByCharacteristicId(characteristicId);
    }

    @Override
    @Transactional
    public void deleteCharacteristic(String characteristicName) {
        repository.deleteByCharacteristicName(characteristicName);
    }
}
