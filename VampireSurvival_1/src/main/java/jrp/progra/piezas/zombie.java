package jrp.progra.piezas;


public class zombie extends Piezas{
    public zombie(){
        this.nombre = "Zombie";
        this.ataque=1;
        this.escudo=0;
        this.vida=1;

        cargarImagenes();
    }
}
