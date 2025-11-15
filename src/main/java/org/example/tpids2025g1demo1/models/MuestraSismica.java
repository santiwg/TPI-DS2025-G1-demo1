package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class MuestraSismica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private LocalDateTime fechaHoraMuestra;
    @OneToMany
    @JoinColumn(name = "muestra_sismica_id", nullable = true)
    private Set<DetalleMuestraSismica> detalleMuestraSismica = new HashSet<>();

    public MuestraSismica(){}
    public MuestraSismica(LocalDateTime fechaHoraMuestra, List<DetalleMuestraSismica> detalleMuestraSismica) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.detalleMuestraSismica = (detalleMuestraSismica == null) ? new HashSet<>() : new HashSet<>(detalleMuestraSismica);
    }

    public String getDatos() {
        String datos = "Fecha/Hora muestra: " + fechaHoraMuestra; // Inicializa un string con la fecha y hora de la muestra
        for (DetalleMuestraSismica detalle:detalleMuestraSismica){ // Itera los detalles de la muestra sismica
            datos = datos + "," + detalle.getDatos(); // Agrega los datos de cada detalle de muestra al string
        }
        return datos; // Devuelve el string completo con los datos concatenados
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuestraSismica that = (MuestraSismica) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
