package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class HombreLobo extends Piezas{
    public HombreLobo(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        this.nombre="HombreLobo";
        this.vida=5;
        this.ataque=5;
        this.escudo=4;

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
