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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private Address address;

    /*
    @ManyToMany
    @JoinTable(
            name = "store_stock",
            joinColumns = @JoinColumn(
                    name = "store_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "stock_id", referencedColumnName = "id"))
    private Collection<Stock> stocks;*/


}
