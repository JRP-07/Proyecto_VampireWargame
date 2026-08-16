package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;

public class MenuInicio extends JPanel {

    public MenuInicio(JFrame marco) {

        this.setBackground(Color.black);
        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.black);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Vampire Survival");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonJugar = new JButton("Jugar");
        botonJugar.setFont(new Font("Arial", Font.PLAIN, 20));
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);

        botonJugar.addActionListener(e -> {
            Tablero tablero = new Tablero();
            PanelInfo panelInfo = new PanelInfo(tablero);

            JPanel panelJuego = new JPanel();
            panelJuego.setBackground(Color.black);
            panelJuego.setLayout(new BorderLayout());
            panelJuego.add(tablero, BorderLayout.CENTER);
            panelJuego.add(panelInfo, BorderLayout.EAST);

            marco.getContentPane().removeAll();
            marco.setLayout(new GridBagLayout());
            marco.add(panelJuego);

            marco.revalidate();
            marco.repaint();
        });

        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Arial", Font.PLAIN, 20));
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.addActionListener(e -> System.exit(0));

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
        panelCentro.add(botonJugar);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonSalir);

        this.add(panelCentro);
    }
}
