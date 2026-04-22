package presentation;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.io.File;

public class SokobanGUI extends JFrame {

    private JMenuBar menuBar;
    private JMenu opciones;
    private JMenuItem nuevo, abrir, salvar, salir;

    private JPanel panelTablero;
    private JLabel labelMovimientos;
    private JLabel labelIntentos;

    public SokobanGUI() {
        super("EasySokoban"); 
        prepareElements();
        prepareActions();
    }

    public static void main(String[] args) {
        SokobanGUI ventana = new SokobanGUI();
        ventana.setVisible(true);
    }

    public void prepareElements() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        
        this.setLayout(new BorderLayout());

        prepareElementsMenu(); //Menu
        prepareElementsBoard(); // Tablero
        prepareElementsEstado(); // Movimientos e intentos
    }

    /**
     * Define la zona del tablero de juego 
     */
    private void prepareElementsBoard() {
        panelTablero = new JPanel();

        this.add(panelTablero, BorderLayout.CENTER);
        
        refresh(); 
    }

    /**
     * Prepara la barra inferior con los contadores de movimientos e intentos
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
     * Actualiza la vista del tablero
     */
    public void refresh() {
        panelTablero.removeAll();
        
        int filas = 9;
        int columnas = 7;
        panelTablero.setLayout(new GridLayout(filas, columnas));
        
        for (int i = 0; i < (filas * columnas); i++) {
            JPanel celda = new JPanel();
            celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            if (i < columnas || i >= (filas * columnas) - columnas || i % columnas == 0 || i % columnas == columnas - 1) {
                celda.setBackground(Color.GRAY); 
            } else {
                celda.setBackground(Color.WHITE);
            }
            
            panelTablero.add(celda);
        }
        
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        opciones = new JMenu("Opciones");

        nuevo = new JMenuItem("Nuevo");
        abrir = new JMenuItem("Abrir");
        salvar = new JMenuItem("Salvar");
        salir = new JMenuItem("Salir");

        opciones.add(nuevo);
        opciones.addSeparator();
        opciones.add(abrir);
        opciones.addSeparator();
        opciones.add(salvar);
        opciones.addSeparator();
        opciones.add(salir);

        menuBar.add(opciones);
        this.setJMenuBar(menuBar);
    }

    public void prepareActions() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        prepareActionsMenu();
        prepareActionsPersistencia();
    }

    private void prepareActionsMenu() {
        salir.addActionListener(e -> exit());
    }

    private void exit() {
        int option = JOptionPane.showConfirmDialog(
            this, 
            "¿Desea cerrar la aplicación?", 
            "Confirmar salida", 
            JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

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
