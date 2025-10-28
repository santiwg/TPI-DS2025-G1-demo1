package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EstacionSismologica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String codigoEstacion;
    @Column(nullable = true)
    private String documentoCerficacionAdq;
    @Column(nullable = true)
    private LocalDateTime fechaSolicitudCertificacion;
    @Column()
    private float latitud;
    @Column()
    private float longitud;
    @Column()
    private String nombre;
    @Column(nullable = true)
    private int nroCertificacionAdquisicion;

    public EstacionSismologica(){}

    public EstacionSismologica(String codigoEstacion, float latitud, float longitud, String nombre) {
        this.codigoEstacion = codigoEstacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    public EstacionSismologica(String codigoEstacion, String documentoCerficacionAdq, LocalDateTime fechaSolicitudCertificacion, float latitud, float longitud, String nombre, int nroCertificacionAdquisicion) {
        this.codigoEstacion = codigoEstacion;
        this.documentoCerficacionAdq = documentoCerficacionAdq;
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.nroCertificacionAdquisicion = nroCertificacionAdquisicion;
    }

    public String getCodigoEstacion() {
        return codigoEstacion;
    } // Obtiene el codigo de la estacion

}
