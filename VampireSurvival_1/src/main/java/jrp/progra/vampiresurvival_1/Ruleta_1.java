package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

public class Ruleta_1 extends JPanel implements ActionListener {
    private double angulo = 0;
    private double velocidad = 0;
    private boolean girando = false;
    private javax.swing.Timer timer;
    private List<String> opciones;
    private String resultado = "";
    private Runnable alTerminar;

    public Ruleta_1(List<String> opciones, Runnable alTerminar) {
        this.opciones = opciones;
        this.alTerminar = alTerminar;
        this.timer = new javax.swing.Timer(20, this);
        this.setPreferredSize(new Dimension(400, 400));
    }

    public void girar() {
        if (!girando) {
            velocidad = 15 + new Random().nextDouble() * 10;
            girando = true;
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radio = 150;

        int anguloRebanada = 360 / opciones.size();

        for (int i = 0; i < opciones.size(); i++) {
            g2d.setColor(i % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

            int startAngle = (int)(i * anguloRebanada + angulo);
            g2d.fillArc(cx - radio, cy - radio, radio * 2, radio * 2, startAngle, anguloRebanada);

            g2d.setColor(Color.BLACK);
            double angleRad = Math.toRadians(startAngle + anguloRebanada / 2.0);
            int tx = (int) (cx + (radio * 0.6) * Math.cos(angleRad));
            int ty = (int) (cy - (radio * 0.6) * Math.sin(angleRad));

            g2d.drawString(opciones.get(i), tx - 20, ty);
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(cx, cy - radio, cx, cy - radio - 20);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(cx - 5, cy - 5, 10, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (girando) {
            angulo += velocidad;
            velocidad *= 0.97;

            if (velocidad < 0.1) {
                girando = false;
                timer.stop();
                calcularResultado();
            }
            repaint();
        }
    }

    private void calcularResultado() {
        double normalized = (angulo % 360 + 360) % 360;
        double topAngle = (90 - normalized + 360) % 360;
        int index = (int) (topAngle / (360.0 / opciones.size()));

        if (index >= 0 && index < opciones.size()) {
            resultado = opciones.get(index);
        }

        if (alTerminar != null) {
            alTerminar.run();
        }
    }

    public String getResultado() {
        return resultado;
    }
}
