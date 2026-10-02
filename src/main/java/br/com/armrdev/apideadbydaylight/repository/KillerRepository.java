package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Killer;
import br.com.armrdev.apideadbydaylight.entity.enums.Difficult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KillerRepository extends JpaRepository<Killer, UUID> {
    List<Killer> findByNameContainingIgnoreCase(String name);
    List<Killer> findByTitleContainingIgnoreCase(String title);
    List<Killer> findByLicensed(Boolean licensed);
    List<Killer> findByDifficultPlay(Difficult difficultPlay);
    List<Killer> findByDlcId(UUID dlcId);
}
