package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "evento_sismico",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_evento_sismico_natural",
        columnNames = {
            "fecha_hora_ocurrencia",
            "latitud_epicentro",
            "latitud_hipocentro",
            "longitud_epicentro",
            "longitud_hipocentro",
            "valor_magnitud"
        }
    )
)
public class EventoSismico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_hora_fin", nullable = true)
    private LocalDateTime fechaHoraFin;
    @Column(name = "fecha_hora_ocurrencia")
    private LocalDateTime fechaHoraOcurrencia;
    @Column(name = "latitud_epicentro")
    private float latitudEpicentro;
    @Column(name = "latitud_hipocentro")
    private float latitudHipocentro;
    @Column(name = "longitud_epicentro")
    private float longitudEpicentro;
    @Column(name = "longitud_hipocentro")
    private float longitudHipocentro;
    @Column(name = "valor_magnitud")
    private float valorMagnitud; //chequear
    @ManyToOne
    private Estado estadoActual;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "evento_sismico_id", nullable = true)
    private Set<CambioEstado> cambioEstado = new HashSet<>();
    //cascade = ALL: propaga al hijo las operaciones sobre el padre:
    //persist/save: al guardar el EventoSismico, se insertan también los CambioEstado nuevos agregados a la lista.
    @ManyToOne
    private ClasificacionSismo clasificacion;
    @ManyToOne
    private OrigenDeGeneracion origenGeneracion;
    @ManyToOne
    private AlcanceSismo alcanceSismo;
    @OneToMany
    @JoinColumn(name = "evento_sismico_id", nullable = true)
    private Set<SerieTemporal> serieTemporal = new HashSet<>();
    @ManyToOne
    @JoinColumn(nullable = true)
    private Empleado analistaSuperior;

    public EventoSismico() {
    }

    public EventoSismico(LocalDateTime fechaHoraOcurrencia, float latitudEpicentro, float latitudHipocentro, float longitudEpicentro, float longitudHipocentro, float valorMagnitud, Estado estadoActual, List<CambioEstado> cambioEstado, ClasificacionSismo clasificacion, OrigenDeGeneracion origenGeneracion, AlcanceSismo alcanceSismo, List<SerieTemporal> serieTemporal) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.latitudEpicentro = latitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudEpicentro = longitudEpicentro;
        this.longitudHipocentro = longitudHipocentro;
        this.valorMagnitud = valorMagnitud;
        this.estadoActual = estadoActual;
        this.cambioEstado = new HashSet<>(cambioEstado);
        this.clasificacion = clasificacion;
        this.origenGeneracion = origenGeneracion;
        this.alcanceSismo = alcanceSismo;
        this.serieTemporal = new HashSet<>(serieTemporal);
    }

    public boolean esAutoDetectado(){
        return this.estadoActual.esAutoDetectado();
    } // Metodo que nos comunica si el estado actual es AutoDetectado

    public boolean esPendienteDeRevision() { return this.estadoActual.esPendienteDeRevision(); } // Metodo que nos comunica si el estado actual es PendienteDeRevision

    public Map<String, Object> getDatosPrincipales() { // Obtiene los datos principales del evento ordenados dentro de un HashMap
        LocalDateTime fechaHoraOcurrencia = this.getFechaHoraOcurrencia();
        float latitudEpicentro = this.getLatitudEpicentro();
        float latitudHipocentro = this.getLatitudHipocentro();
        float longitudEpicentro = this.getLongitudEpicentro();
        float longitudHipocentro = this.getLongitudHipocentro();
        float valorMagnitud = this.getValorMagnitud();

        Map<String, Object> datos = new HashMap<>();
        datos.put("fechaHoraOcurrencia", fechaHoraOcurrencia);
        datos.put("latitudEpicentro", latitudEpicentro);
        datos.put("latitudHipocentro", latitudHipocentro);
        datos.put("longitudEpicentro", longitudEpicentro);
        datos.put("longitudHipocentro", longitudHipocentro);
        datos.put("valorMagnitud", valorMagnitud);

        return datos;
    }

    public LocalDateTime getFechaHoraOcurrencia() {
        return fechaHoraOcurrencia;
    }

    public float getLatitudEpicentro() {
        return latitudEpicentro;
    }

    public float getLatitudHipocentro() {
        return latitudHipocentro;
    }

    public float getLongitudEpicentro() {
        return longitudEpicentro;
    }

    public float getLongitudHipocentro() {
        return longitudHipocentro;
    }

    public float getValorMagnitud() {
        return valorMagnitud;
    }

    public CambioEstado revisar(LocalDateTime fechaHoraInicio, Estado estado, Empleado empleadoLogueado) { // Pone fin al ultimo cambio de estado y crea uno nuevo
        this.buscarUltimoEstado(fechaHoraInicio); // Busca el ultimo CambioEstado
        CambioEstado nuevoEstado = this.crearCambioEstado(fechaHoraInicio, estado, empleadoLogueado); // Crea una nueva instancia de CambioEstado
        this.setEstadoActual(estado); // Asigna el nuevo estado actual

        return nuevoEstado; // Devuelve el cambio de estado para que el gestor lo almacene y posteriormente no se tenga que buscar.
    }

    public void rechazar(LocalDateTime fechaHoraInicio, Estado estado, Empleado empleadoLogueado, CambioEstado ultimoEstado) { // Pone fin al ultimo cambio de estado y crea uno
        this.actualizarUltimoEstado(fechaHoraInicio, ultimoEstado); // Setea fechaFin al ultimo estado
        this.crearCambioEstado(fechaHoraInicio, estado, empleadoLogueado); // Crea un nuevo cambio de estado
        this.setEstadoActual(estado); // Asigna el nuevo estado acutal
    }

    public void confirmar(LocalDateTime fechaHoraInicio, Estado estado, Empleado empleadoLogueado, CambioEstado ultimoEstado) {
        this.actualizarUltimoEstado(fechaHoraInicio, ultimoEstado); // Setea fechaFin al ultimo estado
        this.crearCambioEstado(fechaHoraInicio, estado, empleadoLogueado); // Crea un nuevo cambio de estado
        this.setEstadoActual(estado); // Asigna el nuevo estado acutal
    }

    public ArrayList<String> buscarDatosSeriesTemporales(ArrayList<Sismografo> sismografos){ // Obtiene los datos de todas las series temporales del evento
        ArrayList<String> datosSeries = new ArrayList<>();
        for (SerieTemporal serie: serieTemporal){ // Itera las series temporales
            datosSeries.add(serie.getDatos(sismografos)); // Obtiene los datos de las series temporales y los guarda en el array datosSeries
        }
        this.clasificarDatosPorEstacionSismologica(datosSeries); // Clasifica los elementos del array datosSeries por estacion sismologica
        return datosSeries;
    }

    public CambioEstado crearCambioEstado(LocalDateTime fechaHoraInicio, Estado estado, Empleado empleadoLogueado) { // Crea un nuevo cambio de estado
        CambioEstado nuevoCambio = new CambioEstado(fechaHoraInicio, estado, empleadoLogueado); // Crea una instancia de CambioEstado
        this.cambioEstado.add(nuevoCambio); // Añade la nueva instancia al set cambioEstado

        return nuevoCambio; // Devuelve el cambio de estado para que el gestor lo almacene y posteriormente no se tenga que buscar.
    }

    public void buscarUltimoEstado(LocalDateTime fechaHoraDeFin) { // Busca el ultimo cambio de estado y le asigna una fechaHoraFin
        for(CambioEstado cambioEstado: cambioEstado){ // Itera los cambios de estado
            if(cambioEstado.esActual()){
                cambioEstado.setFechaHoraFin(fechaHoraDeFin); // Setea fechaHoraFin al cambio de estado actual
            }
        }
    }

    public void actualizarUltimoEstado(LocalDateTime fechaHoraDeFin,CambioEstado ultimoEstado) { // Setea fechaHoraFin al ultimo cambio de estado
        ultimoEstado.setFechaHoraFin(fechaHoraDeFin);
    }

    public void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String mostrarAlcance() {
        return this.alcanceSismo.getNombre();
    }

    public String mostrarClasificacion() {
        return this.clasificacion.getNombre();
    }

    public String mostrarOrigenGeneracion() {
        return this.origenGeneracion.getNombre();
    }

    public void clasificarDatosPorEstacionSismologica(ArrayList<String> datosSeries) { // Ordena la lista de datos de las series sismologicas por estacion sismologica
        datosSeries.sort(Comparator.comparing(dato -> {
            // Extrae el código de estación
            /* Hacerlo de esta forma evitará la necesidad de modificar el código si la
            estación sismológica ya no se pone al frente */
            int inicio = dato.indexOf("Estacion Sismologica:") + "Estacion Sismologica:".length();
            int fin = dato.indexOf("-", inicio);
            return dato.substring(inicio, fin);
        }));
    }

    @Override
    public String toString() {
        return "EventoSismico{" +
                "fechaHoraFin=" + fechaHoraFin +
                ", fechaHoraOcurrencia=" + fechaHoraOcurrencia +
                ", estadoActual=" + estadoActual +
                ", cambioEstado=" + cambioEstado +
                '}';
    } // Muestra el valor de los atributos del objeto EventoSismico

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoSismico that = (EventoSismico) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}