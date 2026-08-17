package jrp.progra.piezas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import jrp.progra.vampiresurvival_1.Tablero;

public abstract class Piezas {
    public int col, fil;
    public int xPos, yPos;

    public boolean esBlanca;
    protected String nombre;
    protected String alias;
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

    public void cargarImagenes(String nombre) {
        try {
            this.imagen = ImageIO.read(getClass().getResourceAsStream("/" + nombre + ".png"));
        } catch (IOException | NullPointerException e) {
            System.out.println("Error cargando imagen para la pieza: " + nombre);
            e.printStackTrace();
        }
    }

    public abstract boolean esMovimientoValido(int col, int fil);

    public abstract boolean chocaPieza(int col, int fil);

    public void paint(Graphics2D g2d) {
        if (imagen != null) {
            g2d.drawImage(imagen, xPos, yPos, 120, 120, null);
        }
    }


    public final int getCol() {
        return col;
    }

    public final int getFil() {
        return fil;
    }

    public final String getNombre() {
        return nombre;
    }

    public final String getAlias(){
        return alias;
    }

    public final int getVida() {
        return vida;
    }

    // Ataque normal, se resta del escudo
    public boolean recibirHit(int deal){
        if(this.escudo<=deal){
            int dealR = deal-this.escudo;
            this.escudo=0;
            return muere(dealR);
        }
        this.escudo -= deal;
        return false;
    }

    // Ataque especial, ignora el escudo del objetivo
    public boolean recibirHitDirecto(int deal){
        return muere(deal);
    }

    //Verificacion de si la pieza muere con el daño recibido
    public boolean muere(int deal){
        if(deal==0){
            return false;
        }
        this.vida -=deal;
        if(this.vida<=0){
            this.vida=0;
            return true;}
        else
            return false;

    }

    public int getAtaque() {
        return ataque;
    }

    public int getEscudo() {
        return escudo;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

}
