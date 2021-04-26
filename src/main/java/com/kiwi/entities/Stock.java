package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long quantity;

    @ManyToMany
    @JoinTable(
            name = "stock_product",
            joinColumns = @JoinColumn(
                    name = "stock_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"))
    private Collection<Product> products;


}
