package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double price;

    @OneToOne
    private Measurement measurement;

    @OneToMany
    private List<Ingredient> ingredients;

    @OneToOne
    private Category category;

    @ManyToOne
    private Basket basket;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

}
