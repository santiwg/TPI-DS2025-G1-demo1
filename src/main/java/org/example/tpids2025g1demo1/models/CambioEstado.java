package org.example.tpids2025g1demo1.models;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity

public class CambioEstado {
    @Column()
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

    @Override
    public String toString() {
        return "CambioEstado{" +
                "fechaHoraFin=" + fechaHoraFin +
                ", fechaHoraInicio=" + fechaHoraInicio +
                ", estado=" + estado +
                ", empleadoResponsable=" + empleadoResponsable +
                '}';
    } // Muestra el valor de los atributos del objeto CambioEstado
}
