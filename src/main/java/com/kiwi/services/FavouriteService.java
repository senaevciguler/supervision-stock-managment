package com.kiwi.services;

import com.kiwi.entities.Favourite;

import java.util.List;
import java.util.Optional;

public interface FavouriteService {

    Favourite save(Favourite favourite);

    List<Favourite> findAll();

    Favourite findById(long id);

    Optional<Favourite> update(Favourite favourite, long id);

    void delete(long id);
}
