package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Vampiro extends Piezas{
    public Vampiro(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        this.nombre="Vampiro2";
        this.vida=4;
        this.ataque=3;
        this.escudo=5;

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


    public boolean esMovimientoValido(int col, int fil){
        int distCol = Math.abs(col - this.col);
        int distFil = Math.abs(fil - this.fil);
        int maxDist = Math.max(distCol, distFil);
        return maxDist == 1   && (this.col==col || this.fil==fil || distCol == distFil);
    }
}
