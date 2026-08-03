package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PruebaRuletaSimple {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Prueba Simple de Ruleta");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(450, 500);
        ventana.setLayout(new BorderLayout());

        List<String> opciones = Arrays.asList("Opción 1", "Opción 2", "Opción 3", "Opción 4");

        // Usamos un arreglo para que el Runnable pueda acceder a la ruleta
        final RuletaSimple[] ruletaRef = new RuletaSimple[1];
        ruletaRef[0] = new RuletaSimple(opciones, () -> {
            JOptionPane.showMessageDialog(ventana, "Resultado: " + ruletaRef[0].getResultado());
        });

        JButton boton = new JButton("Girar");
        boton.addActionListener(e -> ruletaRef[0].girar());

        ventana.add(ruletaRef[0], BorderLayout.CENTER);
        ventana.add(boton, BorderLayout.SOUTH);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
