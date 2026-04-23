package presentation;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.io.File;

/**
 * SokobanGUI - Interfaz gráfica para el juego Sokoban
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class SokobanGUI extends JFrame {

    private JMenuBar menuBar;
    private JMenu opciones, configuración;
    private JMenuItem nuevo, abrir, salvar, salir;
    private JMenuItem colorMuros, colorSuelo, colorJugador, colorCaja;

    private JPanel panelTablero;
    private JLabel labelMovimientos, labelIntentos;

    private Color colorActualMuro = Color.GRAY;
    private Color colorActualSuelo = Color.WHITE;
    private Color colorActualJugador = Color.BLUE;
    private Color colorActualCaja = Color.BLACK;
    private final Color COLOR_DESTINO = Color.PINK;

    /**
     * Inicializa la ventana y sus componentes.
     */
    public SokobanGUI() {
        super("EasySokoban"); 
        prepareElements();
        prepareActions();
    }

    /**
     * Punto de entrada principal de la aplicación.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SokobanGUI ventana = new SokobanGUI();
        ventana.setVisible(true);
    }

    /**
     * Configura los parámetros iniciales de la ventana
     */
    public void prepareElements() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2); 
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        prepareElementsMenu(); 
        prepareElementsBoard(); 
        prepareElementsEstado();
    }

    /**
     * Prepara el panel central donde se ubicará el tablero de juego.
     */
    private void prepareElementsBoard() {
        panelTablero = new JPanel();
        this.add(panelTablero, BorderLayout.CENTER);
        refresh(); 
    }

    /**
     * Prepara el panel de estado inferior con los contadores de movimientos e intentos.
     */
    private void prepareElementsEstado() {
        JPanel panelEstado = new JPanel(new GridLayout(1, 2));
        labelMovimientos = new JLabel(" Movimientos: 0");
        labelIntentos = new JLabel("Intentos: 0 ", SwingConstants.RIGHT);
        
        labelMovimientos.setFont(new Font("Arial", Font.BOLD, 14));
        labelIntentos.setFont(new Font("Arial", Font.BOLD, 14));

        panelEstado.add(labelMovimientos);
        panelEstado.add(labelIntentos);
        this.add(panelEstado, BorderLayout.SOUTH);
    }

    /**
     * Redibuja el tablero de juego, actualizando los colores y componentes visuales.
     */
    public void refresh() {
        panelTablero.removeAll();
        int filas = 9;
        int columnas = 7;
        panelTablero.setLayout(new GridLayout(filas, columnas));
        
        for (int i = 0; i < (filas * columnas); i++) {
            JPanel celda = new JPanel();
            celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            if (i == 24) { 
                celda.setBackground(colorActualJugador);
            } else if (i == 32) { 
                celda.setBackground(colorActualCaja);
            } else if (i == 38) { 
                celda.setBackground(COLOR_DESTINO); 
            } else if (i < columnas || i >= (filas * columnas) - columnas || i % columnas == 0 || i % columnas == columnas - 1) {
                celda.setBackground(colorActualMuro); 
            } else {
                celda.setBackground(colorActualSuelo);
            }
            panelTablero.add(celda);
        }
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    /**
     * Prepara la barra de menús
     */
    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        opciones = new JMenu("Opciones");
        configuración = new JMenu("Configuración");

        nuevo = new JMenuItem("Nuevo");
        abrir = new JMenuItem("Abrir");
        salvar = new JMenuItem("Salvar");
        salir = new JMenuItem("Salir");
        
        colorMuros = new JMenuItem("Color de muros");
        colorSuelo = new JMenuItem("Color de suelo");
        colorJugador = new JMenuItem("Color de personaje");
        colorCaja = new JMenuItem("Color de caja");

        opciones.add(nuevo);
        opciones.addSeparator(); 
        opciones.add(abrir);
        opciones.addSeparator();
        opciones.add(salvar);
        opciones.addSeparator();
        opciones.add(salir);

        configuración.add(colorMuros);
        configuración.add(colorSuelo);
        configuración.add(colorJugador);
        configuración.add(colorCaja);

        menuBar.add(opciones);
        menuBar.add(configuración);
        this.setJMenuBar(menuBar);
    }

    /**
     * Define el comportamiento de cierre de la ventana y enlaza las acciones de los menús.
     */
    public void prepareActions() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { exit(); }
        });
        prepareActionsMenu(); 
        prepareActionsPersistencia();
    }

    /**
     * Asigna los listeners para las opciones de salir y personalización de colores.
     */
    private void prepareActionsMenu() {
        salir.addActionListener(e -> exit());

        colorMuros.addActionListener(e -> cambiarColor("muros", 1));
        colorSuelo.addActionListener(e -> cambiarColor("suelo", 2));
        colorJugador.addActionListener(e -> cambiarColor("personaje", 3));
        colorCaja.addActionListener(e -> cambiarColor("caja", 4));
    }

    /**
     * Despliega un JColorChooser para modificar el color de los elementos del juego.
     * @param tipo Nombre del elemento a cambiar.
     * @param opcion Índice del elemento para asignar el nuevo color.
     */
    private void cambiarColor(String tipo, int opcion) {
        Color inicial = Color.WHITE;
        if(opcion == 1) inicial = colorActualMuro;
        if(opcion == 2) inicial = colorActualSuelo;
        if(opcion == 3) inicial = colorActualJugador;
        if(opcion == 4) inicial = colorActualCaja;

        Color nuevoColor = JColorChooser.showDialog(this, "Seleccione color para: " + tipo, inicial); 
        if (nuevoColor != null) {
            if(opcion == 1) colorActualMuro = nuevoColor;
            if(opcion == 2) colorActualSuelo = nuevoColor;
            if(opcion == 3) colorActualJugador = nuevoColor;
            if(opcion == 4) colorActualCaja = nuevoColor;
            refresh(); 
        }
    }

    /**
     * Muestra un diálogo de confirmación antes de cerrar la aplicación.
     */
    private void exit() {
        int option = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la aplicación?", "Confirmar salida", JOptionPane.YES_NO_OPTION); 
        if (option == JOptionPane.YES_OPTION) System.exit(0);
    }

    /**
     * Configura las acciones para los diálogos de apertura y guardado de archivos.
     */
    private void prepareActionsPersistencia() {
        abrir.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(); 
            int result = fileChooser.showOpenDialog(this); 
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile(); 
                JOptionPane.showMessageDialog(this, "Funcion Abrir aun en construcción.\n" + "Archivo seleccionado: " + selectedFile.getName()); 
            }
        });

        salvar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(); 
            int result = fileChooser.showSaveDialog(this); 
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile(); 
                JOptionPane.showMessageDialog(this, "Funcion Salvar aun en construcción.\n" + "Archivo guardado: " + selectedFile.getName());
            }
        });
    }
}
