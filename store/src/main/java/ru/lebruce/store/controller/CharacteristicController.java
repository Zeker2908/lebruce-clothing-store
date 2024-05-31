package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Characteristic;
import ru.lebruce.store.service.CharacteristicService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characteristic")
@AllArgsConstructor
public class CharacteristicController {
    private final CharacteristicService service;

    @GetMapping
    public List<Characteristic> findAllCharacteristic() {
        return service.findAllCharacteristic();
    }

    @GetMapping("/{characteristicName}")
    public Characteristic findByCharacteristicName(@PathVariable String characteristicName) {
        return service.findByCharacteristicName(characteristicName);
    }

    @PostMapping
    public Characteristic saveCharacteristic(@RequestBody Characteristic characteristic) {
        return service.saveCharacteristic(characteristic);
    }

    @PutMapping
    public Characteristic updateCharacteristic(@RequestBody Characteristic characteristic) {
        return service.updateCharacteristic(characteristic);
    }

    @DeleteMapping("/{characteristicId}")
    public void deleteCharacteristic(@PathVariable Long characteristicId) {
        service.deleteCharacteristic(characteristicId);
    }

    @DeleteMapping("name/{characteristicName}")
    public void deleteCharacteristic(@PathVariable String characteristicName) {
        service.deleteCharacteristic(characteristicName);
    }


}
