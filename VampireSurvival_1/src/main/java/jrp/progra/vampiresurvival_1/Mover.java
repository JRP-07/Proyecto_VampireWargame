package jrp.progra.vampiresurvival_1;
import jrp.progra.piezas.Piezas;

//Clase final que se encarga del movimiento de piezas
public final class Mover {
    int aCol;
    int aFil;
    int nCol;
    int nFil;

    Piezas pieza;
    Piezas captura;

    public Mover(Tablero tablero, Piezas pieza, int nCol, int nFila){
        this.aCol=pieza.getCol();
        this.aFil=pieza.getFil();
        this.nCol=nCol;
        this.nFil=nFila; 

        this.pieza=pieza;
        this.captura=tablero.getPieza(nCol, nFila);
    }
}
