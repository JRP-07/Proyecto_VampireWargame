package jrp.progra.vampiresurvival_1;

import javax.swing.*;
import java.awt.*;

// Panel base que dibuja una imagen de fondo escalada a todo el tamaño del panel.
// Las pantallas de menu heredan de esta clase para no repetir el codigo de dibujo.
public class Fondo extends JPanel {

    private Image fondo;

    public Fondo(String nombreImagen) {
        this.fondo = new ImageIcon(getClass().getResource("/" + nombreImagen)).getImage();
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
