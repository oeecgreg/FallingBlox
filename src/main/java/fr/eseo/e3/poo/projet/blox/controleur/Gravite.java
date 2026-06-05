package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Timer;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class Gravite implements ActionListener, PropertyChangeListener {

    private final VuePuits vuePuits;
    private Puits puits;
    private final Timer timer;

    public Gravite(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.timer = new Timer(500, this); // Vitesse de départ : 500 ms
        this.setPuits(vuePuits.getPuits());
        this.timer.start();
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }
        this.puits = puits;
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // NOUVEAU : Si le nombre de lignes a changé, on recalcule la vitesse !
        if (Puits.MODIFICATION_LIGNES.equals(evt.getPropertyName())) {
            int lignes = (int) evt.getNewValue();
            int niveau = lignes / 10; // Niveau augmente toutes les 10 lignes

            // On réduit le délai de 40ms par niveau (avec une limite max de vitesse à 80ms)
            int nouveauDelai = Math.max(80, 500 - (niveau * 40));
            this.timer.setDelay(nouveauDelai);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (this.puits != null && this.puits.isPartieTerminee()) {
            this.timer.stop();
            this.vuePuits.repaint();
            return;
        }

        if (this.puits != null && this.puits.getPieceActuelle() != null && !this.puits.isPartieEnPause()) {
            this.puits.gravite();
            this.vuePuits.repaint();
        }
    }
}