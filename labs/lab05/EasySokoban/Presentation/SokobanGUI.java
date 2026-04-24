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
    private JMenu opciones;
    private JMenuItem salir, colorMuro, colorSuelo, colorCaja, abrir, salvar;
    private JPanel panelTablero;
    private JLabel labelMovimientos;
    private JLabel labelIntentos;

    private Color colorActualMuro = Color.DARK_GRAY;
    private Color colorActualSuelo = Color.LIGHT_GRAY;
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
        labelIntentos = new JLabel(" Intentos: 0");
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

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JPanel celda = new JPanel();
                celda.setBackground(colorActualSuelo);
                panelTablero.add(celda);
            }
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

        salir = new JMenuItem("Salir");
        colorMuro = new JMenuItem("Color Muro");
        colorSuelo = new JMenuItem("Color Suelo");
        colorCaja = new JMenuItem("Color Caja");
        abrir = new JMenuItem("Abrir");
        salvar = new JMenuItem("Salvar");

        opciones.add(abrir);
        opciones.add(salvar);
        opciones.addSeparator();
        opciones.add(colorMuro);
        opciones.add(colorSuelo);
        opciones.add(colorCaja);
        opciones.addSeparator();
        opciones.add(salir);

        menuBar.add(opciones);
        this.setJMenuBar(menuBar);
    }

    /**
     * Define el comportamiento de cierre de la ventana y enlaza las acciones de los menús.
     */
    public void prepareActions() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        prepareActionsMenu();
        prepareActionsPersistencia();
    }

    /**
     * Asigna los listeners para las opciones de salir y personalización de colores.
     */
    private void prepareActionsMenu() {
        salir.addActionListener(e -> exit());

        colorMuro.addActionListener(e -> cambiarColor("muro", 1));
        colorSuelo.addActionListener(e -> cambiarColor("suelo", 2));
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
        else if(opcion == 2) inicial = colorActualSuelo;
        else if(opcion == 4) inicial = colorActualCaja;

        Color nuevo = JColorChooser.showDialog(this, "Seleccionar color para " + tipo, inicial);
        if (nuevo != null) {
            if(opcion == 1) colorActualMuro = nuevo;
            else if(opcion == 2) colorActualSuelo = nuevo;
            else if(opcion == 4) colorActualCaja = nuevo;
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
