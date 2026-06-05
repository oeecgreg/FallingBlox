package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class PanneauInformation extends JPanel implements PropertyChangeListener {

    private VuePiece vuePiece;

    public PanneauInformation(Puits puits) {
        // 1. Enregistrer le panneau en tant qu'écouteur du Puits
        if (puits != null) {
            puits.addPropertyChangeListener(this);
        }

        // 2. Définir la taille de préférence à 70x70 pixels
        this.setPreferredSize(new Dimension(70, 70));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // On vérifie si c'est bien la pièce SUIVANTE qui a été modifiée
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(event.getPropertyName())) {
            Piece prochainePiece = (Piece) event.getNewValue();

            if (prochainePiece != null) {
                // On crée une VuePiece avec une taille de carreau fixée à 10 pixels
                this.vuePiece = new VuePiece(prochainePiece, 10);
            } else {
                this.vuePiece = null;
            }
            // On demande à Swing de redessiner le panneau
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.vuePiece != null) {
            Graphics2D g2D = (Graphics2D) g.create();
            this.vuePiece.afficherPiece(g2D);
            g2D.dispose();
        }
    }
}