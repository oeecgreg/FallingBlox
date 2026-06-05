package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class ControleurClavier extends KeyAdapter {

    private VuePuits vuePuits;
    private Puits puits;

    public ControleurClavier(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits = vuePuits.getPuits();
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        // On vérifie que le jeu est en cours
        if (this.puits != null && this.puits.getPieceActuelle() != null && !this.puits.isPartieTerminee()) {
            Piece piece = this.puits.getPieceActuelle();

            // 1. Touche pour la Pause (P)
            if (event.getKeyCode() == KeyEvent.VK_P) {
                this.puits.setPartieEnPause(!this.puits.isPartieEnPause()); // Inverse l'état
                this.vuePuits.repaint();
                return; // On s'arrête là
            }

            // 2. Si le jeu est en pause, on bloque les mouvements !
            if (this.puits.isPartieEnPause()) {
                return;
            }

            try {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        piece.deplacerDe(-1, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        piece.deplacerDe(1, 0);
                        break;
                    case KeyEvent.VK_UP:
                        piece.tourner(true); // Flèche haut = rotation horaire
                        break;
                    case KeyEvent.VK_DOWN:
                        // Pour la descente, on utilise la gravité du puits
                        // pour bénéficier de la gestion automatique des collisions !
                        this.puits.gravite();
                        break;
                    // NOUVEAU : Touche C pour échanger la pièce
                    case KeyEvent.VK_C:
                        this.puits.echangerPiece();
                        break;
                }
                this.vuePuits.repaint();
            } catch (BloxException | IllegalArgumentException e) {
                // Mouvement bloqué par un mur ou une pièce, on ignore silencieusement
            }
        }
    }
}