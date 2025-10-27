package org.example.tpids2025g1demo1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Usuario {
    @Column()
    private String contrasenia;
    @Column(unique = true)
    private String nombreUsuario;
    @ManyToOne
    private Empleado empleado;

    public Usuario(String contrasenia, String nombreUsuario, Empleado empleado) {
        this.contrasenia = contrasenia;
        this.nombreUsuario = nombreUsuario;
        this.empleado = empleado;
    }

    public Empleado getEmpleado() { return this.empleado; }
}
