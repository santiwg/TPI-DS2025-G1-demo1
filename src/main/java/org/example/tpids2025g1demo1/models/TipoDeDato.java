package org.example.tpids2025g1demo1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TipoDeDato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String denominacion;
    @Column()
    private String nombreUnidadMedida;
    @Column()
    private float valorUmbral;

    public TipoDeDato(){}

    public TipoDeDato(String denominacion, String nombreUnidadMedida, float valorUmbral) {
        this.denominacion = denominacion;
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.valorUmbral = valorUmbral;
    }

    public String getDenominacion() {
        return denominacion;
    }
}