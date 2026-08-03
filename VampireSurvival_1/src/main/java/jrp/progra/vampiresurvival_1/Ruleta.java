package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class Ruleta extends JPanel implements ActionListener {
    private double currentAngle = 0;
    private double speed = 0;
    private boolean isSpinning = false;
    private final javax.swing.Timer timer;
    private final List<String> opciones;
    private final Consumer<Ruleta> onFinished;
    private String resultado = "";

    // --- Paleta Gótica ---
    private final Color COLOR_MARMOL = new Color(235, 230, 210);
    private final Color COLOR_VETA_MARMOL = new Color(200, 190, 170);
    private final Color COLOR_METAL_PLATA = new Color(180, 180, 180);
    private final Color COLOR_METAL_OSCURO = new Color(40, 40, 40);
    private final Color COLOR_TEXTO = new Color(20, 20, 20);

    // Para que las vetas del mármol sean fijas y no parpadeen
    private final List<Line2D> marbleVeins = new ArrayList<>();

    public Ruleta(List<String> opciones, Consumer<Ruleta> onFinished) {
        this.opciones = opciones;
        this.onFinished = onFinished;
        this.timer = new javax.swing.Timer(16, this);
        this.setPreferredSize(new Dimension(500, 500));

        // Generar vetas de mármol aleatorias una sola vez
        Random rnd = new Random();
        for (int i = 0; i < 15; i++) {
            double x1 = rnd.nextDouble() * 400 - 200;
            double y1 = rnd.nextDouble() * 400 - 200;
            double x2 = x1 + (rnd.nextDouble() * 100 - 50);
            double y2 = y1 + (rnd.nextDouble() * 100 - 50);
            marbleVeins.add(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    public void girar() {
        if (!isSpinning) {
            speed = 15 + new Random().nextDouble() * 15;
            isSpinning = true;
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
        int r = Math.min(cx, cy) - 50;

        // 1. MARCO EXTERIOR (Metal tallado)
        drawOrnateFrame(g2d, cx, cy, r);

        // 2. CUERPO DE LA RULETA (Mármol)
        g2d.translate(cx, cy);
        g2d.rotate(Math.toRadians(currentAngle));

        // Fondo de piedra
        g2d.setColor(COLOR_MARMOL);
        g2d.fillOval(-r, -r, r * 2, r * 2);

        // Dibujar vetas de mármol
        g2d.setColor(COLOR_VETA_MARMOL);
        g2d.setStroke(new BasicStroke(1.5f));
        for (Line2D vein : marbleVeins) {
            g2d.draw(vein);
        }

        // 3. REBANADAS Y TEXTOS
        double sliceAngle = 360.0 / opciones.size();
        g2d.setColor(COLOR_TEXTO);
        g2d.setStroke(new BasicStroke(2f));

        for (int i = 0; i < opciones.size(); i++) {
            double angleOffset = i * sliceAngle;

            // Línea divisoria
            g2d.rotate(Math.toRadians(angleOffset));
            g2d.drawLine(0, 0, 0, -r);

            // Texto centrado en la rebanada
            g2d.rotate(Math.toRadians(sliceAngle / 2));
            g2d.setFont(new Font("Serif", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String txt = opciones.get(i);
            g2d.drawString(txt, -fm.stringWidth(txt) / 2, -r / 2);
            g2d.rotate(Math.toRadians(-sliceAngle / 2));

            g2d.rotate(Math.toRadians(-angleOffset));
        }

        // 4. ELEMENTOS CENTRALES Y FIJOS
        g2d.rotate(Math.toRadians(-currentAngle)); // Volver al origen

        // Sombra radial para dar volumen (Efecto 3D)
        RadialGradientPaint shadow = new RadialGradientPaint(
            new Point2D.Float(0, 0), r,
            new float[]{0f, 1f},
            new Color[]{new Color(255,255,255,100), new Color(0,0,0,80)}
        );
        g2d.setPaint(shadow);
        g2d.fillOval(-r, -r, r * 2, r * 2);

        // Botón Central Metálico
        g2d.setPaint(new GradientPaint(-20, -20, COLOR_METAL_PLATA, 20, 20, COLOR_METAL_OSCURO));
        g2d.fillOval(-25, -25, 50, 50);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(-25, -25, 50, 50);

        // INDICADOR SUPERIOR (El puntero gótico)
        g2d.setPaint(new GradientPaint(0, -r, COLOR_METAL_PLATA, 0, -r - 30, COLOR_METAL_OSCURO));
        g2d.setStroke(new BasicStroke(8f));
        g2d.drawLine(0, -r, 0, -r - 30);
        g2d.setColor(COLOR_METAL_OSCURO);
        g2d.fillOval(-6, -r - 36, 12, 12);
    }

    private void drawOrnateFrame(Graphics2D g2d, int cx, int cy, int r) {
        // Anillo exterior grueso con relieve
        g2d.setStroke(new BasicStroke(15f));
        g2d.setPaint(new GradientPaint(cx - r, cy - r, COLOR_METAL_PLATA, cx + r, cy + r, COLOR_METAL_OSCURO));
        g2d.drawOval(cx - r, cy - r, r * 2, r * 2);

        // Anillo interior fino para detalle
        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(Color.BLACK);
        g2d.drawOval(cx - r + 5, cy - r + 5, (r * 2) - 10, (r * 2) - 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSpinning) {
            currentAngle += speed;
            speed *= 0.98; // Fricción

            if (speed < 0.1) {
                isSpinning = false;
                timer.stop();
                finalizarGiro();
            }
            repaint();
        }
    }

    private void finalizarGiro() {
        double normalized = (currentAngle % 360 + 360) % 360;
        double arcAngle = 360.0 / opciones.size();
        double relativeAngle = (360 - normalized) % 360;
        int index = (int) (relativeAngle / arcAngle);

        if (index < 0) index = 0;
        if (index >= opciones.size()) index = opciones.size() - 1;

        resultado = opciones.get(index);
        System.out.println("Resultado: " + resultado + " (Ángulo: " + normalized + ")");

        if (onFinished != null) onFinished.accept(this);
    }

    public String getResultado() {
        return resultado;
    }
}
