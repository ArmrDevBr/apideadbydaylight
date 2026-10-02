package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, UUID> {
    List<Survivor> findByNameContainingIgnoreCase(String name);
    List<Survivor> findByLicensed(Boolean licensed);
    List<Survivor> findByDlcId(UUID dlcId);
}
