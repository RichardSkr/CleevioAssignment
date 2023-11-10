package com.richardSkrivanek.cleevio.repository;

import com.richardSkrivanek.cleevio.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    InventoryItem findByName(String name);
}
