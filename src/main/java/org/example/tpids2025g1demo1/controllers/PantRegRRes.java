package org.example.tpids2025g1demo1.controllers;

import org.example.tpids2025g1demo1.services.GestorRegRRes;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/reg-resultado-revision")
public class PantRegRRes {
    private GestorRegRRes gestor;

    public PantRegRRes(GestorRegRRes gestor) {
        this.gestor = gestor;
        
    }

    @GetMapping("/norevisados")
     public List<String> opcRegResultadoES(){
        return gestor.nuevaRevisionES();
        // Inicia el registro del resultado, obtiene los eventos sísmicos no revisados desde el gestor
    }


    
    @PostMapping("/seleccion-evento")
    public List<String> tomarSeleccionES(String evento){
        return gestor.tomarSeleccionES(evento);
        // Pasa el evento sísmico seleccionado al gestor y devuelve los datos del mismo (alcance, clasificacion, origen)
    }

    
    
    @PostMapping("/seleccion-resultado")
    public String tomarSeleccionResultado(String seleccion, String evento){
        // Toma la elección del resultado y el evento y los pasa al gestor
        // Devuelve un mensaje de éxito u error
        return gestor.tomarSeleccionResultado(seleccion, evento);
    }

    
}