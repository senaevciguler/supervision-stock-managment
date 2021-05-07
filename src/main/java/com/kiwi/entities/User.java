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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean enabled;
    private boolean tokenExpired;

    @OneToOne
    private Address address;

    @OneToMany
    @JoinColumn(name = "orders_id")
    private List<Order> orders;

    @ManyToOne
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_favourite",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "favourite_id", referencedColumnName = "id"))
    private Collection<Favourite> favourites;
}
