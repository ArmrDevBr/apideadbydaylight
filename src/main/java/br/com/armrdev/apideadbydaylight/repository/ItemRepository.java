package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Item;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByItemType(ItemType itemType);
    List<Item> findByRarity(Rarity rarity);
}
