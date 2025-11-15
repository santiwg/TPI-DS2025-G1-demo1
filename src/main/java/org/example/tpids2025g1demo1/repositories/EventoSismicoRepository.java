package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.EventoSismico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoSismicoRepository extends JpaRepository<EventoSismico, Long> {
    @EntityGraph(attributePaths = {
	    "estadoActual",
	    "clasificacion",
	    "origenGeneracion",
	    "alcanceSismo",
	    "cambioEstado",
	    "serieTemporal"
    })
    List<EventoSismico> findAll();
	/**
	 * Carga "completa" del evento recibido sin usar su ID explícitamente.
	 *
	 * - El parámetro es la propia instancia de EventoSismico; el JPQL
	 *   "where e = ?1" compara por identidad, por lo que JPA utilizará
	 *   internamente su identificador sin que nosotros lo manejemos.
	 * - El @EntityGraph indica las asociaciones que deben inicializarse de forma
	 *   anticipada para evitar LazyInitializationException cuando se accede a
	 *   dichas relaciones fuera del repositorio/DAO.
	 * - Útil para rehidratar (reattach) una entidad potencialmente "detached"
	 *   y continuar en el servicio con una instancia gestionada y su grafo
	 *   necesario ya cargado.
	 */
	@EntityGraph(attributePaths = {
    "estadoActual",
    "clasificacion",
    "origenGeneracion",
    "alcanceSismo",
    "cambioEstado",
	"serieTemporal",
	"serieTemporal.muestraSismica",
	"serieTemporal.muestraSismica.detalleMuestraSismica"
})
	@Query("select e from EventoSismico e where e = :evento")
	Optional<EventoSismico> fetchComplete(@Param("evento") EventoSismico evento);

    @EntityGraph(attributePaths = {
	    "estadoActual",
	    "clasificacion",
	    "origenGeneracion",
	    "alcanceSismo",
	    "cambioEstado",
	    "serieTemporal"
    })
    List<EventoSismico> findAllBy();
}
