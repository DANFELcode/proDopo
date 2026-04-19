package presentation;

import java.awt.*;
import javax.swing.*;

public class SokobanGUI extends JFrame{

    public SokobanGUI(){
        super("Sokoban");
               
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int width = (screenSize.width / 2);
        int heigth = (screenSize.height / 2);

        this.setSize(width, heigth);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){
        SokobanGUI ventana = new SokobanGUI();
        ventana.setVisible(true);
    }

}
