package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Sismografo {
    @Column()
    private LocalDateTime fechaAdquisicion;
    @Column(unique = true)
    private int identificadorSismografo;
    @Column()
    private String nroSerie;
    @OneToMany
    private ArrayList<SerieTemporal> serieTemporal;
    @ManyToOne
    private EstacionSismologica estacionSismologica;
    @ManyToOne
    private Estado estadoActual;
    @OneToMany
    private ArrayList<CambioEstado> cambioEstado;

    public Sismografo(){}

    public Sismografo(int identificadorSismografo, ArrayList<SerieTemporal> seriesTemporales, EstacionSismologica estacionSismologica) {
        this.identificadorSismografo = identificadorSismografo;
        this.serieTemporal = seriesTemporales;
        this.estacionSismologica = estacionSismologica;
    }

    public Sismografo(LocalDateTime fechaAdquisicion, int idSismografo, String nroSerie, ArrayList<SerieTemporal> serieTemporal, EstacionSismologica estacionSismologica, Estado estadoActual, ArrayList<CambioEstado> cambiosDeEstado) {
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = idSismografo;
        this.nroSerie = nroSerie;
        this.serieTemporal = serieTemporal;
        this.estacionSismologica = estacionSismologica;
        this.estadoActual = estadoActual;
        this.cambioEstado = cambiosDeEstado;
    }

    public boolean esSismografoDeSerieTemporal(SerieTemporal serie){
        return this.serieTemporal.contains(serie);
    } // Metodo que indica si una serie temporal es del sismografo

    public String getCodigoEstacion(){
        return this.estacionSismologica.getCodigoEstacion();
    }

}
