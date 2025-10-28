package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class MuestraSismica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private LocalDateTime fechaHoraMuestra;
    @OneToMany
    private ArrayList<DetalleMuestraSismica> detalleMuestraSismica;


    public MuestraSismica(LocalDateTime fechaHoraMuestra, ArrayList<DetalleMuestraSismica> detalleMuestraSismica) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.detalleMuestraSismica = detalleMuestraSismica;
    }

    public String getDatos() {
        String datos = "Fecha/Hora muestra: " + fechaHoraMuestra; // Inicializa un string con la fecha y hora de la muestra
        for (DetalleMuestraSismica detalle:detalleMuestraSismica){ // Itera los detalles de la muestra sismica
            datos = datos + "," + detalle.getDatos(); // Agrega los datos de cada detalle de muestra al string
        }
        return datos; // Devuelve el string completo con los datos concatenados
    }
}
