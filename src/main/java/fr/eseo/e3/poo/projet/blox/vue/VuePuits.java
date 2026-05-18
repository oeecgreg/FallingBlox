package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

// La classe implémente désormais formellement le contrat PropertyChangeListener
public class VuePuits extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 20;

    private Puits puits;
    private int taille;
    private VuePiece vuePiece;

    // --- Constructeurs ---

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;
        this.vuePiece = null;

        this.setBackground(Color.WHITE);
        this.mettreAJourTaille();

        // Inscription de la vue auprès du puits initial s'il existe
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
        }
    }

    // --- Accesseurs et Mutateurs ---

    public Puits getPuits() {
        return this.puits;
    }

    public void setPuits(Puits puits) {
        // Désinscription auprès de l'ancien Puits si nécessaire
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }

        this.puits = puits;

        // Inscription auprès du nouveau Puits
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
        }

        this.mettreAJourTaille();
    }

    public int getTaille() {
        return this.taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
        this.mettreAJourTaille();
    }

    public VuePiece getVuePiece() {
        return this.vuePiece;
    }

    // Exigence : Visibilité modifiée en 'private' pour sécuriser l'encapsulation
    private void setVuePiece(VuePiece vuePiece) {
        this.vuePiece = vuePiece;
        this.repaint(); // Force le rafraîchissement graphique immédiat de l'écran
    }

    private void mettreAJourTaille() {
        if (this.puits != null) {
            int largeurPixels = this.puits.getLargeur() * this.taille;
            int profondeurPixels = this.puits.getProfondeur() * this.taille;
            this.setPreferredSize(new Dimension(largeurPixels, profondeurPixels));
        }
    }

    // --- Réception Automatique des Notifications ---
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // On vérifie si la notification concerne un changement de la pièce actuelle
        if (Puits.MODIFICATION_PIECE_ACTUELLE.equals(evt.getPropertyName())) {
            Piece nouvellePieceActuelle = (Piece) evt.getNewValue();

            if (nouvellePieceActuelle != null) {
                // Création automatique de la VuePiece adaptée
                this.setVuePiece(new VuePiece(nouvellePieceActuelle, this.taille));
            } else {
                this.setVuePiece(null);
            }
        }
    }

    // --- Dessin du composant ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setColor(Color.LIGHT_GRAY);

        int largeurMax = this.puits.getLargeur() * this.taille;
        int profondeurMax = this.puits.getProfondeur() * this.taille;

        for (int x = 0; x <= largeurMax; x += this.taille) {
            g2D.drawLine(x, 0, x, profondeurMax);
        }

        for (int y = 0; y <= profondeurMax; y += this.taille) {
            g2D.drawLine(0, y, largeurMax, y);
        }

        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }

        g2D.dispose();
    }
}