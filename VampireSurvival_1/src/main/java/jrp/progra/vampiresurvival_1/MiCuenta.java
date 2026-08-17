package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class MiCuenta extends Fondo {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;

    JLabel labelMensaje;

    public MiCuenta(JFrame marco, GestorJugadores gestor, Jugador jugadorActual) {
        super("fondo_4.jpg");
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;

        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Mi cuenta");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        JLabel labelUsuario = new JLabel("Usuario: " + jugadorActual.getUsuario());
        labelUsuario.setForeground(Color.white);
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelPuntos = new JLabel("Puntos: " + jugadorActual.getPuntos());
        labelPuntos.setForeground(Color.white);
        labelPuntos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelFecha = new JLabel("Fecha de ingreso: " + formato.format(jugadorActual.getFechaIngreso()));
        labelFecha.setForeground(Color.white);
        labelFecha.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelMensaje = new JLabel(" ");
        labelMensaje.setForeground(Color.red);
        labelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonCambiarContraseña = new JButton("Cambiar contraseña");
        botonCambiarContraseña.setFont(new Font("Arial", Font.PLAIN, 16));
        botonCambiarContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCambiarContraseña.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarContraseña(); //se llama la funcion para cambiar la contraseña
            }
        });

        JButton botonCerrarCuenta = new JButton("Cerrar mi cuenta");
        botonCerrarCuenta.setFont(new Font("Arial", Font.PLAIN, 16));
        botonCerrarCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCerrarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarCuenta(); //se llama la funcion para desactivar la cuenta
            }
        });

        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Arial", Font.PLAIN, 16));
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal(); //se llama la funcion para volver al menu principal
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentro.add(labelUsuario);
        panelCentro.add(labelPuntos);
        panelCentro.add(labelFecha);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelMensaje);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCambiarContraseña);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCerrarCuenta);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonVolver);

        this.add(panelCentro);
    }

    private void cambiarContraseña() {
        String nueva = JOptionPane.showInputDialog(this, "Escribe la nueva contraseña (5 caracteres):");

        if (nueva == null) {
            return; // el jugador le dio cancelar
        }

        if (nueva.length() != 5) {
            labelMensaje.setText("La contraseña debe tener exactamente 5 caracteres.");
            return;
        }

        jugadorActual.setContraseña(nueva);
        labelMensaje.setForeground(Color.green);
        labelMensaje.setText("Contraseña actualizada.");
    }

    //Funcion para desactivar la cuenta del usuario
    private void cerrarCuenta() {
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que quieres cerrar tu cuenta? No vas a poder iniciar sesion de nuevo.",
            "Cerrar cuenta",
            JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            jugadorActual.setActivo(false);

            MenuInicio menuInicio = new MenuInicio(marco, gestor);
            marco.getContentPane().removeAll();
            marco.setLayout(new BorderLayout());
            marco.add(menuInicio, BorderLayout.CENTER);
            marco.revalidate();
            marco.repaint();
        }
    }

    //Funcion que regrese al menu principal
    private void volverAlMenuPrincipal() {
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuPrincipal, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }
}
