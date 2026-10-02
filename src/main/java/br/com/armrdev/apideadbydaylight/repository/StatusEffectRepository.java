package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.StatusEffect;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.entity.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusEffectRepository extends JpaRepository<StatusEffect, UUID> {
    Optional<StatusEffect> findByNameIgnoreCase(String name);
    List<StatusEffect> findByType(StatusType type);
    List<StatusEffect> findByAffectedRole(Role affectedRole);
}
