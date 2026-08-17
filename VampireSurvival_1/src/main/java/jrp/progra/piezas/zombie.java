package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Zombie extends Piezas{

    public Piezas dueño;

    public boolean puedeAtacar = false;

    public Zombie(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.esBlanca = esBlanca;
        this.col = col;
        this.fil = fil;
        //Nombre asignado a la imagen
        this.nombre = "Zombie";
        //Nombre asignado a la pieza por el juego
        this.alias="Zombie";
        this.ataque=1;
        this.escudo=0;
        this.vida=1;

        this.xPos = col * tablero.tCasillas;
        this.yPos = fil * tablero.tCasillas;

        cargarImagenes(this.nombre);
    }

    public boolean esMovimientoValido(int col, int fil){
        int distCol = Math.abs(col - this.col);
        int distFil = Math.abs(fil - this.fil);
        int maxDist = Math.max(distCol, distFil);
        return maxDist == 1;
    }

    public boolean chocaPieza(int col, int fil){
        return false;
    }

}
