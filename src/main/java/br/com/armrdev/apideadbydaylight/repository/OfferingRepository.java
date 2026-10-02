package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Offering;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, UUID> {
    List<Offering> findByNameContainingIgnoreCase(String name);
    List<Offering> findByRole(Role role);
    List<Offering> findByRarity(Rarity rarity);
    List<Offering> findBySecret(Boolean secret);
    List<Offering> findByDlcId(UUID dlcId);
}
