package org.example.tpids2025g1demo1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Empleado {
    @Column()
    private String apellido;
    @Column()
    private String mail;
    @Column()
    private String nombre;
    @Column()
    private String telefono;

    public Empleado() {}

    public Empleado(String apellido, String mail, String nombre, String telefono) {
        this.apellido = apellido;
        this.mail = mail;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    } // Muestra el valor de los atributos del objeto Empleado
}
