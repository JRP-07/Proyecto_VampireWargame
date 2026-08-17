package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.awt.event.*;
import java.util.*;

import jrp.progra.piezas.Piezas;

public class PanelInfo extends JPanel {

    Tablero tablero;
    int anchoIcono = 40;

    Ruleta_1 ruleta;
    JButton botonGirar;
    JButton botonRetirarse;
    JLabel labelTurno;
    JLabel labelInstruccion;

    public PanelInfo(Tablero tablero) {
        this.tablero = tablero;
        this.setPreferredSize(new Dimension(260, tablero.tCasillas * tablero.fil));
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());

        this.add(crearPanelRuleta(), BorderLayout.NORTH);
        this.add(crearPanelDatos(), BorderLayout.CENTER);

        // Repintar y actualizar el estado de turno cada cierto tiempo
        Timer temporizador = new Timer(200, e -> {
            repaint();
            actualizarEstadoTurno();
        });
        temporizador.start();
    }


    //Funcion que se encarga de crear el panel donde va la ruleta
    private JPanel crearPanelRuleta() {
        JPanel panelRuleta = new JPanel();
        panelRuleta.setBackground(Color.darkGray);
        panelRuleta.setLayout(new BoxLayout(panelRuleta, BoxLayout.Y_AXIS));

        labelTurno = new JLabel("Turno: Blanco");
        labelTurno.setForeground(Color.white);
        labelTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRuleta.add(labelTurno);

        labelInstruccion = new JLabel("Gira la ruleta");
        labelInstruccion.setForeground(Color.yellow);
        labelInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRuleta.add(labelInstruccion);

        //Llenado de la lista de la ruleta
        List<String> opciones = Arrays.asList("Vampiro", "Vampiro", "Hombre \nLobo", "Hombre \nLobo", "Muerte", "Bruja");

        ruleta = new Ruleta_1(opciones, new Runnable() {
            public void run() {
                alTerminarGiro();
            }
        });

        ruleta.setPreferredSize(new Dimension(220, 220));
        ruleta.setMaximumSize(new Dimension(220, 220));
        ruleta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRuleta.add(ruleta);

        botonGirar = new JButton("Girar");
        botonGirar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonGirar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ruleta.girar(); //Llamado a la funcion que se encarga de girar la ruleta
            }
        });
        panelRuleta.add(botonGirar);

        botonRetirarse = new JButton("Retirarse");
        botonRetirarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRetirarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarRetiro(); //LLamado a la funcion para rendirse
            }
        });
        panelRuleta.add(botonRetirarse);

        JButton botonMenu = new JButton("Menu Principal");
        botonMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal(); //Llamado a la funcion para volver al menu
            }
        });
        panelRuleta.add(botonMenu);

        return panelRuleta;
    }

    private void volverAlMenuPrincipal() {
        if (!tablero.partidaTerminada) {
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres salir? Se perdera el progreso de la partida actual.",
                "Volver al menu principal",
                JOptionPane.YES_NO_OPTION
            );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        Window ventana = SwingUtilities.getWindowAncestor(this);
        if (!(ventana instanceof JFrame) || tablero.jugadorBlanco == null || tablero.gestor == null) {
            return;
        }

        JFrame marco = (JFrame) ventana;
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, tablero.gestor, tablero.jugadorBlanco);

        marco.getContentPane().removeAll();
        marco.setLayout(new GridBagLayout());
        marco.add(menuPrincipal);

        marco.revalidate();
        marco.repaint();
    }

    //Funcion que se encarga de verificar si el jugador se rinde
    private void confirmarRetiro() {
        String colorEnTurno = tablero.turnoBlanco ? "Blanco" : "Negro";

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que el jugador " + colorEnTurno + " se quiere retirar? El otro jugador ganara la partida.",
            "Retirarse de la partida",
            JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            tablero.retirarse();
            actualizarEstadoTurno();
        }
    }

    //Funcion que crea el panel de datos de las piezas y las capturas
    private JPanel crearPanelDatos() {
        JPanel panelDatos = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarCapturadasYDatos((Graphics2D) g);
            }
        };
        panelDatos.setBackground(Color.darkGray);
        return panelDatos;
    }

    private void alTerminarGiro() {
        tablero.establecerResultadoRuleta(ruleta.getResultadoIndice());
        actualizarEstadoTurno();
    }

    private void actualizarEstadoTurno() {
        if (tablero.partidaTerminada) {
            labelTurno.setText("Partida terminada");
            labelInstruccion.setText("");
            botonGirar.setEnabled(false);
            botonRetirarse.setEnabled(false);
            return;
        }

        labelTurno.setText("Turno: " + (tablero.turnoBlanco ? "Blanco" : "Negro"));

        if (tablero.piezaPermitida != null) {
            labelInstruccion.setText("Mover: " + tablero.piezaPermitida.getAlias());
        } else {
            labelInstruccion.setText("Gira la ruleta");
        }

        botonGirar.setEnabled(tablero.piezaPermitida == null);
    }

    private void dibujarCapturadasYDatos(Graphics2D g2d) {
        g2d.setColor(Color.white);

        int y = 20;

        if (tablero.mensajeFinal != null) {
            y = dibujarTextoConWrap(g2d, tablero.mensajeFinal, y, Color.yellow);
            y += 20;
        }

        g2d.drawString("Capturadas - Blanco", 10, y);
        y += 10;
        y = dibujarCapturadas(g2d, tablero.capturadasBlancas, y);

        y += 30;
        g2d.drawString("Capturadas - Negro", 10, y);
        y += 10;
        y = dibujarCapturadas(g2d, tablero.capturadasNegras, y);

        y += 30;
        g2d.drawString("Datos de la pieza", 10, y);
        y += 20;
        y = dibujarDatos(g2d, y);

        if (tablero.ultimoMensaje != null) {
            y += 30;
            g2d.drawString("Ultimo ataque", 10, y);
            y += 20;
            dibujarTextoConWrap(g2d, tablero.ultimoMensaje, y, Color.orange);
        }
    }

    // Dibuja un texto largo partido en varias lineas para que no se salga
    // del panel (que es angosto), en el color indicado.
    private int dibujarTextoConWrap(Graphics2D g2d, String texto, int y, Color color) {
        g2d.setColor(color);

        String[] palabras = texto.split(" ");
        String lineaActual = "";

        for (String palabra : palabras) {
            String pruebaLinea = lineaActual + palabra + " ";

            if (pruebaLinea.length() > 32) {
                g2d.drawString(lineaActual, 10, y);
                y += 16;
                lineaActual = palabra + " ";
            } else {
                lineaActual = pruebaLinea;
            }
        }

        if (lineaActual.length() > 0) {
            g2d.drawString(lineaActual, 10, y);
            y += 16;
        }

        g2d.setColor(Color.white);
        return y;
    }


    //Funcion que dibuja las piezas que han sido capturadas
    private int dibujarCapturadas(Graphics2D g2d, ArrayList<Piezas> lista, int y) {
        int x = 10;

        for (Piezas p : lista) {
            if (p.getImagen() != null) {
                g2d.drawImage(p.getImagen(), x, y, anchoIcono, anchoIcono, null);
            }

            x += anchoIcono + 5;

            if (x > 260 - anchoIcono) {
                x = 10;
                y += anchoIcono + 5;
            }
        }

        return y + anchoIcono + 5;
    }

    public int dibujarDatos(Graphics2D g2d, int y) {
        Piezas seleccionada = tablero.piezaElegida;

        if (seleccionada == null) {
            g2d.drawString("Selecciona una pieza", 10, y);
            return y + 20;
        }

        g2d.drawString("Nombre: " + seleccionada.getAlias(), 10, y);
        g2d.drawString("Vida: " + seleccionada.getVida(), 10, y + 20);
        g2d.drawString("Ataque: " + seleccionada.getAtaque(), 10, y + 40);
        g2d.drawString("Escudo: " + seleccionada.getEscudo(), 10, y + 60);
        if(seleccionada.getAlias().equals("Muerte"))
            g2d.drawString("Click derecho para ver el panel de habilidades de " + seleccionada.getAlias(), 10, 80);
        if(seleccionada.getAlias().equals("Vampiro"))
            g2d.drawString("Click derecho para usar su habilidad especial (Robo de vida)", 10, 80);

        return y + 60;
    }
}
