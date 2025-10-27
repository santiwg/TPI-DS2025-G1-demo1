package org.example.tpids2025g1demo1.models;

 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class DetalleMuestraSismica {
    @Column()
    private double valor;
    @ManyToOne
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
