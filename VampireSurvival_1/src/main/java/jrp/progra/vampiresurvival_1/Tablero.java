package jrp.progra.vampiresurvival_1;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import jrp.progra.piezas.Piezas;
import jrp.progra.piezas.zombie;

public class Tablero extends JPanel {
    public int tCasillas = 90;

    int col = 6;
    int fil = 6;


    ArrayList<Piezas> piezasJuego = new ArrayList<>();
    public Piezas[][] ubicacion = new Piezas[12][12];

    Image im1 = Toolkit.getDefaultToolkit().createImage("VampireSurvival_1\\src\\main\\resources\\blanca_1.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    Image im2 = Toolkit.getDefaultToolkit().createImage("VampireSurvival_1\\src\\main\\resources\\negra_1.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);

    public Tablero() {
        this.setPreferredSize(new Dimension(col * tCasillas, fil * tCasillas));
        generarPiezas();
        

    }

    public void agregarPieza(Piezas p, int c, int f) {
        if (c >= 0 && c < 12 && f >= 0 && f < 12) {
            p.col = c;
            p.fil = f;
            p.xPos = c * tCasillas;
            p.yPos = f * tCasillas;
            ubicacion[c][f] = p;
            piezasJuego.add(p);
        }
    }

    public void generarPiezas(){
        // Aquí puedes instanciar tus zombies, vampiros, etc.
        zombie z = new zombie();
        z.cargarImagenes();
        
        agregarPieza(z, 1, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                g2d.drawImage((j + i) % 2 == 0 ? im1 : im2, j * tCasillas, i * tCasillas, tCasillas, tCasillas, this);
            }
        }

        // 2. Dibujar todas las piezas que estén en el tablero
        for (Piezas p : piezasJuego) {
            if (p != null) {
                p.paint(g2d);
            }
        }
    }
}
