package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
}
