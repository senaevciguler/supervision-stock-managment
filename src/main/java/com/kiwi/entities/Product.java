package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 1, max = 200, message = "Name must be between 1 and 200 characters")
    @Column(name = "name")
    private String name;

    @NotNull
    @Positive
    @Column(name = "price")
    private BigDecimal price;

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
