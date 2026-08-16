package jrp.progra.piezas;

import java.io.IOException;

import javax.imageio.ImageIO;

import jrp.progra.vampiresurvival_1.Tablero;

public class Muerte extends Piezas{
    public Muerte(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.col=col;
        this.fil=fil;
        this.esBlanca=esBlanca;
        this.nombre="Muerte2";
        this.ataque=4;
        this.vida=5;
        this.escudo=4;

        this.xPos=col*tablero.tCasillas;
        this.yPos=fil*tablero.tCasillas;

        // this.cargarImagenes();
        
        if(esBlanca){
            this.nombre += "_blanco";
        }
        else{
            this.nombre += "_negro";
        }
        cargarImagenes(this.nombre);
    }

    public boolean esMovimientoValido(int col, int fil){
        return Math.max(Math.abs(col - this.col), Math.abs(fil - this.fil)) == 1;
    }
}
