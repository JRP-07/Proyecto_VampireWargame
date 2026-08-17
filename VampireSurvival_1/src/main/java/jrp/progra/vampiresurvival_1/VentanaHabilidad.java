package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jrp.progra.piezas.Piezas;

// Ventana para elegir la habilidad especial del Necromante.
public class VentanaHabilidad extends JDialog {

    private static final String[] CLAVES_NECROMANTE = {"invocarZombie", "lanza", "ordenarZombie"};

    public VentanaHabilidad(Frame propietario, Tablero tablero, Piezas origen) {
        super(propietario, "Elegir habilidad - " + origen.getNombre(), true);

        this.setLayout(new GridLayout(0, 1, 5, 5));

        for (int i = 0; i < CLAVES_NECROMANTE.length; i++) {
            final String clave = CLAVES_NECROMANTE[i];

            JButton boton = new JButton(nombreVisible(clave, origen.esBlanca));
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tablero.habilidadPendiente = clave;
                    tablero.piezaHabilidadPendiente = origen;
                    dispose();
                }
            });

            this.add(boton);
        }

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(botonCancelar);

        this.pack();
        this.setLocationRelativeTo(propietario);
    }

    private String nombreVisible(String clave, boolean esBlanca) {
        // El Necromante blanco invoca Esqueletos, el negro invoca Zombies
        String nombreRefuerzo = esBlanca ? "Esqueleto" : "Zombie";

        if (clave.equals("lanza")) {
            return "Ataque con lanza (2 casillas, ignora escudo)";
        } else if (clave.equals("invocarZombie")) {
            return "Invocar " + nombreRefuerzo + " en una casilla vacia";
        } else if (clave.equals("ordenarZombie")) {
            return "Ordenar " + nombreRefuerzo + " (atacar enemigo lejano o habilitarlo)";
        } else {
            return clave;
        }
    }
}
