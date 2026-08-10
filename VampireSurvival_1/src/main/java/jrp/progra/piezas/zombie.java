package jrp.progra.piezas;

import jrp.progra.vampiresurvival_1.Tablero;

public class Zombie extends Piezas{
    public Zombie(Tablero tablero, int col, int fil, boolean esBlanca){
        super(tablero);
        this.esBlanca = esBlanca;
        this.col = col;
        this.fil = fil;
        this.nombre = "Zombie";
        this.ataque=1;
        this.escudo=0;
        this.vida=1;

        this.xPos = col * tablero.tCasillas;
        this.yPos = fil * tablero.tCasillas;

        cargarImagenes(this.nombre);
    }

    // public void cargarImagenes(){
    //     try {
    //         if(esBlanco){
    //             this.nombre += "_blanco";
    //         }
    //         else{
    //             this.nombre += "_negro";
    //         }
    //         this.imagen = ImageIO.read(getClass().getResourceAsStream("/" + this.nombre + ".png"));
    //     } catch (IOException | NullPointerException e) {
    //         System.out.println("Error cargando imagen para la pieza: " + nombre);
    //         e.printStackTrace();
    //     }
    // }
}
