package ru.lebruce.store.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.dto.CharacteristicRequest;
import ru.lebruce.store.domain.model.Characteristic;
import ru.lebruce.store.exception.CharacteristicAlreadyExistsException;
import ru.lebruce.store.repository.CharacteristicRepository;
import ru.lebruce.store.service.CharacteristicService;
import ru.lebruce.store.service.ProductService;

@Service
@AllArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {
    private final CharacteristicRepository repository;
    private final ProductService productService;

    @Override
    public Characteristic create(CharacteristicRequest characteristic) {
        if (repository.existsByProduct_ProductIdAndCharacteristicName(characteristic.getProductId(), characteristic.getCharacteristicName())) {
            throw new CharacteristicAlreadyExistsException("Характеристика уже существует");
        }
        return repository.save(Characteristic.builder()
                .product(productService.getByProductId(characteristic.getProductId()))
                .characteristicName(characteristic.getCharacteristicName())
                .characteristicValue(characteristic.getCharacteristicValue())
                .build());
    }

    @Override
    public Characteristic update(Characteristic characteristic) {
        return repository.save(characteristic);
    }

    @Override
    @Transactional
    public void deleteCharacteristic(Long characteristicId) {
        repository.deleteByCharacteristicId(characteristicId);
    }

}
