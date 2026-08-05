package jrp.progra.vampiresurvival_1;


import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jrp.progra.piezas.HombreLobo;
import jrp.progra.piezas.Muerte;
import jrp.progra.piezas.Piezas;
import jrp.progra.piezas.Vampiro;
import jrp.progra.piezas.Zombie;
import jrp.progra.piezas.Bruja;

public class Tablero extends JPanel {
    public int tCasillas = 120;

    int col = 6;
    int fil = 6;


    ArrayList<Piezas> piezasJuego = new ArrayList<>();
    public Piezas[][] ubicacion = new Piezas[6][6];

    Image im1 = new ImageIcon(getClass().getResource("/blanca_1.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    Image im2 = new ImageIcon(getClass().getResource("/negra_1.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

    public Tablero() {
        this.setPreferredSize(new Dimension(col * tCasillas, fil * tCasillas));
        generarPiezas();
        

    }

    public void agregarPieza(Piezas p, int c, int f) {
        if (c >= 0 && c < col && f >= 0 && f < fil) {
            p.setPos(c, f, c * tCasillas, f * tCasillas);
            ubicacion[c][f] = p;
            piezasJuego.add(p);
        }
    }

    public void generarPiezas(){
        // Piezas lado superior (fil 0) - Equipo Blanco
        piezasJuego.add(new Muerte(this, 2, 0, true));
        
        // Muerte m1=new Muerte();
        // m1.setEsBlanca(true);
        // m1.cargarImagenes(true);
        // agregarPieza(m1, 2, 0);

        // Bruja m2=new Bruja();
        // m2.setEsBlanca(true);
        // m2.cargarImagenes(true);
        // agregarPieza(m2, 3, 0);

        // Vampiro v1=new Vampiro();
        // v1.setEsBlanca(true);
        // v1.cargarImagenes(true);
        // agregarPieza(v1, 4, 0);

        // Vampiro v2=new Vampiro();
        // v2.setEsBlanca(true);
        // v2.cargarImagenes(true);
        // agregarPieza(v2, 1, 0);

        // HombreLobo hb1 =new HombreLobo();
        // hb1.setEsBlanca(true);
        // hb1.cargarImagenes(true);
        // agregarPieza(hb1, 0, 0);

        // HombreLobo hb2 =new HombreLobo();
        // hb2.setEsBlanca(true);
        // hb2.cargarImagenes(true);
        // agregarPieza(hb2, 5, 0);

        // // Piezas lado inferior (fil 5) - Equipo Negro
        // Bruja m3=new Bruja();
        // m3.setEsBlanca(false);
        // m3.cargarImagenes(false);
        // agregarPieza(m3, 3, 5);

        // Muerte m4=new Muerte();
        // m4.setEsBlanca(false);
        // m4.cargarImagenes(false);
        // agregarPieza(m4, 2, 5);

        // Vampiro v3=new Vampiro();
        // v3.setEsBlanca(false);
        // v3.cargarImagenes(false);
        // agregarPieza(v3, 1, 5);

        // Vampiro v4=new Vampiro();
        // v4.setEsBlanca(false);
        // v4.cargarImagenes(false);
        // agregarPieza(v4, 4, 5);

        // HombreLobo hb3 =new HombreLobo();
        // hb3.setEsBlanca(false);
        // hb3.cargarImagenes(false);
        // agregarPieza(hb3, 5, 5);

        // HombreLobo hb4 =new HombreLobo();
        // hb4.setEsBlanca(false);
        // hb4.cargarImagenes(false);
        // agregarPieza(hb4, 0, 5);
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
