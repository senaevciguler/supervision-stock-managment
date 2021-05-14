package com.kiwi.repositories;

import com.kiwi.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT t FROM Store t WHERE t.name = ?1")
    Store findByStoreName(String storeName);
}
