package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;


public class Partida {

    public static void main(String[] args){
        JFrame marco = new JFrame();
        marco.getContentPane().setBackground(Color.black);
        marco.setLayout(new GridBagLayout());
        marco.setMinimumSize(new Dimension(1000, 1000));
        marco.setLocationRelativeTo(null);

        Tablero tabla = new Tablero();
        marco.add(tabla);

        marco.setVisible(true);

        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
    }
    
}
