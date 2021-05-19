package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;


    @ManyToMany
    @JoinTable(
            name = "orders_product",
            joinColumns = @JoinColumn(
                    name = "orders_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"))
    private Collection<Product> products;

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToMany
    @JoinTable(
            name = "orders_store",
            joinColumns = @JoinColumn(
                    name = "orders_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "store_id", referencedColumnName = "id"))
    private Collection<Store> stores;

}
