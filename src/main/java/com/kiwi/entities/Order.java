package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;


    /*
    @OneToMany(mappedBy = "order")
    private List<Product> products;*/

    @OneToOne
    private Basket basket;


}
