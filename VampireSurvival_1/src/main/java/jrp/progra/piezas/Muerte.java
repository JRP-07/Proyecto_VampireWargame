package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Muerte extends Piezas {
    public Muerte(Tablero tablero, int col, int fil, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.esBlanca = esBlanca;
        //Nombre asignado a la imagen
        this.nombre = "Muerte2";
        // Nombre asignado a la pieza por el juego
        this.alias = "Muerte";
        this.ataque = 4;
        this.vida = 3;
        this.escudo = 1;

        this.xPos = col * tablero.tCasillas;
        this.yPos = fil * tablero.tCasillas;

        if (esBlanca) {
            this.nombre += "_blanco";
        } else {
            this.nombre += "_negro";
        }
        cargarImagenes(this.nombre);
    }

    public boolean esMovimientoValido(int col, int fil) {
        return Math.max(Math.abs(col - this.col), Math.abs(fil - this.fil)) == 1;
    }

    public boolean chocaPieza(int col, int fil) {
        return false;
    }

    // Habilidad especial: ataque con lanza.
    public boolean ataqueLanza(Piezas objetivo) {
        int dealLanza = this.ataque / 2;
        return objetivo.recibirHitDirecto(dealLanza);
    }
}
