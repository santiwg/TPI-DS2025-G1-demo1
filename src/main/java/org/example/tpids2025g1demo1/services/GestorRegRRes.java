package org.example.tpids2025g1demo1.services;

import org.example.tpids2025g1demo1.models.*;
import org.example.tpids2025g1demo1.controllers.PantRegRRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GestorRegRRes {

    private ArrayList<EventoSismico> listaEventosSismicos;
    private ArrayList<Map<String, Object>> listaESNoRevisados=new ArrayList<>();
    // La lista de eventos será un array de diccionarios, cada uno de los cuales tendrá evento y datos
    private EventoSismico eventoSismicoSeleccionado;
    private Estado estadoRechazado;
    private Estado estadoConfirmado;
    private ArrayList<String> listaDatosSeriesTemporales=new ArrayList<>();
    private Sesion sesion;
    private Empleado empleadoLogueado;
    private ArrayList<Estado> listaEstados;
    private Estado estadoBloqueadoEnRevision;
    private LocalDateTime fechaHoraActual;
    private PantRegRRes pantalla;
    private String nombreAlcance;
    private String nombreOrigenGeneracion;
    private String nombreClasificacion;
    private CambioEstado ultimoCambioDeEstado;
    private ArrayList<Sismografo> sismografos;
    private String seleccionResultado;

    public GestorRegRRes(ArrayList<EventoSismico> listaEventosSismicos, Sesion sesion, ArrayList<Estado> listaEstados, ArrayList<Sismografo> sismografos) {
        this.listaEventosSismicos = listaEventosSismicos;
        this.sesion = sesion;
        this.listaEstados = listaEstados;
        this.sismografos = sismografos;
    }

    public void nuevaRevisionES(PantRegRRes pantalla){
        this.pantalla=pantalla;
        this.buscarESNoRevisados();

        if (!listaESNoRevisados.isEmpty()){ // Verifica que haya eventos para revisar
            this.ordenarEventosSismicosPorFechaYHora(); // Ordena los eventos por fecha y hora
            // Arma un array con los datos de los eventos formateados para pasarlo a la pantalla
            ArrayList<String> datosEventos = listaESNoRevisados.stream()
                    .map(diccEvento -> {
                        // Obtiene el diccionario de datos del evento y le hace un toString
                        Map<String, Object> datos = (Map<String, Object>) diccEvento.get("datos");
                        String datosStr = String.format(
                                "fechaHoraOcurrencia=%s, latitudEpicentro=%.2f, latitudHipocentro=%.2f, longitudEpicentro=%.2f, longitudHipocentro=%.2f, valorMagnitud=%.1f",
                                datos.get("fechaHoraOcurrencia"),
                                datos.get("latitudEpicentro"),
                                datos.get("latitudHipocentro"),
                                datos.get("longitudEpicentro"),
                                datos.get("longitudHipocentro"),
                                datos.get("valorMagnitud")
                        );
                        return datosStr;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

            pantalla.mostrarESParaSeleccion(datosEventos); // Muestra los ES en pantalla para que el usuario seleccione uno
        }else { // Si no hay ES no revisados
            pantalla.informarNoHayESNoRevisados();
            this.cancelarCU();
        }

    }

    public void buscarESNoRevisados(){ // Metodo para buscar los ES auto detectados que están pendientes de revisión
        for (EventoSismico evento : listaEventosSismicos) { // Recorre todos los eventos sísmicos registrados
            if (evento.esAutoDetectado() || evento.esPendienteDeRevision()){ // Chequea el estado del evento
                Map<String, Object> diccionarioEvento = new HashMap<>(); // Nuevo diccionario que va a tener los eventos y sus datos
                diccionarioEvento.put("evento", evento);
                diccionarioEvento.put("datos", evento.getDatosPrincipales()); // Se espera que esto devuelva un Map
                listaESNoRevisados.add(diccionarioEvento);
            }
        }
    }

    public void ordenarEventosSismicosPorFechaYHora() {
        this.listaESNoRevisados.sort((diccEvento1, diccEvento2) -> {
            // Obtiene los datos del evento, que son un diccionario
            // Se debe hacer casteo porque estan definidos con tipo Object
            Map<String, Object> datos1 = (Map<String, Object>) diccEvento1.get("datos");
            Map<String, Object> datos2 = (Map<String, Object>) diccEvento2.get("datos");

            // Obtengo la fecha de cada evento
            LocalDateTime fecha1 = (LocalDateTime) datos1.get("fechaHoraOcurrencia");
            LocalDateTime fecha2 = (LocalDateTime) datos2.get("fechaHoraOcurrencia");

            return fecha2.compareTo(fecha1); // Retorna en orden descendente (más reciente primero)
        });
    }

    public void tomarSeleccionES(String eventoSelecc){
        for (Map<String, Object> diccEvento : listaESNoRevisados) { // Busca el evento correspondiente en la lista de eventos no revisados
            Map<String, Object> datos = (Map<String, Object>) diccEvento.get("datos");
            String datosStr = String.format( // Arma el texto del evento tal como se muestra en pantalla
                    "fechaHoraOcurrencia=%s, latitudEpicentro=%.2f, latitudHipocentro=%.2f, longitudEpicentro=%.2f, longitudHipocentro=%.2f, valorMagnitud=%.1f",
                    datos.get("fechaHoraOcurrencia"),
                    datos.get("latitudEpicentro"),
                    datos.get("latitudHipocentro"),
                    datos.get("longitudEpicentro"),
                    datos.get("longitudHipocentro"),
                    datos.get("valorMagnitud")
            );
            if (datosStr.equals(eventoSelecc)) { // Compara el texto armado con el que paso la pantalla
                // Si coinciden, guarda este evento como el seleccionado para revisar
                this.eventoSismicoSeleccionado = (EventoSismico) diccEvento.get("evento");
                break;
            }
        }
            this.buscarEstadoBloqueadoEnRev(); // Busca el estado a asignar

            this.tomarFechaHoraActual(); // Guarda la fecha y hora actual para registrar el cambio de estado

            this.buscarEmpleadoLogueado(); // Obtiene el empleado que está usando el sistema en este momento

            this.bloquearEventoSismico(); // Marca el evento como bloqueado para que nadie más lo modifique mientras se revisa

            this.buscarDatosEventoSismico(); // Extrae los datos del evento que necesita mostrar en pantalla

            // Muestra en la pantalla los datos principales del evento
            pantalla.mostrarDatosEventoSismico(this.nombreAlcance,this.nombreOrigenGeneracion,this.nombreClasificacion);

            this.buscarDatosSeriesTemporales(); // Busca los datos necesarios para generar la gráfica del sismograma

            this.llamarCUGenerarSismograma(); // instancia al caso de uso que genera el sismograma (CU GenerarSismograma)

            pantalla.habilitarOpcVerMapa();
            pantalla.habilitarOpcModificarDatosES();
            pantalla.pedirSeleccionResultadoEvento();
    }

    public void buscarEstadoBloqueadoEnRev(){
        for(Estado estado: listaEstados){ // Itera los estados
            // Verifica ámbito y nombre de los estados
            if(estado.esAmbitoEventoSismico() & estado.esBloqueadoEnRevision()){
                this.estadoBloqueadoEnRevision = estado; // Almacena el estado para asignarlo posteriormente
                break;
            }
        }
    }

    public void bloquearEventoSismico(){ // Bloquea el evento sismico seleccionado
        this.ultimoCambioDeEstado = this.eventoSismicoSeleccionado.revisar(fechaHoraActual, estadoBloqueadoEnRevision, empleadoLogueado);
        // Invoca el metodo para cambiar el estado del evento y almacena el cambio de estado que se crea
    }

    public void tomarFechaHoraActual(){
        this.fechaHoraActual = LocalDateTime.now();
    }

    public void buscarEmpleadoLogueado() {
        this.empleadoLogueado = this.sesion.obtenerEmpleadoLogueado();
    } // Obtiene el empleado logueado asociado a la sesion actual

    public void buscarDatosEventoSismico(){ // Obtiene los datos del evento sismico seleccionado para poder mostrarlos
        this.nombreAlcance = this.eventoSismicoSeleccionado.mostrarAlcance();
        this.nombreClasificacion = this.eventoSismicoSeleccionado.mostrarClasificacion();
        this.nombreOrigenGeneracion= this.eventoSismicoSeleccionado.mostrarOrigenGeneracion();
    }

    public void buscarEstadoRechazado(){
        for(Estado estado: listaEstados){ // Itera los estados
            // Verifica ámbito y nombre de los estados
            if(estado.esAmbitoEventoSismico() & estado.esRechazado()){
                this.estadoRechazado = estado;
                break;
            }
        }
    }

    public void buscarEstadoConfirmado(){
        for(Estado estado: listaEstados){ // Itera los estados
            // Verifica ámbito y nombre de los estados
            if(estado.esAmbitoEventoSismico() & estado.esConfirmado()){
                this.estadoConfirmado = estado; // Almacena el estado para asignarlo posteriormente
                break;
            }
        }
    }

    public void tomarSeleccionResultado(String seleccion){ //toma la acción a realizar y lleva a cabo el flujo correspondiente
        this.seleccionResultado = seleccion;
        if (this.validarDatosMinimos()){ //valida que se tengan los datos mínimos para poder registrar el resultado
            this.tomarFechaHoraActual();
            switch (seleccionResultado) {
                case "Rechazado": // Si se rechaza, busca al estado 'Rechazado' y rechaza al evento sismico, llamando a los metodos correspondientes
                    this.buscarEstadoRechazado();
                    this.rechazarEventoSismico();
                    break;
                case "Confirmado": // Si se confirma, busca al estado 'Confirmado' y confirma el evento sismico, llamando a los metodos correspondientes
                    this.buscarEstadoConfirmado();
                    this.confirmarEventoSismico();
                    break;
                case "Derivado a Experto":
                    // No se implementa
                    break;
                default:
                    throw new IllegalArgumentException("Resultado inválido: " + seleccionResultado); // Si el resultado seleccionado es invalido, lo informa
            }
            this.finCU();

        }else{
            // No se implementa
        }
    }

    public void rechazarEventoSismico(){ // Rechaza el evento sismico seleccionado
        this.eventoSismicoSeleccionado.rechazar(fechaHoraActual, estadoRechazado, empleadoLogueado,ultimoCambioDeEstado); // Llama al metodo rechazar()
    }

    public void confirmarEventoSismico(){ // Confirma el evento sismico seleccionado
        this.eventoSismicoSeleccionado.confirmar(fechaHoraActual, estadoConfirmado, empleadoLogueado,ultimoCambioDeEstado); // Llama al metodo confirmar()
        this.notificarConfirmacionAInteresados(); // se notifica a los interesados de la confirmación de un evento sísmico
    }

    public void notificarConfirmacionAInteresados(){
        // No se implementa
    }

    public void cancelarCU(){ // Se "destruye" el gestor dando valor nulo a todos sus atributos
        this.listaEventosSismicos=null;
        this.listaESNoRevisados=null;
        this.eventoSismicoSeleccionado=null;
        this.estadoRechazado=null;
        this.listaDatosSeriesTemporales=null;
        this.sesion=null;
        this.empleadoLogueado=null;
        this.listaEstados=null;
        this.estadoBloqueadoEnRevision=null;
        this.fechaHoraActual=null;
        this.pantalla=null;
        this.nombreAlcance=null;
        this.nombreOrigenGeneracion=null;
        this.nombreClasificacion=null;
        this.ultimoCambioDeEstado=null;
        this.sismografos=null;

    }

    public boolean validarDatosMinimos(){ // Valida que exista magnitud, alcance y origen de generación del evento y que se haya seleccionado una accion
        float magnitud = 0;
        for (Map<String, Object> diccEvento : listaESNoRevisados) {
            EventoSismico evento = (EventoSismico) diccEvento.get("evento");
            if (evento.equals(eventoSismicoSeleccionado)) {
                Map<String, Object> datos = (Map<String, Object>) diccEvento.get("datos");
                // Suponiendo que el valor es un float, lo casteamos
                magnitud = (float) datos.get("valorMagnitud");
                break;
            }
        }
        // Retorna 'true' si se validaron los datos minimos, o 'false' en caso contrario
        return (magnitud != 0 & this.nombreAlcance != null & this.nombreOrigenGeneracion != null & this.seleccionResultado != null);
    }

    public void buscarDatosSeriesTemporales(){
        this.listaDatosSeriesTemporales=eventoSismicoSeleccionado.buscarDatosSeriesTemporales(sismografos);
    } // solicita y almacena los datos de las series temporales del evento

    public void llamarCUGenerarSismograma(){
        // No se implementa
    }

    public void finCU(){ // Se "destruye" el gestor dando valor nulo a todos sus atributos
        this.listaEventosSismicos=null;
        this.listaESNoRevisados=null;
        this.eventoSismicoSeleccionado=null;
        this.estadoRechazado=null;
        this.listaDatosSeriesTemporales=null;
        this.sesion=null;
        this.empleadoLogueado=null;
        this.listaEstados=null;
        this.estadoBloqueadoEnRevision=null;
        this.fechaHoraActual=null;
        this.pantalla=null;
        this.nombreAlcance=null;
        this.nombreOrigenGeneracion=null;
        this.nombreClasificacion=null;
        this.ultimoCambioDeEstado=null;
        this.sismografos=null;
    }

    @Override
    public String toString() {
        return "GestorRegRRes{" +
                "listaEventosSismicos=" + listaEventosSismicos +
                ", listaESNoRevisados=" + listaESNoRevisados +
                ", eventoSismicoSeleccionado=" + eventoSismicoSeleccionado +
                ", estadoRechazado=" + estadoRechazado +
                ", listaDatosSeriesTemporales=" + listaDatosSeriesTemporales +
                ", sesion=" + sesion +
                ", empleadoLogueado=" + empleadoLogueado +
                ", listaEstados=" + listaEstados +
                ", estadoBloqueadoEnRevision=" + estadoBloqueadoEnRevision +
                ", fechaHoraActual=" + fechaHoraActual +
                ", pantalla=" + pantalla +
                ", nombreAlcance='" + nombreAlcance + '\'' +
                ", nombreOrigenGeneracion='" + nombreOrigenGeneracion + '\'' +
                ", nombreClasificacion='" + nombreClasificacion + '\'' +
                ", ultimoCambioDeEstado=" + ultimoCambioDeEstado +
                ", sismografos=" + sismografos +
                '}';
    } // Muestra el valor de los atributos del Gestor
}
