package org.example.tpids2025g1demo1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class AlcanceSismo {
    @Column()
    private String descripcion;
    @Column(unique = true)
    private String nombre;

    public AlcanceSismo(){}

    public AlcanceSismo(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
