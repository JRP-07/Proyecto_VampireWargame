package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JPanel {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;

    public MenuPrincipal(JFrame marco, GestorJugadores gestor, Jugador jugadorActual) {
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;

        this.setBackground(Color.black);
        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.black);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu Principal");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelBienvenida = new JLabel("Bienvenido, " + jugadorActual.getUsuario());
        labelBienvenida.setForeground(Color.lightGray);
        labelBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonJugar = new JButton("Jugar Vampire Wargame");
        botonJugar.setFont(new Font("Arial", Font.PLAIN, 18));
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonJugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirSeleccionOponente();
            }
        });

        JButton botonMiCuenta = new JButton("Mi cuenta");
        botonMiCuenta.setFont(new Font("Arial", Font.PLAIN, 18));
        botonMiCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMiCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMiCuenta();
            }
        });

        JButton botonReportes = new JButton("Reportes");
        botonReportes.setFont(new Font("Arial", Font.PLAIN, 18));
        botonReportes.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonReportes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReportes();
            }
        });

        JButton botonCerrarSesion = new JButton("Cerrar sesion");
        botonCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 18));
        botonCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelBienvenida);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
        panelCentro.add(botonJugar);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonMiCuenta);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonReportes);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCerrarSesion);

        this.add(panelCentro);
    }

    private void abrirSeleccionOponente() {
        SeleccionOponente seleccion = new SeleccionOponente(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(seleccion);

        marco.revalidate();
        marco.repaint();
    }

    private void abrirMiCuenta() {
        MiCuenta miCuenta = new MiCuenta(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(miCuenta);

        marco.revalidate();
        marco.repaint();
    }

    private void abrirReportes() {
        Reportes reportes = new Reportes(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(reportes);

        marco.revalidate();
        marco.repaint();
    }

    private void cerrarSesion() {
        MenuInicio menuInicio = new MenuInicio(marco, gestor);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(menuInicio);

        marco.revalidate();
        marco.repaint();
    }
}
