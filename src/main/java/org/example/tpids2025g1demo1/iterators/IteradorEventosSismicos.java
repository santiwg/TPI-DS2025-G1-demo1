package org.example.tpids2025g1demo1.iterators;

import java.util.ArrayList;

import org.example.tpids2025g1demo1.interfaces.IIterador;
import org.example.tpids2025g1demo1.models.EventoSismico; 

public class IteradorEventosSismicos implements IIterador {
    EventoSismico[] eventosSismicos;
    int actual;

    public IteradorEventosSismicos(Object[] elementos) {
        this.eventosSismicos = (EventoSismico[])elementos;
        this.actual = 0;
    }

    @Override
    public boolean haTerminado() {
        return actual >= eventosSismicos.length;
    }

    @Override
    public void siguiente() {
        actual++;
    }

    @Override
    public Object actual() {
        if (cumpleFiltro()) {
        return eventosSismicos[actual];}
        else {
            return null;
        }
    }
    @Override
    public void primero() {
        actual = 0;
    }
    @Override
    public boolean cumpleFiltro() {
        EventoSismico evento = eventosSismicos[actual];
        return (evento.esAutoDetectado() || evento.esPendienteDeRevision());
    }
}
