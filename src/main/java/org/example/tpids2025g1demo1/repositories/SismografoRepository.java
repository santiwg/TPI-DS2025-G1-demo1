package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.Sismografo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SismografoRepository extends JpaRepository<Sismografo, Long> {
}
