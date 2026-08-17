package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicio extends JPanel {

    GestorJugadores gestor;

    JTextField campoUsuario;
    JPasswordField campoContraseña;
    JLabel labelMensaje;

    public MenuInicio(JFrame marco) {
        this(marco, new GestorJugadores());
    }

    public MenuInicio(JFrame marco, GestorJugadores gestor) {
        this.gestor = gestor;

        this.setBackground(Color.black);
        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(Color.black);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Vampire Survival");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setForeground(Color.white);
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoUsuario = new JTextField(15);
        campoUsuario.setMaximumSize(new Dimension(200, 30));
        campoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelContraseña = new JLabel("Contraseña (5 caracteres):");
        labelContraseña.setForeground(Color.white);
        labelContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoContraseña = new JPasswordField(15);
        campoContraseña.setMaximumSize(new Dimension(200, 30));
        campoContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelMensaje = new JLabel(" ");
        labelMensaje.setForeground(Color.red);
        labelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonIniciarSesion = new JButton("Iniciar sesion");
        botonIniciarSesion.setFont(new Font("Arial", Font.PLAIN, 18));
        botonIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion(); //se llama la funcion de iniciar sesion
            }
        });

        JButton botonCrearJugador = new JButton("Crear jugador");
        botonCrearJugador.setFont(new Font("Arial", Font.PLAIN, 18));
        botonCrearJugador.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCrearJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearJugador();
                //se llama la funcion de crear jugador
            }
        });

        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Arial", Font.PLAIN, 18));
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentro.add(labelUsuario);
        panelCentro.add(campoUsuario);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelContraseña);
        panelCentro.add(campoContraseña);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelMensaje);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonIniciarSesion);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCrearJugador);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonSalir);

        this.add(panelCentro);

        this.marco = marco;
    }

    JFrame marco;


    //Funcion de iniciar sesion dentro del menu de inicio
    private void iniciarSesion() {
        String usuario = campoUsuario.getText();
        String contraseña = new String(campoContraseña.getPassword());

        Jugador jugador = gestor.iniciarSesion(usuario, contraseña);

        if (jugador == null) {
            labelMensaje.setText("Usuario o contraseña incorrectos.");
        } else {
            labelMensaje.setText(" ");
            abrirMenuPrincipal(jugador); //se llama la funcion de abrir el menu con los datos del jugador
        }
    }

    //Funcion de crear jugador nuevo dentro del menu de inicio
    private void crearJugador() {
        String usuario = campoUsuario.getText();
        String contraseña = new String(campoContraseña.getPassword());

        String error = gestor.crearJugador(usuario, contraseña);

        if (error != null) {
            labelMensaje.setText(error);
        } else {
            labelMensaje.setText(" ");
            Jugador jugadorNuevo = gestor.buscarPorUsuario(usuario);
            abrirMenuPrincipal(jugadorNuevo);
        }
    }

    //Funcion que abre el menu principal del juego
    private void abrirMenuPrincipal(Jugador jugador) {
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, gestor, jugador);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(menuPrincipal);

        marco.revalidate();
        marco.repaint();
    }
}
