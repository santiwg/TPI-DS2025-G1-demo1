package org.example.tpids2025g1demo1.models;

public class OrigenDeGeneracion {
    private String nombre;
    private String descripcion;

    public OrigenDeGeneracion(){}
    public OrigenDeGeneracion(String nombre, String descripcion){
        this.nombre=nombre;
        this.descripcion=nombre;
    }
    public String getNombre(){
        return this.nombre;
    }
}
