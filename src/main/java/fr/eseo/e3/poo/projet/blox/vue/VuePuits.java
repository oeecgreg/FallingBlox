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

    // Nouvelle variable d'instance pour créer l'association avec VuePiece
    private VuePiece vuePiece;

    // --- Constructeurs ---

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;

        // Consigne : Après la construction, il n'y a aucune VuePiece associée
        this.vuePiece = null;

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

    // Accesseur pour récupérer la VuePiece associée
    public VuePiece getVuePiece() {
        return this.vuePiece;
    }

    // Mutateur pour associer une VuePiece
    public void setVuePiece(VuePiece vuePiece) {
        this.vuePiece = vuePiece;
    }

    /**
     * Méthode utilitaire privée pour recalculer et appliquer la taille de préférence.
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
        super.paintComponent(g); // Remplit le fond en blanc

        /* Création de la copie de type Graphics2D */
        Graphics2D g2D = (Graphics2D) g.create();

        /* 1. Affichage de la grille de couleur gris clair */
        g2D.setColor(Color.LIGHT_GRAY);

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

        /* 2. Dessin de la VuePiece associée (seulement s'il y en a une) */
        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }

        /* Libération de la mémoire */
        g2D.dispose();
    }
}