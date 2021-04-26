package com.kiwi.services.implementation;

import com.kiwi.entities.Favourite;
import com.kiwi.repositories.FavouriteRepository;
import com.kiwi.services.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    FavouriteRepository favouriteRepository;

    @Override
    public Favourite save(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }

    @Override
    public List<Favourite> findAll() {
        return favouriteRepository.findAll();
    }

    @Override
    public Favourite findById(long id) {
        return favouriteRepository.findById(id).get();
    }

    @Override
    public Optional<Favourite> update(Favourite favourite, long id) {
        return favouriteRepository.findById(id).map(favouriteUpdated -> {
            favouriteUpdated.setProduct(favourite.getProduct());

            return favouriteRepository.save(favouriteUpdated);
        });
    }

    @Override
    public void delete(long id) {
        favouriteRepository.deleteById(id);
    }
}
