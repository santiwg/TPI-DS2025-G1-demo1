package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Sesion {
    @Column()
    private LocalDateTime fechaHoraInicio;
    @Column()
    private LocalDateTime fechaHoraCierre;
    @ManyToOne
    private Usuario usuario;

    public Sesion(LocalDateTime fechaHoraInicio, Usuario usuario) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.usuario = usuario;
    }

    public Empleado obtenerEmpleadoLogueado() {
        return this.usuario.getEmpleado();
    } // Obtiene el empleado logueado asociado al usuario de la sesion actual
}
