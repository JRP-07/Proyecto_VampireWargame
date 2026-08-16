package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;

import jrp.progra.piezas.Piezas;

public class PanelInfo extends JPanel {

    Tablero tablero;
    int anchoIcono = 40;

    public PanelInfo(Tablero tablero) {
        this.tablero = tablero;
        this.setPreferredSize(new Dimension(260, tablero.tCasillas * tablero.fil));
        this.setBackground(Color.darkGray);

        // Repintar el panel cada cierto tiempo para reflejar los cambios del tablero
        Timer temporizador = new Timer(200, e -> repaint());
        temporizador.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);

        int y = 20;

        g2d.drawString("Capturadas - Blanco", 10, y);
        y += 10;
        y = dibujarCapturadas(g2d, tablero.capturadasBlancas, y);

        y += 100;
        g2d.drawString("Capturadas - Negro", 10, y);
        y += 10;
        y = dibujarCapturadas(g2d, tablero.capturadasNegras, y);

        y += 80;
        g2d.drawString("Datos de la pieza", 10, y);
        y += 20;
        dibujarDatos(g2d, y);
    }

    private int dibujarCapturadas(Graphics2D g2d, java.util.ArrayList<Piezas> lista, int y) {
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

    public void dibujarDatos(Graphics2D g2d, int y) {
        Piezas seleccionada = tablero.piezaElegida;

        if (seleccionada == null) {
            g2d.drawString("Selecciona una pieza", 10, y);
            return;
        }

        g2d.drawString("Nombre: " + seleccionada.getNombre(), 10, y);
        g2d.drawString("Vida: " + seleccionada.getVida(), 10, y + 20);
        g2d.drawString("Ataque: " + seleccionada.getAtaque(), 10, y + 40);
        g2d.drawString("Escudo: " + seleccionada.getEscudo(), 10, y + 60);
    }
}
