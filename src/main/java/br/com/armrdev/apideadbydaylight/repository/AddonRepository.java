package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Addon;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddonRepository extends JpaRepository<Addon, UUID> {
    List<Addon> findByNameContainingIgnoreCase(String name);
    List<Addon> findByKillerId(UUID killerId);
    List<Addon> findByTargetItemIgnoreCase(String targetItem);
    List<Addon> findByRarity(Rarity rarity);
}
