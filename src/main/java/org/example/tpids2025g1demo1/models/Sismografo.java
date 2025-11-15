package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Sismografo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)//no debería poder ser null en realidad hay que ver como hacemos!!
    private LocalDateTime fechaAdquisicion;
    @Column(unique = true)
    private int identificadorSismografo;
    @Column(nullable = true) //no debería poder ser null en realidad hay que ver como hacemos!!
    private String nroSerie;
    @OneToMany
    @JoinColumn(name = "sismografo_id", nullable = true)
    private List<SerieTemporal> serieTemporal = new ArrayList<>();
    @ManyToOne
    private EstacionSismologica estacionSismologica;
    @ManyToOne
    @JoinColumn(nullable = true) //no debería poder ser null en realidad hay que ver como hacemos!!
    private Estado estadoActual;
    @OneToMany
    @JoinColumn(name = "sismografo_id", nullable = true)
    private List<CambioEstado> cambioEstado = new ArrayList<>();

    public Sismografo(){}

    public Sismografo(int identificadorSismografo, List<SerieTemporal> seriesTemporales, EstacionSismologica estacionSismologica) {
        this.identificadorSismografo = identificadorSismografo;
        this.serieTemporal = seriesTemporales;
        this.estacionSismologica = estacionSismologica;
    }

    public Sismografo(LocalDateTime fechaAdquisicion, int idSismografo, String nroSerie, List<SerieTemporal> serieTemporal, EstacionSismologica estacionSismologica, Estado estadoActual, List<CambioEstado> cambiosDeEstado) {
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
