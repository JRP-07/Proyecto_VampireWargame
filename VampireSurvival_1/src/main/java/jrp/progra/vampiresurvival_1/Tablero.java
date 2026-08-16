package jrp.progra.vampiresurvival_1;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

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
    // public Piezas[][] ubicacion = new Piezas[6][6];

    public ArrayList<Piezas> capturadasBlancas = new ArrayList<>();
    public ArrayList<Piezas> capturadasNegras = new ArrayList<>();

    public Piezas piezaElegida; 

    Entrada entra = new Entrada(this);



    Image im1 = new ImageIcon(getClass().getResource("/Casilla2_1.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    Image im2 = new ImageIcon(getClass().getResource("/Casilla2_2.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

    public Tablero() {
        this.setPreferredSize(new Dimension(col * tCasillas, fil * tCasillas));
        generarPiezas();

        this.addMouseListener(entra);
        this.addMouseMotionListener(entra);

        ToolTipManager.sharedInstance().registerComponent(this);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int columnaM = event.getX() / tCasillas;
        int filaM = event.getY() / tCasillas;

        Piezas piezaBajoMouse = getPieza(columnaM, filaM);

        if (piezaBajoMouse == null) {
            return null;
        }

        return "<html>" + piezaBajoMouse.getNombre()
                + "<br>Vida: " + piezaBajoMouse.getVida()
                + "<br>Ataque: " + piezaBajoMouse.getAtaque()
                + "<br>Escudo: " + piezaBajoMouse.getEscudo()
                + "</html>";
    }

    public boolean esMovimientoValido(Mover mov){
        if(mismoEquipo(mov.pieza, mov.captura)){
            return false;
        }

        if(!mov.pieza.esMovimientoValido(mov.nCol, mov.nFil)){
            return false;
        }

        if(mov.pieza.chocaPieza(mov.nCol, mov.nFil)){
            return false;
        }

        return true;
    }

    public boolean mismoEquipo(Piezas p1, Piezas p2){
        if(p1==null || p2==null){
            return false;
        }
        return p1.esBlanca ==p2.esBlanca;
    }

    public void moverPieza(Mover mov){
        if(mov.captura != null){
            boolean muereE = atacar(mov.pieza, mov.captura);

            if(muereE){
                capturar(mov);
                moverPosicion(mov);
            } else {
                mov.pieza.xPos = mov.aCol * tCasillas;
                mov.pieza.yPos = mov.aFil * tCasillas;
            }
        } else {
            moverPosicion(mov);
        }

    }

    private void moverPosicion(Mover mov){
        mov.pieza.col=mov.nCol;
        mov.pieza.fil=mov.nFil;
        mov.pieza.xPos=mov.nCol*tCasillas;
        mov.pieza.yPos=mov.nFil*tCasillas;
    }

    public void capturar(Mover mov){
        piezasJuego.remove(mov.captura);

        if(mov.captura.esBlanca){
            capturadasBlancas.add(mov.captura);
        } else {
            capturadasNegras.add(mov.captura);
        }
    }

    public boolean atacar(Piezas atacante, Piezas defensor){
        return defensor.recibirDaño(atacante.getAtaque());
    }

    public Piezas getPieza(int col, int fil){
        for(Piezas p: piezasJuego){
            if(p.getCol()==col && p.getFil()==fil){
                return p;
            }
        }
        return null;
    }

    public void generarPiezas(){
        // Piezas lado superior (fil 0) - Equipo Blanco
        piezasJuego.add(new Muerte(this, 2, 0, true));
        piezasJuego.add(new Bruja(this, 3, 0, true));
        piezasJuego.add(new Vampiro(this, 4, 0, true));
        piezasJuego.add(new Vampiro(this, 1, 0, true));
        piezasJuego.add(new HombreLobo(this, 0, 0, true));
        piezasJuego.add(new HombreLobo(this, 5, 0, true));

        // Piezas lado inferior (fil 5) - Equipo Negro
        piezasJuego.add(new Muerte(this, 2, 5, false));
        piezasJuego.add(new Bruja(this, 3, 5, false));
        piezasJuego.add(new Vampiro(this, 4, 5, false));
        piezasJuego.add(new Vampiro(this, 1, 5, false));
        piezasJuego.add(new HombreLobo(this, 0, 5, false));
        piezasJuego.add(new HombreLobo(this, 5, 5, false));
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
        
        //Dibujar las casillas a las que se puede mover
        if(piezaElegida!=null)
        for (int f = 0; f < fil; f++) {
            for (int c = 0; c < col; c++) {
                if(esMovimientoValido(new Mover(this, piezaElegida, c, f))){
                    g2d.setColor(new Color(68, 180,57,190));
                    g2d.fillRect(c*tCasillas, f*tCasillas, tCasillas, tCasillas);
                }
            }
        }


        // Dibujar las piezas que estén en el tablero
        for (Piezas p : piezasJuego) {
            if (p != null) {
                p.paint(g2d);
            }
        }

        // Resaltar la pieza seleccionada 
        dibujarResaltados(g2d);
    }

    private void dibujarResaltados(Graphics2D g2d) {
        if (piezaElegida == null) {
            return;
        }

        int columnaSeleccionada = piezaElegida.getCol();
        int filaSeleccionada = piezaElegida.getFil();

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(6));
        g2d.drawRect(columnaSeleccionada * tCasillas + 3, filaSeleccionada * tCasillas + 3, tCasillas - 6, tCasillas - 6);
    }
}
