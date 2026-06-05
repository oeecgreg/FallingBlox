package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class PieceRotation extends MouseAdapter {

    private VuePuits vuePuits;
    private Puits puits;

    // --- Constructeur ---
    public PieceRotation(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits = vuePuits.getPuits();
    }

    // --- Mutateurs ---
    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    // --- Gestion du clic de souris ---
    @Override
    public void mouseClicked(MouseEvent event) {
        if (this.puits != null && this.puits.getPieceActuelle() != null && !this.puits.isPartieTerminee()) {
            boolean rotationEffectuee = false;

            // AJOUT DU BLOC TRY...CATCH
            try {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    this.puits.getPieceActuelle().tourner(false);
                    rotationEffectuee = true;
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    this.puits.getPieceActuelle().tourner(true);
                    rotationEffectuee = true;
                }

                if (rotationEffectuee) {
                    this.vuePuits.repaint();
                }
            } catch (BloxException e) {
                // Si la rotation provoque une collision ou une sortie du puits, elle est annulée
            }
        }
    }
}