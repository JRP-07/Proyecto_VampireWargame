package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PruebaRuleta {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Vampire Wargame - Ritual de Selección");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 650);
        ventana.setLayout(new BorderLayout());

        // Fondo oscuro profundo
        ventana.getContentPane().setBackground(new Color(20, 20, 25));

        List<String> piezas = Arrays.asList(
            "SANGRE PURA", "NO MUERTO", "CAZADOR",
            "BESTIA", "SANGRE CORRUPTA", "SOBERANO"
        );

        Ruleta ruleta = new Ruleta(piezas, r -> {
            JOptionPane.showMessageDialog(ventana,
                "El destino ha hablado:\n\n" + r.getResultado().toUpperCase(),
                "Sello del Destino",
                JOptionPane.PLAIN_MESSAGE);
        });

        JButton btnGirar = new JButton("GIRAR LA RULETA");
        btnGirar.setFont(new Font("Serif", Font.BOLD, 20));
        btnGirar.setForeground(new Color(180, 150, 100));
        btnGirar.setBackground(new Color(40, 30, 30));
        btnGirar.setFocusPainted(false);
        btnGirar.setBorder(BorderFactory.createLineBorder(new Color(100, 80, 60), 2));
        btnGirar.addActionListener(e -> ruleta.girar());

        ventana.add(ruleta, BorderLayout.CENTER);
        ventana.add(btnGirar, BorderLayout.SOUTH);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
