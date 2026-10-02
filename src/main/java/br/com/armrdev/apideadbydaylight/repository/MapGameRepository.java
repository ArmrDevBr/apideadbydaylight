package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.MapGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MapGameRepository extends JpaRepository<MapGame, UUID> {
    List<MapGame> findByTitleContainingIgnoreCase(String title);
    List<MapGame> findByRealmId(UUID realmId);
    List<MapGame> findByDlcId(UUID dlcId);
}
