package com.kiwi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "order")
public class Order {

    @Id
    private Long id;

    private LocalDate date;


    /*
    @OneToMany(mappedBy = "order")
    private List<Product> products;*/

    @OneToOne
    private Basket basket;


}
