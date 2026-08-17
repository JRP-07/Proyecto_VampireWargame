package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Bruja extends Muerte {
    public Bruja(Tablero tablero, int col, int fil, boolean esBlanca) {
        super(tablero, col, fil, esBlanca);
        this.col = col;
        this.fil = fil;
        this.esBlanca = esBlanca;
        // Nombre asignado a la imagen
        this.nombre = "Bruja";
        // Nombre asignado a la pieza por el juego
        this.alias = "Bruja";

        this.xPos = col * tablero.tCasillas;
        this.yPos = fil * tablero.tCasillas;

        if (esBlanca) {
            this.nombre += "_blanco";
        } else {
            this.nombre += "_negro";
        }
        cargarImagenes(this.nombre);
    }
}
