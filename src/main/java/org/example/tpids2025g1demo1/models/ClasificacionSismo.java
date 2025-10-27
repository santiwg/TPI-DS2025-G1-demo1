package org.example.tpids2025g1demo1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ClasificacionSismo {
    @Column()
    private float kmProfundidadDesde;
    @Column()
    private float kmProfundidadHasta;
    @Column(unique = true)
    private String nombre;

    public ClasificacionSismo(){}

    public ClasificacionSismo(float kmProfundidadDesde, float kmProfundidadHasta, String nombre) {
        this.kmProfundidadDesde = kmProfundidadDesde;
        this.kmProfundidadHasta = kmProfundidadHasta;
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
}
