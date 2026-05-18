package fr.eseo.e3.poo.projet.blox;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class FallingBlox {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater est la bonne pratique pour lancer une interface graphique en Java
        SwingUtilities.invokeLater(() -> {
            // 1. Création du modèle (le Puits)
            Puits puits = new Puits(); // Utilise les dimensions par défaut (10x15)

            // 2. Création de la vue associée
            VuePuits vuePuits = new VuePuits(puits);

            // 3. Création de la fenêtre (JFrame)
            JFrame fenetre = new JFrame("Falling Blox");

            // 4. Configuration de la fenêtre
            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quitte l'application quand on ferme la fenêtre
            fenetre.setLayout(new BorderLayout());
            fenetre.add(vuePuits, BorderLayout.CENTER); // Ajoute notre zone de dessin au centre

            fenetre.pack(); // Ajuste la taille de la fenêtre à la taille de préférence de VuePuits
            fenetre.setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
            fenetre.setResizable(false); // Empêche le redimensionnement pour ne pas casser la grille

            // 5. Affichage !
            fenetre.setVisible(true);
        });
    }
}