package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class SerieTemporal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private boolean condicionAlarma;
    @Column()
    private LocalDateTime fechaHoraInicioRegistroMuestras;
    @Column()
    private LocalDateTime fechaHoraRegistro;
    @Column()
    private String frecuenciaMuestreo; // Ver el tipo de dato
    @ManyToOne
    private Estado estado;
    @OneToMany
    @JoinColumn(name = "serie_temporal_id", nullable = true)
    private Set<MuestraSismica> muestraSismica = new HashSet<>();

    public SerieTemporal() {}
    public SerieTemporal(boolean condicionAlarma, LocalDateTime fechaHoraInicioRegistroMuestras, LocalDateTime fechaHoraRegistro, String frecuenciaMuestreo, Estado estado, List<MuestraSismica> muestrasSismicas) {
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraInicioRegistroMuestras = fechaHoraInicioRegistroMuestras;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.estado = estado;
        this.muestraSismica = (muestrasSismicas == null) ? new HashSet<>() : new HashSet<>(muestrasSismicas);
    }
    public String getDatos(ArrayList<Sismografo> sismografos){
        String datos = "Fecha/Hora inicio: " + fechaHoraInicioRegistroMuestras; // Inicializa un string con la fecha y hora de inicio del registro de muestras
        for (MuestraSismica muestra : muestraSismica){ // Itera las muestras sismicas
            datos = datos + " -- [" + muestra.getDatos() + "]"; // Agrega los datos de cada muestra al string
        }
        String codigoEstacion = "Estacion Sismologica: " + this.obtenerEstacionSismologica(sismografos); // Guarda el codigo de la estacion sismologica en un string
        return codigoEstacion + "," + datos; // Devuelve el string completo con los datos de las muestras y el codigo de la estacion concatenados
    }
    public String obtenerEstacionSismologica(ArrayList<Sismografo> sismografos){ // Obtiene el codigo de la estacion sismologica
        for(Sismografo sismografo : sismografos){ // Itera los sismografos
            if (sismografo.esSismografoDeSerieTemporal(this)){
                return sismografo.getCodigoEstacion(); // Cuando encuentra el sismógrafo al que pertence la serie, obtiene el código de la estación
            }
        }
        return null; // En caso de que no se encuentre una estacion a la que pertenezca el sismografo, devuelve un null
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerieTemporal that = (SerieTemporal) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
}