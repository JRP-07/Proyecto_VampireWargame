package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Bruja extends Piezas{
    public Bruja(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        this.nombre="Bruja";
        this.vida=3;
        this.ataque=4;
        this.escudo=2;

        this.xPos=col*tablero.tCasillas;
        this.yPos=fil*tablero.tCasillas;

        if(esBlanca){
            this.nombre += "_blanco";
        }
        else{
            this.nombre += "_negro";
        }
        cargarImagenes(this.nombre);
    }
}
