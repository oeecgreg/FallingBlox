package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;


public class PieceDeplacement extends MouseAdapter {

    private VuePuits vuePuits;
    private Puits puits;
    private int colonnePrecedente;

    // --- Constructeur ---
    public PieceDeplacement(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits = vuePuits.getPuits();
        this.colonnePrecedente = -1;
    }

    // --- Accesseurs et Mutateurs ---
    public void setVuePuits(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    // --- Surcharge des événements souris (MouseAdapter) ---

    @Override
    public void mouseMoved(MouseEvent event) {
        if (this.puits != null && this.puits.getPieceActuelle() != null && !this.puits.isPartieTerminee()) {
            int colonneActuelle = event.getX() / this.vuePuits.getTaille();

            if (this.colonnePrecedente == -1) {
                this.colonnePrecedente = colonneActuelle;
            } else if (colonneActuelle != this.colonnePrecedente) {
                Piece piece = this.puits.getPieceActuelle();

                // AJOUT DU BLOC TRY...CATCH
                try {
                    if (colonneActuelle > this.colonnePrecedente) {
                        piece.deplacerDe(1, 0);
                    } else {
                        piece.deplacerDe(-1, 0);
                    }
                    this.vuePuits.repaint();
                } catch (IllegalArgumentException | BloxException e) {
                    // Si la pièce sort du puits ou cogne le tas, l'action est ignorée silencieusement
                }

                this.colonnePrecedente = colonneActuelle;
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent event) {
        // Réinitialise la colonne quand la souris rentre dans le panneau
        // Cela évite un "saut" brusque de la pièce.
        this.colonnePrecedente = -1;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (this.puits != null && this.puits.getPieceActuelle() != null && !this.puits.isPartieTerminee()) {
            if (event.getWheelRotation() > 0) {
                // AJOUT DU BLOC TRY...CATCH
                try {
                    this.puits.getPieceActuelle().deplacerDe(0, 1);
                    this.vuePuits.repaint();
                } catch (IllegalArgumentException | BloxException e) {
                    // Si la pièce touche le fond ou le tas, la chute forcée s'arrête
                }
            }
        }
    }
}