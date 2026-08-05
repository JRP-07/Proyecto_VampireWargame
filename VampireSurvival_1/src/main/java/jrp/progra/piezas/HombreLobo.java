package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class HombreLobo extends Piezas{
    public HombreLobo(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.nombre="HombreLobo";
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        this.vida=5;
        this.ataque=5;
        this.escudo=4;
    }
}
