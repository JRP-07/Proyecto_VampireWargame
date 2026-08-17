package jrp.progra.vampiresurvival_1;

import java.awt.event.MouseEvent;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseAdapter;

import javax.swing.SwingUtilities;

import jrp.progra.piezas.Muerte;
import jrp.progra.piezas.Piezas;
import jrp.progra.piezas.Vampiro;
import jrp.progra.piezas.Zombie;


public class Entrada extends MouseAdapter {
    Tablero tablero;

    public Entrada(Tablero tablero){
        this.tablero=tablero;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(tablero.partidaTerminada){
            return;
        }

        int col = e.getX() / tablero.tCasillas;
        int fil = e.getY() / tablero.tCasillas;

        if(!SwingUtilities.isRightMouseButton(e) && tablero.habilidadPendiente != null){
            ejecutarDestinoHabilidad(col, fil);
            return;
        }

        if(SwingUtilities.isRightMouseButton(e)){
            manejarClickDerecho(col, fil);
            return;
        }

        Piezas piezaXY = tablero.getPieza(col, fil);
        if(piezaXY != null && puedeSeleccionar(piezaXY)){
            tablero.piezaElegida = piezaXY;
            tablero.repaint();

        }
    }

    // Solo se selecciona la ficha que la ruleta habilita
    private boolean puedeSeleccionar(Piezas pieza){
        if(pieza.esBlanca != tablero.turnoBlanco){
            return false;
        }

        //Revisa si la pieza es un Zombie
        if(pieza instanceof Zombie){
            return ((Zombie) pieza).puedeAtacar;
        }

        return pieza == tablero.piezaPermitida;
    }

    // El jugador eligio la habilidad del nigromante
    private void ejecutarDestinoHabilidad(int col, int fil){
        String clave = tablero.habilidadPendiente;
        Piezas origen = tablero.piezaHabilidadPendiente;

        origen.xPos = origen.col * tablero.tCasillas;
        origen.yPos = origen.fil * tablero.tCasillas;

        if(tablero.esHabilidadValidaEnDestino(clave, origen, col, fil)){
            tablero.ejecutarHabilidad(clave, origen, col, fil);
        }

        tablero.habilidadPendiente = null;
        tablero.piezaHabilidadPendiente = null;
        tablero.piezaElegida = null;
        tablero.repaint();
    }

    private void manejarClickDerecho(int col, int fil){
        if(tablero.piezaElegida == null){
            return;
        }

        Piezas piezaClic = tablero.getPieza(col, fil);

        // Click derecho sobre la Muerte abre el menu
        if(piezaClic == tablero.piezaElegida && tablero.piezaElegida instanceof Muerte){
            abrirVentanaHabilidadNecromante();
            return;
        }

        // Click derecho sobre un enemigo con el Vampiro absorbe sangre directo
        if(tablero.piezaElegida instanceof Vampiro && piezaClic != null){
            if(tablero.esHabilidadValidaEnDestino("absorber", tablero.piezaElegida, col, fil)){
                tablero.ejecutarHabilidad("absorber", tablero.piezaElegida, col, fil);
            }
            tablero.piezaElegida = null;
            tablero.repaint();
        }
    }

    //Funcion que se encarga de abrir la ventana de habilidades del Nigromante
    private void abrirVentanaHabilidadNecromante(){
        Window ventana = SwingUtilities.getWindowAncestor(tablero);
        Frame propietario = (ventana instanceof Frame) ? (Frame) ventana : null;

        VentanaHabilidad ventanaHabilidad = new VentanaHabilidad(propietario, tablero, tablero.piezaElegida);
        ventanaHabilidad.setVisible(true);

        tablero.repaint();
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
    public void mouseDragged(MouseEvent e){
        if(tablero.piezaElegida!=null){
            tablero.piezaElegida.xPos=e.getX()-tablero.tCasillas/2;
            tablero.piezaElegida.yPos=e.getY()-tablero.tCasillas/2;

            tablero.repaint();
        }
    }
    
}
