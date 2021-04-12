package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="basket")
public class Basket {

    @Id
    private Long id;

    private Integer quantity;

    @OneToMany
    private List<Product> products;

    /*
    @OneToOne
    private User user;*/

}
