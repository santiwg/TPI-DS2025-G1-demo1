package org.example.tpids2025g1demo1.models;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity

public class CambioEstado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private LocalDateTime fechaHoraFin;
    @Column()
    private LocalDateTime fechaHoraInicio;
    @ManyToOne
    private Estado estado;
    @ManyToOne
    private Empleado empleadoResponsable;

    public CambioEstado() {}

    public CambioEstado(LocalDateTime fechaHoraInicio, Estado estado, Empleado empleadoResponsable) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
        this.empleadoResponsable = empleadoResponsable;
    }

    public boolean esActual() { return this.fechaHoraFin == null; } // Metodo que nos comunica si el cambio de estado esActual comprobando que nunca termino

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    } // Setea fechaHoraFin de un objeto CambioEstado

    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }

    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }

    // relación con EventoSismico ahora se maneja unidireccionalmente desde EventoSismico

    @Override
    public String toString() {
        return "CambioEstado{" +
                "fechaHoraFin=" + fechaHoraFin +
                ", fechaHoraInicio=" + fechaHoraInicio +
                ", estado=" + estado +
                ", empleadoResponsable=" + empleadoResponsable +
                '}';
    } // Muestra el valor de los atributos del objeto CambioEstado

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CambioEstado that = (CambioEstado) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
