package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class PanneauInformation extends JPanel implements PropertyChangeListener {

    private VuePiece vuePiece;
    // NOUVEAU : Attribut pour stocker la valeur du score à afficher
    private int score = 0;

    public PanneauInformation(Puits puits) {
        if (puits != null) {
            puits.addPropertyChangeListener(this);
            // On initialise l'affichage avec le score de départ du puits
            this.score = puits.getScore();
        }

        // Taille agrandie pour laisser de la place au texte en bas
        this.setPreferredSize(new Dimension(140, 140));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // 1. Si la notification concerne la pièce suivante
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(event.getPropertyName())) {
            Piece prochainePiece = (Piece) event.getNewValue();

            if (prochainePiece != null) {
                this.vuePiece = new VuePiece(prochainePiece, 20);
            } else {
                this.vuePiece = null;
            }
            this.repaint();
        }
        // 2. NOUVEAU : Si la notification concerne une modification du score
        else if (Puits.MODIFICATION_SCORE.equals(event.getPropertyName())) {
            // On met à jour la variable locale avec le nouveau score
            this.score = (int) event.getNewValue();
            this.repaint(); // On demande à redessiner le panneau
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();

        // A. Dessin de la pièce suivante (en haut)
        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }

        // B. NOUVEAU : Dessin du texte du Score (en bas)
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial", Font.BOLD, 16));
        String texteScore = "Score : " + this.score;

        // Petite formule mathématique pour centrer le texte horizontalement
        java.awt.FontMetrics fm = g2D.getFontMetrics();
        int largeurTexte = fm.stringWidth(texteScore);
        int coordX = (this.getWidth() - largeurTexte) / 2;
        int coordY = this.getHeight() - 20; // Placé 20 pixels au-dessus du bord inférieur

        g2D.drawString(texteScore, coordX, coordY);

        g2D.dispose();
    }
}