package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Perk;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PerkRepository extends JpaRepository<Perk, UUID> {
    List<Perk> findByNameContainingIgnoreCase(String name);
    List<Perk> findByRole(Role role);
    List<Perk> findBySurvivorId(UUID survivorId);
    List<Perk> findByKillerId(UUID killerId);
    List<Perk> findByRoleAndSurvivorIsNull(Role role);
    List<Perk> findByRoleAndKillerIsNull(Role role);
}
