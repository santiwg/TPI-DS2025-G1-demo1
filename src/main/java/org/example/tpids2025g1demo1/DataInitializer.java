package org.example.tpids2025g1demo1;

import jakarta.annotation.PostConstruct;
import org.example.tpids2025g1demo1.models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class DataInitializer {

    @PostConstruct
    public static void initData() {

        // Creo instancias de tipos de dato
        TipoDeDato velocidadDeOnda = new TipoDeDato("Velocidad de onda","Km/seg", 1f);
        TipoDeDato frecuenciaDeOnda = new TipoDeDato("Frecuencia De Onda","Hz",15f);
        TipoDeDato longitud = new TipoDeDato("Longitud","Km/ciclo",1f);

        // Creo instancias de detalles de muestras sismicas y las añado a un array de detalles de muestras sismicas
        DetalleMuestraSismica velocidad = new DetalleMuestraSismica(0.7,velocidadDeOnda);
        DetalleMuestraSismica frecuencia = new DetalleMuestraSismica(10,frecuenciaDeOnda);
        DetalleMuestraSismica medidaLongitud = new DetalleMuestraSismica(0.7, longitud);

        ArrayList<DetalleMuestraSismica> detallesMuestra = new ArrayList<DetalleMuestraSismica>();
        detallesMuestra.add(velocidad);
        detallesMuestra.add(frecuencia);
        detallesMuestra.add(medidaLongitud);

        // Creo instancias de muestras sismicas y las añado a un array de muestras sismicas
        MuestraSismica muestra1 = new MuestraSismica(LocalDateTime.of(2025, 6,14,13,48,34),detallesMuestra);
        MuestraSismica muestra2 = new MuestraSismica(LocalDateTime.of(2025, 6,14,13,28,54),detallesMuestra);
        MuestraSismica muestra3 = new MuestraSismica(LocalDateTime.of(2025, 6,14,12,28,54),detallesMuestra);

        ArrayList<MuestraSismica> muestrasSismicas=new ArrayList<>();
        muestrasSismicas.add(muestra1);
        muestrasSismicas.add(muestra2);
        muestrasSismicas.add(muestra3);

        // Creo el estado de la serie temporal
        Estado completada = new Estado("SerieTemporal","Completada");

        // Creo instancias de series temporales y las añado a distintos arrays de series temporales
        SerieTemporal serie1=new SerieTemporal(
                false,
                LocalDateTime.of(2025, 6,14,12,28,54),
                LocalDateTime.of(2025, 6,14,12,28,54),
                "50 hz",
                completada,
                muestrasSismicas);
        SerieTemporal serie2 = new SerieTemporal(
                false,
                LocalDateTime.of(2025, 6,14,12,21,54),
                LocalDateTime.of(2025, 6,14,12,21,54),
                "50 hz",
                completada,
                muestrasSismicas);
        SerieTemporal serie3 = new SerieTemporal(
                false,
                LocalDateTime.of(2025, 6,14,12,23,54),
                LocalDateTime.of(2025, 6,14,12,23,54),
                "50 hz",
                completada,
                muestrasSismicas);
        SerieTemporal serie4 = new SerieTemporal(
                false,
                LocalDateTime.of(2025, 6,14,12,29,54),
                LocalDateTime.of(2025, 6,14,12,29,54),
                "50 hz",
                completada,
                muestrasSismicas);

        ArrayList<SerieTemporal> seriesTemporales = new ArrayList<>();
        seriesTemporales.add(serie1);
        seriesTemporales.add(serie2);
        seriesTemporales.add(serie3);
        seriesTemporales.add(serie4);

        ArrayList<SerieTemporal> seriesSismografo1 = new ArrayList<>();
        seriesSismografo1.add(serie2);
        seriesSismografo1.add(serie4);

        ArrayList<SerieTemporal> seriesSismografo2 = new ArrayList<>();
        seriesSismografo2.add(serie1);
        seriesSismografo2.add(serie3);

        // Creo instancias de estaciones sismologicas
        EstacionSismologica estacion1 = new EstacionSismologica("AAA",-32.56f,-94.56f,"Estacion Uno");
        EstacionSismologica estacion2 = new EstacionSismologica("AAB",-32.56f,-94.56f,"Estacion Dos");

        // Creo instancias de sismografos y añado a un array de sismografos
        Sismografo sismografo1 = new Sismografo(1,seriesSismografo1,estacion1);
        Sismografo sismografo2 = new Sismografo(2,seriesSismografo2,estacion2);
        ArrayList<Sismografo> sismografos = new ArrayList<>();
        sismografos.add(sismografo1);
        sismografos.add(sismografo2);

        // Creo instancias de empleado,usuario y sesion
        Empleado responsable = new Empleado("Gimenez","miltongimenez@gmail.com","Milton","3526062011");
        Usuario usuarioLogueado = new Usuario("123","miltong",responsable);
        Sesion sesionActual = new Sesion(LocalDateTime.now(),usuarioLogueado);

        // Creo instancias de alcance, clasificacion, origen de generacion
        AlcanceSismo regional = new AlcanceSismo("Hasta 1000km","Regional");
        OrigenDeGeneracion explosionesMinas = new OrigenDeGeneracion("Explosiones en minas","Sismo provocado por explosiones de minas");
        ClasificacionSismo intermedio = new ClasificacionSismo(61f,300f,"Intermedios");

        // Creo instancias de estados y los añado a un array de estados
        Estado autoDetectado = new Estado("EventoSismico","AutoDetectado");
        Estado pendienteDeRevision = new Estado("EventoSismico","PendienteDeRevision");
        Estado bloqueadoEnRevision = new Estado("EventoSismico","BloqueadoEnRevision");
        Estado rechazado = new Estado("EventoSismico","Rechazado");
        Estado confirmado = new Estado("EventoSismico","Confirmado");
        Estado rechazadoAmbitoIncorrecto = new Estado("Evento","Rechazado");

        ArrayList<Estado> listaEstados = new ArrayList<>();
        listaEstados.add(autoDetectado);
        listaEstados.add(pendienteDeRevision);
        listaEstados.add(bloqueadoEnRevision);
        listaEstados.add(rechazadoAmbitoIncorrecto);
        listaEstados.add(rechazado);
        listaEstados.add(confirmado);

        // Creo instancias de cambios de estado, una lista por cada tipo de cambio y los añado a esa lista
        CambioEstado cambioAutoDetectado = new CambioEstado(LocalDateTime.now(),autoDetectado,responsable);
        ArrayList<CambioEstado> listaCambioAutoDetectado = new ArrayList<>();
        listaCambioAutoDetectado.add(cambioAutoDetectado);

        CambioEstado cambioPendienteDeRevision = new CambioEstado(LocalDateTime.now(), pendienteDeRevision, responsable);
        ArrayList<CambioEstado> listaCambioPendienteDeRevision = new ArrayList<>();
        listaCambioPendienteDeRevision.add(cambioPendienteDeRevision);

        CambioEstado cambioBloqueadoEnRevision = new CambioEstado(LocalDateTime.now(), bloqueadoEnRevision, responsable);
        ArrayList<CambioEstado> listaCambioBloqueadoEnRevision = new ArrayList<>();
        listaCambioBloqueadoEnRevision.add(cambioBloqueadoEnRevision);

        CambioEstado cambioRechazado = new CambioEstado(LocalDateTime.now(), rechazado, responsable);
        ArrayList<CambioEstado> listaCambioRechazado = new ArrayList<>();
        listaCambioRechazado.add(cambioRechazado);

        CambioEstado cambioConfirmado = new CambioEstado(LocalDateTime.now(), confirmado, responsable);
        ArrayList<CambioEstado> listaCambioConfirmado = new ArrayList<>();
        listaCambioConfirmado.add(cambioConfirmado);

        CambioEstado cambioRechazadoIncorrecto = new CambioEstado(LocalDateTime.now(), rechazadoAmbitoIncorrecto, responsable);
        ArrayList<CambioEstado> listaCambioRechazadoAmbitoIncorrecto = new ArrayList<>();
        listaCambioRechazadoAmbitoIncorrecto.add(cambioRechazadoIncorrecto);

        // Creo instancias de eventos sismicos y los añado a un array de eventos sismicos
        EventoSismico evento1 = new EventoSismico(
                LocalDateTime.of(2025,6,16,15,58,20),
                -21.5f,-34.5f,-21.7f,-34.6f,7,
                bloqueadoEnRevision,listaCambioBloqueadoEnRevision,
                intermedio,explosionesMinas,regional,
                seriesTemporales);
        EventoSismico evento2 = new EventoSismico(
                LocalDateTime.of(2025,6,17,15,58,20),
                -21.5f,-34.5f,-21.7f,-34.6f,7,
                rechazado,listaCambioRechazado,
                intermedio,explosionesMinas,regional,
                seriesTemporales);
        EventoSismico evento3 = new EventoSismico(
                LocalDateTime.of(2025,6,16,15,58,20),
                -21.5f,-34.5f,-21.7f,-34.6f,7,
                autoDetectado,listaCambioAutoDetectado,
                intermedio,explosionesMinas,regional,
                seriesTemporales);
        EventoSismico evento4 = new EventoSismico(
                LocalDateTime.of(2025,6,15,15,58,20),
                -23.6f,-37.8f,-21.9f,-30.6f,8,
                pendienteDeRevision,listaCambioPendienteDeRevision,
                intermedio,explosionesMinas,regional,
                seriesTemporales);
        EventoSismico evento5 = new EventoSismico(
                LocalDateTime.of(2025,6,18,15,58,20),
                -21.5f,-34.5f,-21.7f,-34.6f,7,
                confirmado,listaCambioConfirmado,
                intermedio,explosionesMinas,regional,
                seriesTemporales);
        EventoSismico evento6 = new EventoSismico(
                LocalDateTime.of(2025,6,19,15,58,20),
                -21.5f,-34.5f,-21.7f,-34.6f,7,
                rechazado,listaCambioRechazado,
                intermedio,explosionesMinas,regional,
                seriesTemporales);

        ArrayList<EventoSismico> eventosSismicos = new ArrayList<>();
        eventosSismicos.add(evento1);
        eventosSismicos.add(evento2);
        eventosSismicos.add(evento3);
        eventosSismicos.add(evento4);
        eventosSismicos.add(evento5);
        eventosSismicos.add(evento6);

        // Creo instancias del gestor y de la pantalla
        /*GestorRegRRes gestor1 = new GestorRegRRes(eventosSismicos,sesionActual,listaEstados,sismografos);
        PantRegRRes pantalla = new PantRegRRes(gestor1);

        GestorRegRRes gestor2 = new GestorRegRRes(eventosSismicos,sesionActual,listaEstados,sismografos);
        PantRegRRes pantalla2 = new PantRegRRes(gestor2);

        GestorRegRRes gestor3 = new GestorRegRRes(eventosSismicos,sesionActual,listaEstados,sismografos);
        PantRegRRes pantalla3 = new PantRegRRes(gestor3);*/
    }
}