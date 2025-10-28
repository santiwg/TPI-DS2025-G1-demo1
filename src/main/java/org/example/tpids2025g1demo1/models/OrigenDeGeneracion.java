package org.example.tpids2025g1demo1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrigenDeGeneracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    @Column()
    private String descripcion;

    public OrigenDeGeneracion(){}
    public OrigenDeGeneracion(String nombre, String descripcion){
        this.nombre=nombre;
        this.descripcion=nombre;
    }
    public String getNombre(){
        return this.nombre;
    }
}
