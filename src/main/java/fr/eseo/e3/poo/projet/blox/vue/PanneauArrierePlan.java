package fr.eseo.e3.poo.projet.blox.vue;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanneauArrierePlan extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        // Option : Dégradé futuriste pour remplir les bords
        java.awt.GradientPaint gp = new java.awt.GradientPaint(0, 0, new Color(20, 20, 40), 0, getHeight(), new Color(0, 0, 0));
        g2D.setPaint(gp);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        // Optionnel : Dessiner une grille légère en fond
        g2D.setColor(new Color(255, 255, 255, 30));
        for(int i=0; i<getWidth(); i+=50) g2D.drawLine(i, 0, i, getHeight());
        for(int i=0; i<getHeight(); i+=50) g2D.drawLine(0, i, getWidth(), i);
    }
}