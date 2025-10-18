package org.example.tpids2025g1demo1.models;

import java.util.Objects;

public class Estado {
    private String ambito;
    private String nombreEstado;

    public Estado() {}

    public Estado(String ambito, String nombreEstado) {
        this.ambito = ambito;
        this.nombreEstado = nombreEstado;
    }

    /*
    Los siguentes metodos hacen una comparacion de strings, para informar los valores de la clase
     */
    public boolean esAutoDetectado(){
        return this.nombreEstado.equals("AutoDetectado");
    }
    public boolean esPendienteDeRevision(){
        return this.nombreEstado.equals("PendienteDeRevision");
    }
    public boolean esAmbitoEventoSismico(){
        return this.ambito.equals("EventoSismico");
    }
    public boolean esBloqueadoEnRevision(){
        return this.nombreEstado.equals("BloqueadoEnRevision");
    }
    public boolean esRechazado(){
        return this.nombreEstado.equals("Rechazado");
    }
    public boolean esConfirmado(){
        return this.nombreEstado.equals("Confirmado");
    }

    @Override
    public String toString() {
        return "Estado{" +
                "ambito='" + ambito + '\'' +
                ", nombreEstado='" + nombreEstado + '\'' +
                '}';
    } // Muestra el valor de los atributos del objeto Estado
}
