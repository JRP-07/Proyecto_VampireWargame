package jrp.progra.vampiresurvival_1;

import java.util.Date;

public class Jugador {

    private String usuario;
    private String contraseña;
    private int puntos;
    private Date fechaIngreso;
    private boolean activo;

    public Jugador(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.puntos = 0;
        this.fechaIngreso = new Date();
        this.activo = true;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad) {
        this.puntos = this.puntos + cantidad;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
