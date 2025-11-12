package org.example.tpids2025g1demo1.controllers;

import org.example.tpids2025g1demo1.services.GestorRegRRes;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@RestController
@RequestMapping("/reg-resultado-revision")
public class PantRegRRes extends JFrame {
    private GestorRegRRes gestor;
    private JButton opcRegRRevision;
    private JComboBox<String> eventosComboBox;
    private JTextField origentxt;
    private JTextField alcancetxt;
    private JTextField clasificaciontxt;
    private JButton seleccionarButton;
    private JLabel alcanceLabel;
    private JLabel clasificacionLabel;
    private JLabel origenDeGeneracionLabel;
    private JButton rechazarButton;
    private JButton confirmarButton;
    private JButton delegarAExpertoButton;
    private JButton verMapaButton;
    private JButton modificarDatosButton;
    private JLabel seleccioneResultadoLabel;
    private JPanel panelPrincipal;

    public PantRegRRes(GestorRegRRes gestor) {
        this.gestor = gestor;
        setContentPane(panelPrincipal); // Establece el panel principal como el contenido de la ventana
        setTitle("Registrar Resultado de manual"); // Asigna el título a la ventana
        setSize(1000,600); // Configura el tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Define el comportamiento de cierre
        setLocationRelativeTo(null); // Indica respecto a que se centre, al poner null es respecto al centro.
        setExtendedState(JFrame.NORMAL); // Define el estado extendido de la ventana
        setVisible(true); // Hace visible la ventana

        // Inicia ocultando estos componentes
        eventosComboBox.setVisible(false);
        origentxt.setVisible(false);
        alcancetxt.setVisible(false);
        clasificaciontxt.setVisible(false);
        seleccionarButton.setVisible(false);
        alcanceLabel.setVisible(false);
        clasificacionLabel.setVisible(false);
        origenDeGeneracionLabel.setVisible(false);
        rechazarButton.setVisible(false);
        confirmarButton.setVisible(false);
        delegarAExpertoButton.setVisible(false);
        verMapaButton.setVisible(false);
        modificarDatosButton.setVisible(false);
        seleccioneResultadoLabel.setVisible(false);

        //Modifica el evento de cierre de la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            // Sobrescribimos el método windowClosing que se ejecuta cuando el usuario cierra una ventana
            public void windowClosing(java.awt.event.WindowEvent e) {
                tomarCancelacion(); // Al cerrar le ventana se cancela la ejecución del CU
            }});
        // Al presionar el botón de Registrar Resultado Revisión, se ejecuta opcRegResultadoES() comenzando la ejecución del CU
        opcRegRRevision.addActionListener(e -> {
            opcRegResultadoES();
        });

        // Agrega ActionListener para tomar la selección de un evento
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) eventosComboBox.getSelectedItem(); // Obtenemos el elemento seleccionado y lo casteamos a String
                // Verifica que se haya seleccionado un elemento
                if (seleccionado != null) {
                    tomarSeleccionES(seleccionado);
                } else {
                    // Si no se selecciono ningun evento, se muestra un mensaje
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un evento antes de continuar.");}
            }
        });

        //ActionListeners para responder a la interacción con los botones que representan las distintas opciones

        rechazarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarSeleccionResultado("Rechazado");
            }
        });
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarSeleccionResultado("Confirmado");
            }
        });
        delegarAExpertoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarSeleccionResultado("Derivado a Experto");
            }
        });
        modificarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarSolicitudModificacionDatos();
            }
        });
        verMapaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarSolicitudVerMapa();
            }
        });
    }

    // Inicia el registro del resultado al seleccionarse la opción correspondiente
    @GetMapping("/norevisados")
     public void opcRegResultadoES(){
        this.abrirPantalla();
        gestor.nuevaRevisionES(this); // Se pasa así mismo como parámetro para que el gestor puede luego invocar su comportamiento
    }

    // "Abre" la pantalla para registrar el resultado de la revisión
    public void abrirPantalla(){
        //se oculta el botón para iniciar el CU y se muestran los demás componentes
        this.opcRegRRevision.setVisible(false);
        this.eventosComboBox.setVisible(true);
        this.origentxt.setVisible(true);
        this.alcancetxt.setVisible(true);
        this.clasificaciontxt.setVisible(true);
        this.seleccionarButton.setVisible(true);
        this.alcanceLabel.setVisible(true);
        this.clasificacionLabel.setVisible(true);
        this.origenDeGeneracionLabel.setVisible(true);
        this.verMapaButton.setVisible(true);
        this.modificarDatosButton.setVisible(true);
        this.confirmarButton.setVisible(true);
        this.rechazarButton.setVisible(true);
        this.delegarAExpertoButton.setVisible(true);
        this.seleccioneResultadoLabel.setVisible(true);
    }

    // Carga y muestra en el comboBox los eventos sísmicos
    public void mostrarESParaSeleccion(ArrayList<String> eventosSismicos){

        // Crea un modelo para el comboBox
    DefaultComboBoxModel<String> eventos = new DefaultComboBoxModel<>();
        for (String evento: eventosSismicos) { // Se recorren los eventos de la lista
            eventos.addElement(evento); // Agrega el evento al modelo
        }
        eventosComboBox.setModel(eventos); // Asigna el modelo al comboBox
    }

    // Pasa el evento sísmico seleccionado al gestor
    @PostMapping("/seleccion-evento")
    public void tomarSeleccionES(String evento){
        gestor.tomarSeleccionES(evento);
    }

    // Se muestran los datos principales del evento seleccionado
    public void mostrarDatosEventoSismico(String nombreAlcance, String nombreOrigenGeneracion, String nombreClasificacion){
        this.alcancetxt.setText(nombreAlcance);
        this.origentxt.setText(nombreOrigenGeneracion);
        this.clasificaciontxt.setText(nombreClasificacion);
    }

    // Habilita el botón que permite la opción de ver mapa
    public void habilitarOpcVerMapa(){
        this.verMapaButton.setEnabled(true);
    }

    // Habilita el botón que permite la opción de modificar datos
    public void habilitarOpcModificarDatosES(){
        this.modificarDatosButton.setEnabled(true);
    }

    // Habilita los botones para la selección del resultado
    public void pedirSeleccionResultadoEvento(){
        this.seleccioneResultadoLabel.setEnabled(true);
        this.rechazarButton.setEnabled(true);
        this.confirmarButton.setEnabled(true);
        this.delegarAExpertoButton.setEnabled(true);
    }

    // Cierra la ventana y cancela la ejecución del CU
    @PostMapping("/cancelacion")
    public void tomarCancelacion(){
        dispose();
        gestor.cancelarCU();
    }

    // Informa al usuario la situación con un mensaje en pantalla
    public void informarNoHayESNoRevisados(){
        JOptionPane.showMessageDialog(this, "No hay sismos auto detectados que aún no han sido revisados");
        this.dispose(); // Cierra la ventana
    }
    @PostMapping("/seleccion-resultado")
    // Toma la elección del resultado y la pasa al gestor
    public void tomarSeleccionResultado(String seleccion){
        JOptionPane.showMessageDialog(this,"Resultado Registrado!"); // Muestra un mensaje
        dispose(); // Cierra la ventana
        gestor.tomarSeleccionResultado(seleccion);
    }

    // Responde a la solicitud de moficación de datos
    @PutMapping("/datos-evento")
    public void tomarSolicitudModificacionDatos(){
        JOptionPane.showMessageDialog(this,"Cargando..."); // Muestra un mensaje
    }

    // Responde a la solicitud de ver mapa
    @PostMapping("/visualizar-mapa")
    public void tomarSolicitudVerMapa(){
        JOptionPane.showMessageDialog(this,"Cargando mapa..."); // Muestra un mensaje
    }
}