package presentation;

import domain.Sokoban;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

/**
 * SokobanGUI - Interfaz gráfica para el juego Sokoban
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class SokobanGUI extends JFrame {

    private Sokoban juego;

    private JMenuBar menuBar;
    private JMenu opciones, configuración;
    private JMenuItem nuevo, abrir, salvar, salir, reiniciar, cambiarTamaño; // ciclo 8
    private JMenuItem colorMuros, colorSuelo, colorJugador, colorCaja;

    private JPanel panelTablero;
    private JLabel labelMovimientos, labelIntentos;

    private Color colorActualMuro = new Color(155, 145, 90);
    private Color colorActualSuelo = new Color(225, 215, 175); 
    private Color colorActualJugador = Color.BLUE;
    private Color colorActualCaja = Color.ORANGE;
    private final Color COLOR_DESTINO = Color.PINK;
    private Color colorCajaEnDestino = new Color(139, 69, 19);   
    
    private int movimientosContador = 0;

    /**
     * Inicializa la ventana y sus componentes.
     */
    public SokobanGUI() {
        super("EasySokoban");
        juego = new Sokoban(9, 7);
        juego.generate();
        
        prepareElements();
        prepareActions();
    }

    /**
     * Punto de entrada principal de la aplicación.
     */
    public static void main(String[] args) {
        SokobanGUI ventana = new SokobanGUI();
        ventana.setVisible(true);
    }

    /**
     * Configura los parámetros iniciales de la ventana.
     */
    public void prepareElements() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2); 
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        labelMovimientos = new JLabel(" Movimientos: 0");
        labelIntentos = new JLabel("Cajas en Destino: 0 ", SwingConstants.RIGHT);
        labelMovimientos.setFont(new Font("Arial", Font.BOLD, 14));
        labelIntentos.setFont(new Font("Arial", Font.BOLD, 14));

        prepareElementsMenu(); 
        prepareElementsBoard(); 
        prepareElementsEstado();
    }

    /**
     * Prepara el panel central 
     */
    private void prepareElementsBoard() {
        panelTablero = new JPanel();
        this.add(panelTablero, BorderLayout.CENTER);
        refresh(); 
    }

    /**
     * Agrega los labels al panel de estado inferior.
     */
    private void prepareElementsEstado() {
        JPanel panelEstado = new JPanel(new GridLayout(1, 2));
        panelEstado.add(labelMovimientos);
        panelEstado.add(labelIntentos);
        this.add(panelEstado, BorderLayout.SOUTH);
    }

    /**
     * Redibuja el tablero de juego
     */
    public void refresh() {
        if (panelTablero == null) return;

        panelTablero.removeAll();
        char[][] board = juego.board();
        int filas = board.length;
        int columnas = board[0].length;
        
        panelTablero.setLayout(new GridLayout(filas, columnas));
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JPanel celda = new JPanel();
                celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                char tipo = board[i][j];

                switch (tipo) {
                    case 'w': 
                        celda.setBackground(colorActualMuro); 
                        break;
                    case 'p': 
                    case 'P': 
                        celda.setBackground(colorActualJugador); 
                        break;
                    case 'b': 
                        celda.setBackground(colorActualCaja); 
                        break;
                    case 'B': 
                        celda.setBackground(colorCajaEnDestino); 
                        break;
                    case 'd': 
                        celda.setBackground(COLOR_DESTINO); 
                        break;
                    default:
                        celda.setBackground(colorActualSuelo); 
                        break;
                }
                panelTablero.add(celda);
            }
        }
        
        labelMovimientos.setText(" Movimientos: " + movimientosContador);
        labelIntentos.setText("Cajas en Destino: " + juego.score() + " ");
        
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    /**
     * Prepara los elementos del menu
     */
    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        opciones = new JMenu("Opciones");
        configuración = new JMenu("Configuración");

        nuevo = new JMenuItem("Nuevo");
        abrir = new JMenuItem("Abrir");
        salvar = new JMenuItem("Salvar");
        salir = new JMenuItem("Salir");
        reiniciar = new JMenuItem("Reiniciar");
        cambiarTamaño = new JMenuItem("Cambiar tamaño"); // ciclo 8    
            
        
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
        opciones.addSeparator();
        opciones.add(reiniciar);

        configuración.add(colorMuros);
        configuración.add(colorSuelo);
        configuración.add(colorJugador);
        configuración.add(colorCaja);
        configuración.add(cambiarTamaño); //ciclo 8

        menuBar.add(opciones);
        menuBar.add(configuración);
        this.setJMenuBar(menuBar);

    }

    /**
     * Configura las acciones para los elementos de la interfaz
     */
    public void prepareActions() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { exit(); }
        });
        
        prepareActionsMenu(); 
        prepareActionsPersistencia();
        prepareActionsTeclado();
    }

    /**
     * Configura las acciones para los elementos del menu
     */
    private void prepareActionsMenu() {
        salir.addActionListener(e -> exit());
        
        nuevo.addActionListener(e -> {
            movimientosContador = 0;
            juego.generate();
            refresh();
        });

        reiniciar.addActionListener(e -> {
            juego.restart();           
            movimientosContador = 0;   
            refresh();                 
        });

        // ciclo 8
        cambiarTamaño.addActionListener(e -> {
            String inputHeight = JOptionPane.showInputDialog(this, "Numero de filas?: ", "cambiar tamaño", JOptionPane.QUESTION_MESSAGE);
            String inputWidth = JOptionPane.showInputDialog(this, "Numero de columnas?: ", "cambiar tamaño", JOptionPane.QUESTION_MESSAGE);
            if (inputHeight != null && inputWidth != null) {
                int h = Integer.parseInt(inputHeight);
                int w = Integer.parseInt(inputWidth);
                movimientosContador = 0;
                juego.modifySizeBoard(h, w);
                refresh();
            }
        });

        colorMuros.addActionListener(e -> cambiarColor("muros", 1));
        colorSuelo.addActionListener(e -> cambiarColor("suelo", 2));
        colorJugador.addActionListener(e -> cambiarColor("personaje", 3));
        colorCaja.addActionListener(e -> cambiarColor("caja", 4));
    }

    /**
     * Configura las acciones para el teclado
     */
    private void prepareActionsTeclado() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                boolean movio = true;

                switch (code) {
                    case KeyEvent.VK_UP:    juego.move('u'); break;
                    case KeyEvent.VK_DOWN:  juego.move('d'); break;
                    case KeyEvent.VK_LEFT:  juego.move('l'); break;
                    case KeyEvent.VK_RIGHT: juego.move('r'); break;
                    default: movio = false; break;
                }

                if (movio) {
                    movimientosContador++;
                    refresh();
                    if (juego.playerWin()) {
                        JOptionPane.showMessageDialog(SokobanGUI.this, "Felicidades, ganaste en " + movimientosContador + " movimientos");
                    }
                }
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow(); 
    }

    /**
     * Permite cambiar el color de un elemento del tablero
     * @param tipo El tipo de elemento a cambiar (muros, suelo, personaje, caja)
     * @param opcion Un número que indica qué color se está cambiando (1=muro, 2=suelo, 3=jugador, 4=caja)
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
     * Muestra un diálogo de confirmación antes de cerrar
     */
    private void exit() {
        int option = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la aplicación?", "Confirmar salida", JOptionPane.YES_NO_OPTION); 
        if (option == JOptionPane.YES_OPTION) System.exit(0);
    }

    /**
     * Prepara las acciones para la persistencia de datos
     */
    private void prepareActionsPersistencia() {
        abrir.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(); 
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile(); 
                JOptionPane.showMessageDialog(this, "Funcion Abrir aun en construcción.\nArchivo seleccionado: " + selectedFile.getName()); 
            }
        });

        salvar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(); 
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile(); 
                JOptionPane.showMessageDialog(this, "Funcion Salvar aun en construcción.\nArchivo guardado: " + selectedFile.getName());
            }
        });
    }
}
