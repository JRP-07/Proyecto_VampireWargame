package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Muerte extends Piezas{
    public Muerte(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        this.nombre="muerte_2";
        this.ataque=4;
        this.vida=5;
        this.escudo=4;
        
    }
}
