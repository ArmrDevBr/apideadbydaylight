package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.RealmDBD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RealmDBDRepository extends JpaRepository<RealmDBD, UUID> {
    List<RealmDBD> findByNameContainingIgnoreCase(String name);
    List<RealmDBD> findByDlcId(UUID dlcId);
}
