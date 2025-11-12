package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.EventoSismico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

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
