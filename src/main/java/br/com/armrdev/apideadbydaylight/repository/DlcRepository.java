package br.com.armrdev.apideadbydaylight.repository;

import br.com.armrdev.apideadbydaylight.entity.Dlc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DlcRepository extends JpaRepository<Dlc, UUID> {
    List<Dlc> findByNameContainingIgnoreCase(String name);
}
