package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String addressLine;

    private String city;

    private String country;

    private Integer postalCode;

    private String phone;

}
