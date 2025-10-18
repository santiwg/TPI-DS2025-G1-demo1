package org.example.tpids2025g1demo1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MuestraSismica {
    private LocalDateTime fechaHoraMuestra;
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
