package jrp.progra.vampiresurvival_1;

import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import jrp.progra.piezas.Piezas;


public class Entrada extends MouseAdapter {
    Tablero tablero;

    public Entrada(Tablero tablero){
        this.tablero=tablero;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / tablero.tCasillas;
        int fil = e.getY() / tablero.tCasillas;

        Piezas piezaXY = tablero.getPieza(col, fil);
        if(piezaXY != null){
            tablero.piezaElegida = piezaXY;
            tablero.repaint();

        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / tablero.tCasillas;
        int fil = e.getY() / tablero.tCasillas;

        if(tablero.piezaElegida != null){

            boolean esClick = (col == tablero.piezaElegida.col && fil == tablero.piezaElegida.fil);

            if(esClick){
                tablero.piezaElegida.xPos = tablero.piezaElegida.col * tablero.tCasillas;
                tablero.piezaElegida.yPos = tablero.piezaElegida.fil * tablero.tCasillas;
                tablero.repaint();
                return;
            }

            Mover mov = new Mover(tablero, tablero.piezaElegida, col, fil);

            if(tablero.esMovimientoValido(mov)){
                tablero.moverPieza(mov);
            }
            else{
                tablero.piezaElegida.xPos=tablero.piezaElegida.col * tablero.tCasillas;
                tablero.piezaElegida.yPos=tablero.piezaElegida.fil * tablero.tCasillas;

            }

            tablero.piezaElegida=null;
        }

        tablero.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(tablero.piezaElegida!=null){
            tablero.piezaElegida.xPos=e.getX()-tablero.tCasillas/2;
            tablero.piezaElegida.yPos=e.getY()-tablero.tCasillas/2;

            tablero.repaint();
        }
    }
    
}
