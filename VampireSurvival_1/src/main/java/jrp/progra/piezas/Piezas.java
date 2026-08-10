package jrp.progra.piezas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import jrp.progra.vampiresurvival_1.Tablero;

public abstract class Piezas {
    protected int col, fil;
    protected int xPos, yPos;

    protected boolean esBlanca;
    protected String nombre;
    protected int vida;
    protected int ataque;
    protected int escudo;
    protected BufferedImage imagen;
    Tablero tablero;

    public Piezas(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setPos(int col, int fil, int xPos, int yPos) {
        this.col = col;
        this.fil = fil;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void cargarImagenes() {
        try {
            // if(esBlanco){
            // this.nombre += "_blanco";
            // }
            // else{
            // this.nombre += "_negro";
            // }
            this.imagen = ImageIO.read(getClass().getResourceAsStream("/" + this.nombre + ".png"));
        } catch (IOException | NullPointerException e) {
            System.out.println("Error cargando imagen para la pieza: " + nombre);
            e.printStackTrace();
        }
    }

    public void cargarImagenes(String nombre) {
        try {
            this.imagen = ImageIO.read(getClass().getResourceAsStream("/" + nombre + ".png"));
        } catch (IOException | NullPointerException e) {
            System.out.println("Error cargando imagen para la pieza: " + nombre);
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        if (imagen != null) {
            g2d.drawImage(imagen, xPos, yPos, 120, 120, null);
        }
    }

    public void setEsBlanca(boolean esBlanca) {
        this.esBlanca = esBlanca;
    }

    public int getCol() {
        return col;
    }

    public int getFil() {
        return fil;
    }

}
