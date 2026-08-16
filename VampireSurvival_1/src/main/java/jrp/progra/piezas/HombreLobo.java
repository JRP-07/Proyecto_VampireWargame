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

    public boolean esMovimientoValido(int col, int fil){
        int distCol = Math.abs(col - this.col);
        int distFil = Math.abs(fil - this.fil);
        int maxDist = Math.max(distCol, distFil);
        return maxDist >= 1 && maxDist <= 2 && (this.col==col || this.fil==fil || distCol == distFil);
    }

    public boolean chocaPieza(int col, int fil){
        if(this.col==col || this.fil ==fil){

            //izquierda
            if(this.col > col){
                for (int c = this.col -1 ; c > col ; c--) 
                    if(tablero.getPieza(c, this.fil) != null){
                        return true;
                }
            }
    
            //derecha
            if(this.col < col){
                for (int c = this.col + 1 ; c < col ; c++) 
                    if(tablero.getPieza(c, this.fil) != null){
                        return true;
                }
            }
    
            //arriba
            if(this.fil > fil){
                for (int f = this.fil -1 ; f > fil ; f--) 
                    if(tablero.getPieza(this.col, f) != null){
                        return true;
                }
            }
    
            //abajo
            if(this.fil < fil){
                for (int f = this.fil + 1 ; f < fil ; f++) 
                    if(tablero.getPieza(this.col, f) != null){
                        return true;
                }
            }
        } else {
            // arriba izquierda
            if(this.col > col && this.fil > fil){
                for (int i = 1; i < Math.abs(this.col - col); i++){
                    if(tablero.getPieza(this.col - i, this.fil - i) != null)
                        return true;
                }
            }

            // arriba derecha
            if(this.col < col && this.fil > fil){
                for (int i = 1; i < Math.abs(this.col - col); i++){
                    if(tablero.getPieza(this.col + i, this.fil - i) != null)
                        return true;
                }
            }

            // abajo izquierda
            if(this.col > col && this.fil < fil){
                for (int i = 1; i < Math.abs(this.col - col); i++){
                    if(tablero.getPieza(this.col - i, this.fil + i) != null)
                        return true;
                }
            }

            // abajo derecha
            if(this.col < col && this.fil < fil){
                for (int i = 1; i < Math.abs(this.col - col); i++){
                    if(tablero.getPieza(this.col + i, this.fil + i) != null)
                        return true;
                }
            }
        }

        
        return false;
    }
}
