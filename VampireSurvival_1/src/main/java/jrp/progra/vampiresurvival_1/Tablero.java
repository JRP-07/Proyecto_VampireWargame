package jrp.progra.vampiresurvival_1;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Tablero extends JPanel {
    public int tCasillas = 80;

    int col = 6;
    int fil = 6;

    Image im1 = Toolkit.getDefaultToolkit().createImage("VampireSurvival_1\\src\\main\\resources\\blanca_1.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    Image im2 = Toolkit.getDefaultToolkit().createImage("VampireSurvival_1\\src\\main\\resources\\negra_1.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);

    public Tablero() {
        this.setPreferredSize(new Dimension(col * tCasillas, fil * tCasillas));
    }

    // @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                // g2d.setColor((j+i) % 2 == 0 ? Color.black: Color.white);
                // g2d.setcolor
                // g2d.drawImage((j+1)%2==0 ? im1 : im2, null, getFocusCycleRootAncestor());
                // g2d.fillRect(j*tCasillas, i*tCasillas, tCasillas, tCasillas);
                g2d.drawImage((j+i)%2==0 ? im1 : im2, j*tCasillas, i*tCasillas, tCasillas, tCasillas, getFocusCycleRootAncestor());
            }
        }
    }
}
