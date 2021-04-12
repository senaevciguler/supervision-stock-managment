package com.kiwi.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
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
@Entity
@Table(name="user")
public class User {

    @Id
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
