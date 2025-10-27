package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
