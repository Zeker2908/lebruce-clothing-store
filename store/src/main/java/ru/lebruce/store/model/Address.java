package ru.lebruce.store.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String country;
    private String state;
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String zipCode;
}
