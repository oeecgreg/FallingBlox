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
    private int score = 0;
    private int lignes = 0; // NOUVEAU

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
            this.score = (int) event.getNewValue();
            this.repaint();
        }
        // NOUVEAU
        else if (Puits.MODIFICATION_LIGNES.equals(event.getPropertyName())) {
            this.lignes = (int) event.getNewValue();
            this.repaint();
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
        // B. Dessin des informations (en bas)
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial", Font.BOLD, 14));

        int niveau = this.lignes / 10;

        String texteScore = "Score : " + this.score;
        String texteLignes = "Lignes : " + this.lignes;
        String texteNiveau = "Niveau : " + niveau;

        java.awt.FontMetrics fm = g2D.getFontMetrics();

        // On affiche les 3 textes les uns sous les autres
        g2D.drawString(texteNiveau, (this.getWidth() - fm.stringWidth(texteNiveau)) / 2, this.getHeight() - 45);
        g2D.drawString(texteLignes, (this.getWidth() - fm.stringWidth(texteLignes)) / 2, this.getHeight() - 25);
        g2D.drawString(texteScore, (this.getWidth() - fm.stringWidth(texteScore)) / 2, this.getHeight() - 5);
        g2D.dispose();
    }
}