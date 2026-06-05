package fr.eseo.e3.poo.projet.blox.controleur;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class GraviteTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraviteTest().testGraviteAutomatique();
            }
        });
    }

    private void testGraviteAutomatique() {
        // 1. Initialisation du Puits (Modèle)
        Puits puits = new Puits();

        // 2. Initialisation de la Vue
        VuePuits vuePuits = new VuePuits(puits);

        // 3. Ajout des pièces pour lancer le jeu
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        // 4. Création et affichage de la fenêtre (comme demandé par la consigne)
        JFrame fenetre = new JFrame("Test Contrôleur : Gravité Automatique");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setVisible(true);

        // 5. NOUVEAU : Démarrage du timer dans la classe de test !
        Gravite gravite = new Gravite(vuePuits);
    }
}