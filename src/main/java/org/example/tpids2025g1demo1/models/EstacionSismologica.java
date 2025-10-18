package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;

public class EstacionSismologica {
    private String codigoEstacion;
    private String documentoCerficacionAdq;
    private LocalDateTime fechaSolicitudCertificacion;
    private float latitud;
    private float longitud;
    private String nombre;
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
