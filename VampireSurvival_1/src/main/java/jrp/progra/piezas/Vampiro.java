package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Vampiro extends Piezas{
    public Vampiro(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.nombre="vampiro_2";
        this.vida=4;
        this.ataque=3;
        this.escudo=5;
    }
}
