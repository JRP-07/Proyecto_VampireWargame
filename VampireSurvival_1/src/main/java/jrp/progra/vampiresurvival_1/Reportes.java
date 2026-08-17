package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Reportes extends JPanel {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;

    JPanel panelContenido;
    CardLayout cardLayout;

    public Reportes(JFrame marco, GestorJugadores gestor, Jugador jugadorActual) {
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;

        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Reportes", SwingConstants.CENTER);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        this.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.black);

        JButton botonRanking = new JButton("Ranking de jugadores");
        botonRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenido, "ranking");
            }
        });

        JButton botonHistorial = new JButton("Mi historial");
        botonHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenido, "historial");
            }
        });

        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal();
            }
        });

        panelBotones.add(botonRanking);
        panelBotones.add(botonHistorial);
        panelBotones.add(botonVolver);
        this.add(panelBotones, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(Color.black);
        panelContenido.add(crearPanelRanking(), "ranking");
        panelContenido.add(crearPanelHistorial(), "historial");
        this.add(panelContenido, BorderLayout.CENTER);
    }

    private JPanel crearPanelRanking() {
        Jugador[] todos = gestor.obtenerJugadores();

        // El ranking solo muestra jugadores activos
        ArrayList<Jugador> soloActivos = new ArrayList<Jugador>();
        for (int i = 0; i < todos.length; i++) {
            if (todos[i].isActivo()) {
                soloActivos.add(todos[i]);
            }
        }

        Jugador[] jugadores = soloActivos.toArray(new Jugador[0]);

        // Ordenamos por puntos de mayor a menor con quicksort (recursivo)
        ordenarPorPuntos(jugadores, 0, jugadores.length - 1);

        String[] columnas = {"Posicion", "Usuario", "Puntos"};
        String[][] filas = new String[jugadores.length][3];

        for (int i = 0; i < jugadores.length; i++) {
            filas[i][0] = "" + (i + 1);
            filas[i][1] = jugadores[i].getUsuario();
            filas[i][2] = "" + jugadores[i].getPuntos();
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setEnabled(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.black);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // Funcion recursiva que ordena el arreglo de jugadores de mayor a menor puntaje.
    private void ordenarPorPuntos(Jugador[] arreglo, int inicio, int fin) {
        if (inicio >= fin) {
            return; 
        }

        int pivote = arreglo[fin].getPuntos();
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (arreglo[j].getPuntos() > pivote) {
                i = i + 1;
                Jugador temporal = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temporal;
            }
        }

        Jugador temporal = arreglo[i + 1];
        arreglo[i + 1] = arreglo[fin];
        arreglo[fin] = temporal;

        int posicionPivote = i + 1;

        ordenarPorPuntos(arreglo, inicio, posicionPivote - 1);
        ordenarPorPuntos(arreglo, posicionPivote + 1, fin);
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] historialCompleto = gestor.obtenerHistorial();

        JLabel etiqueta = new JLabel("Historial de " + jugadorActual.getUsuario() + " (del mas reciente al mas antiguo)");
        etiqueta.setForeground(Color.white);
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(etiqueta);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        boolean hayRegistros = false;

        // Se recorre del mas reciente al mas antiguo, mostrando solo los jugadores activos
        for (int i = historialCompleto.length - 1; i >= 0; i--) {
            String registro = historialCompleto[i];

            if (registro.startsWith(jugadorActual.getUsuario() + " - ")) {
                JLabel labelRegistro = new JLabel(registro);
                labelRegistro.setForeground(Color.lightGray);
                labelRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(labelRegistro);
                hayRegistros = true;
            }
        }

        if (!hayRegistros) {
            JLabel labelVacio = new JLabel("Todavia no tienes partidas registradas.");
            labelVacio.setForeground(Color.lightGray);
            labelVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(labelVacio);
        }

        JScrollPane scroll = new JScrollPane(panel);
        JPanel envoltorio = new JPanel(new BorderLayout());
        envoltorio.setBackground(Color.black);
        envoltorio.add(scroll, BorderLayout.CENTER);
        return envoltorio;
    }

    private void volverAlMenuPrincipal() {
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(menuPrincipal);

        marco.revalidate();
        marco.repaint();
    }
}
