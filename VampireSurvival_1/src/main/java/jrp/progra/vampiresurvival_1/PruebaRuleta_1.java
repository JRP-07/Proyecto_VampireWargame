package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PruebaRuleta_1 {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Prueba Simple de Ruleta");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(450, 500);
        ventana.setLayout(new BorderLayout());

        List<String> opciones = Arrays.asList("Vampiro", "Hombre Lobo", "Muerte");

        final Ruleta_1[] ruletaRef = new Ruleta_1[1];
        ruletaRef[0] = new Ruleta_1(opciones, () -> {
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
