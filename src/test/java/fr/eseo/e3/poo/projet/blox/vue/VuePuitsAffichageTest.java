package fr.eseo.e3.poo.projet.blox.vue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;

public class VuePuitsAffichageTest {

    // Méthode principale (point d'entrée) pour lancer nos tests d'affichage
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VuePuitsAffichageTest test = new VuePuitsAffichageTest();
                test.testConstructeurPuits();
                test.testConstructeurPuitsTaille();
            }
        });
    }

    // Test 1 : Fenêtre avec le constructeur à 1 paramètre
    private void testConstructeurPuits() {
        Puits puits = new Puits(); // Utilise la dimension par défaut du modèle (souvent 10x15)
        VuePuits vuePuits = new VuePuits(puits);

        JFrame fenetre = new JFrame("Puits");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // S'arrête à la fermeture
        fenetre.add(vuePuits); // Ajout physique de l'instance
        fenetre.pack(); // Modifie la taille en fonction de la taille de préférence
        fenetre.setLocationRelativeTo(null); // Centre sur l'écran

        // Petite astuce (non demandée, mais utile) pour que les fenêtres ne se superposent pas exactement
        fenetre.setLocation(fenetre.getX() - 150, fenetre.getY());

        fenetre.setVisible(true);
    }

    // Test 2 : Fenêtre avec le constructeur à 2 paramètres
    private void testConstructeurPuitsTaille() {
        Puits puits = new Puits();
        VuePuits vuePuits = new VuePuits(puits, 35); // Taille des éléments beaucoup plus grande (35 pixels)

        JFrame fenetre = new JFrame("Puits et taille");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // S'arrête à la fermeture
        fenetre.add(vuePuits); // Ajout physique de l'instance
        fenetre.pack(); // Modifie la taille en fonction de la taille de préférence
        fenetre.setLocationRelativeTo(null); // Centre sur l'écran

        // Décalage pour voir les deux fenêtres simultanément
        fenetre.setLocation(fenetre.getX() + 150, fenetre.getY());

        fenetre.setVisible(true);
    }
}