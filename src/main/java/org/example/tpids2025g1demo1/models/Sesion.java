package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;

public class Sesion {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraCierre;
    private Usuario usuario;

    public Sesion(LocalDateTime fechaHoraInicio, Usuario usuario) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.usuario = usuario;
    }

    public Empleado obtenerEmpleadoLogueado() {
        return this.usuario.getEmpleado();
    } // Obtiene el empleado logueado asociado al usuario de la sesion actual
}
