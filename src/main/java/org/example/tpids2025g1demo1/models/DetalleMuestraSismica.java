package org.example.tpids2025g1demo1.models;

import java.util.Timer;

public class DetalleMuestraSismica {
    private double valor;
    private TipoDeDato tipoDeDato;

    public DetalleMuestraSismica(){}

    public DetalleMuestraSismica(double valor, TipoDeDato tipoDeDato) {
        this.valor = valor;
        this.tipoDeDato = tipoDeDato;
    }

    public String getDatos() {
        return this.tipoDeDato.getDenominacion()+":"+valor;
    } // Devuelve la denominación del tipo de dato y su valor. Ejemplo: 'Longitud : 20'
}
