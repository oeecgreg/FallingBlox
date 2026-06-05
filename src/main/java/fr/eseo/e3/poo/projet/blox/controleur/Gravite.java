package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

/**
 * Contrôleur gérant la chute automatique de la pièce.
 * Il implémente ActionListener pour réagir aux impulsions d'un Timer Swing.
 */
public class Gravite implements ActionListener {

    private final VuePuits vuePuits;
    private Puits puits;
    private final Timer timer;

    /**
     * Constructeur.
     * Initialise le timer avec une vitesse par défaut (ex : 500 ms = une demi-seconde).
     */
    public Gravite(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits = vuePuits.getPuits();

        // Timer(délai_en_ms, écouteur)
        this.timer = new Timer(500, this);
        this.timer.start(); // On lance l'horloge immédiatement !
    }

    // --- Accesseurs et Mutateurs ---

    public int getPeriodicite() {
        return this.timer.getDelay();
    }

    public void setPeriodicite(int periodicite) {
        this.timer.setDelay(periodicite);
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    // --- Méthode appelée automatiquement à chaque "Tic" du Timer ---

    @Override
    public void actionPerformed(ActionEvent event) {
        // On stoppe le Timer si le jeu est fini
        if (this.puits != null && this.puits.isPartieTerminee()) {
            this.timer.stop();
            this.vuePuits.repaint(); // Un dernier rafraîchissement pour l'écran de fin
            return;
        }

        if (this.puits != null && this.puits.getPieceActuelle() != null) {
            this.puits.gravite();
            this.vuePuits.repaint();
        }
    }
}