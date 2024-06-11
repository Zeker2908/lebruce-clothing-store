package ru.lebruce.store.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Адрес")
public class AddressRequest {

    @Schema(description = "Страна", example = "Россия")
    @NotBlank(message = "Страна не может быть пустой")
    @Size(min = 1, max = 50, message = "Длина страны должна быть не более 50 символов")
    private String country;

    @Schema(description = "Регион/область/штат", example = "Московская область")
    @NotBlank(message = "Регион/область/штат не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина региона/области/штата должна быть не более 50 символов")
    private String state;

    @Schema(description = "Город", example = "Москва")
    @NotBlank(message = "Город не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина города должна быть не более 50 символов")
    private String city;

    @Schema(description = "Улица", example = "Ленина")
    @NotBlank(message = "Улица не может быть пустой")
    @Size(min = 1, max = 50, message = "Длина улицы должна быть не более 50 символов")
    private String street;

    @Schema(description = "Номер дома", example = "1")
    @NotBlank(message = "Номер дома не может быть пустым")
    @Size(min = 1, max = 10, message = "Длина номера дома должна быть не более 10 символов")
    private String houseNumber;

    @Schema(description = "Номер квартиры/офиса", example = "1")
    @NotBlank(message = "Номер квартиры/офиса не может быть пустым")
    @Size(min = 1, max = 10, message = "Длина номера квартиры/офиса должна быть не более 10 символов")
    private String apartmentNumber;

    @Schema(description = "Почтовый индекс", example = "123456")
    @NotBlank(message = "Почтовый индекс не может быть пустым")
    @Size(min = 1, max = 10, message = "Длина почтового индекса должна быть не более 10 символов")
    private String zipCode;
}
