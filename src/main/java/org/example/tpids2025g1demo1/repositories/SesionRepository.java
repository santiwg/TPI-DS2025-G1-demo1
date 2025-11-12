package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
	// Última sesión abierta (sin fecha de cierre), ordenada por inicio descendente
    @EntityGraph(attributePaths = {"usuario", "usuario.empleado"})
	Optional<Sesion> findTopByFechaHoraCierreIsNullOrderByFechaHoraInicioDesc();
    //"usuario.empleado" en @EntityGraph es ruta anidada. Indica: cargar la relación usuario de Sesion y, además, la relación empleado dentro de Usuario.
}
