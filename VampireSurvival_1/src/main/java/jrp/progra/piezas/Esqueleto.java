package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Esqueleto extends Zombie{
    public Esqueleto(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero, col, fil, esBlanca);
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        //Nombre asignado a la imagen
        this.nombre="Esqueleto";
        // Nombre asignado a la pieza por el juego
        this.alias="Esqueleto";

        cargarImagenes(this.nombre);
    }
}
