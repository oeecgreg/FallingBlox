package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fr.eseo.e3.poo.projet.blox.modele.Puits;

public class VuePuits extends JPanel {

    public static final int TAILLE_PAR_DEFAUT = 20;

    private Puits puits;
    private int taille;

    // --- Constructeurs ---

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;

        // Configuration de base du JPanel selon l'énoncé
        this.setBackground(Color.WHITE);
        this.mettreAJourTaille();
    }

    // --- Accesseurs et Mutateurs ---

    public Puits getPuits() {
        return this.puits;
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
        this.mettreAJourTaille();
    }

    public int getTaille() {
        return this.taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
        this.mettreAJourTaille();
    }

    /**
     * Méthode utilitaire privée pour recalculer et appliquer la taille de préférence.
     * C'est une bonne pratique plutôt que de dupliquer ce code dans chaque setter/constructeur.
     */
    private void mettreAJourTaille() {
        if (this.puits != null) {
            int largeurPixels = this.puits.getLargeur() * this.taille;
            int profondeurPixels = this.puits.getProfondeur() * this.taille;
            this.setPreferredSize(new Dimension(largeurPixels, profondeurPixels));
        }
    }

    // --- Dessin du composant ---

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Remplit le fond avec la couleur définie (WHITE)

        /* Création de la copie de type Graphics2D */
        Graphics2D g2D = (Graphics2D) g.create();

        /* Définition de la couleur de la grille */
        g2D.setColor(Color.LIGHT_GRAY);

        // Dimensions totales en pixels
        int largeurMax = this.puits.getLargeur() * this.taille;
        int profondeurMax = this.puits.getProfondeur() * this.taille;

        // Tracé des lignes verticales
        for (int x = 0; x <= largeurMax; x += this.taille) {
            g2D.drawLine(x, 0, x, profondeurMax);
        }

        // Tracé des lignes horizontales
        for (int y = 0; y <= profondeurMax; y += this.taille) {
            g2D.drawLine(0, y, largeurMax, y);
        }

        /* Libération de la mémoire */
        g2D.dispose();
    }
}