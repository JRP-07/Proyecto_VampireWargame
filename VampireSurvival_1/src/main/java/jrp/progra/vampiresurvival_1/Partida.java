package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;

public class Partida extends JPanel{

    static Image fondo = Toolkit.getDefaultToolkit().createImage("VampireSurvival_1\\src\\main\\resources\\fondo1.jpg");
    public static void main(String[] args) {

        // Atrapa cualquier error que surja
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread hilo, Throwable error) {
                error.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Ocurrio un error inesperado:\n" + error,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        try {
            JFrame marco = new JFrame();
            marco.getContentPane().setBackground(Color.black);
            marco.setLayout(new BorderLayout());
            marco.setMinimumSize(new Dimension(1200, 950));
            marco.setLocationRelativeTo(null);

            MenuInicio menu = new MenuInicio(marco);
            marco.add(menu, BorderLayout.CENTER);

            marco.setVisible(true);

            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception error) {
            error.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "No se pudo iniciar el programa:\n" + error,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    

}
