package jrp.progra.piezas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Piezas {
    public int col, fil;
    public int xPos, yPos;

    public boolean esBlanca;
    public String nombre;
    public int vida;
    public int ataque;
    public int escudo;
    public BufferedImage imagen;

    public void cargarImagenes(){
        try {
            this.imagen = ImageIO.read(getClass().getResourceAsStream("/" + this.nombre + ".png"));
        } catch (IOException | NullPointerException e) {
            System.out.println("Error cargando imagen para la pieza: " + nombre);
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        if (imagen != null) {
            g2d.drawImage(imagen, xPos, yPos, 90, 90,  null);
        }
    }
}
