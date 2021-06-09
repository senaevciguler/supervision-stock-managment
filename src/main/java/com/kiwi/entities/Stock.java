package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(name = "name",nullable = false)
    private String name;

    @PositiveOrZero
    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne
    private Product product;

}
