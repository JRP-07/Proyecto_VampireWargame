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
    private Image[] imagenes;
    private String resultado = "";
    private int resultadoIndice = -1;
    private Runnable alTerminar;

    public Ruleta_1(List<String> opciones, Runnable alTerminar) {
        this.opciones = opciones;
        this.alTerminar = alTerminar;
        this.timer = new javax.swing.Timer(20, this);
        this.setPreferredSize(new Dimension(220, 220));

        // Se carga la imagen de cada tipo de pieza para dibujarla en su rebanada
        imagenes = new Image[opciones.size()];
        for (int i = 0; i < opciones.size(); i++) {
            imagenes[i] = cargarImagen(opciones.get(i));
        }
    }

    // Convierte el nombre de la opcion en la imagen de la carta correspondiente
    private Image cargarImagen(String opcion) {
        String nombreLimpio = opcion.replace("\n", "").replace(" ", "").trim();
        String archivo;

        if (nombreLimpio.equals("Vampiro")) {
            archivo = "/Vampiro2_blanco.png";
        } else if (nombreLimpio.equals("HombreLobo")) {
            archivo = "/HombreLobo_blanco.png";
        } else if (nombreLimpio.equals("Muerte")) {
            archivo = "/Muerte2_blanco.png";
        } else if (nombreLimpio.equals("Bruja")) {
            archivo = "/Bruja_blanco.png";
        } else {
            return null;
        }

        return new ImageIcon(getClass().getResource(archivo)).getImage();
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
        int radio = Math.min(cx, cy) - 10; // el radio se adapta al tamaño real del panel

        int anguloRebanada = 360 / opciones.size();

        for (int i = 0; i < opciones.size(); i++) {
            g2d.setColor(i % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

            int startAngle = (int)(i * anguloRebanada + angulo);
            g2d.fillArc(cx - radio, cy - radio, radio * 2, radio * 2, startAngle, anguloRebanada);

            double angleRad = Math.toRadians(startAngle + anguloRebanada / 2.0);
            int tx = (int) (cx + (radio * 0.6) * Math.cos(angleRad));
            int ty = (int) (cy - (radio * 0.6) * Math.sin(angleRad));

            Image imagen = imagenes[i];
            if (imagen != null) {
                int tamañoImagen = Math.max(20, radio / 2);
                g2d.drawImage(imagen, tx - tamañoImagen / 2, ty - tamañoImagen / 2, tamañoImagen, tamañoImagen, this);
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(Math.max(2f, radio / 40f)));
        int largoFlecha = Math.max(6, radio / 8);
        g2d.drawLine(cx, cy - radio, cx, cy - radio - largoFlecha);

        g2d.setColor(Color.DARK_GRAY);
        int radioCentro = Math.max(4, radio / 15);
        g2d.fillOval(cx - radioCentro, cy - radioCentro, radioCentro * 2, radioCentro * 2);
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
            resultadoIndice = index;
        }

        if (alTerminar != null) {
            alTerminar.run();
        }
    }

    public String getResultado() {
        return resultado;
    }

    // Posicion (0 a 5) donde cayo la ruleta. Sirve para saber a cual de las fehcas corresponde al resultado
    public int getResultadoIndice() {
        return resultadoIndice;
    }
}
