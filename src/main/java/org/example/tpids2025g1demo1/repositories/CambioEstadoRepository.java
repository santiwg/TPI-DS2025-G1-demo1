package org.example.tpids2025g1demo1.repositories;

import org.example.tpids2025g1demo1.models.CambioEstado;
import org.example.tpids2025g1demo1.models.EventoSismico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CambioEstadoRepository extends JpaRepository<CambioEstado, Long> {

	// Carga el cambio de estado con sus relaciones necesarias (estado y empleadoResponsable)
	@EntityGraph(attributePaths = {"estado", "empleadoResponsable"})
	@Query("select c from CambioEstado c where c = :cambio")
	java.util.Optional<CambioEstado> fetchComplete(@Param("cambio") CambioEstado cambio);

	// Busca el cambio de estado 'actual' (fechaHoraFin null) del evento usando como
	// criterio la fechaHoraInicio tomada desde el propio objeto CambioEstado recibido.
	// Se evita pasar IDs explícitos; se pasa el agregado/objeto completo y se extraen
	// los atributos necesarios dentro de la consulta usando SpEL.
	@EntityGraph(attributePaths = {"estado", "empleadoResponsable"})
	@Query("select c from EventoSismico e join e.cambioEstado c where e = :evento and c.fechaHoraInicio = :#{#cambio.fechaHoraInicio} and c.fechaHoraFin is null")
	java.util.Optional<CambioEstado> fetchActualByCambioAndEvento(@Param("cambio") CambioEstado cambio,
		@Param("evento") EventoSismico evento);
}
