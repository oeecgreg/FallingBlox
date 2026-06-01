package fr.eseo.e3.poo.projet.blox.controleur;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class PieceRotationTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PieceRotationTest().testRotationSouris();
            }
        });
    }

    private void testRotationSouris() {
        // 1. Initialisation du Puits (Modèle)
        Puits puits = new Puits();

        // 2. Initialisation de la Vue (Le contrôleur PieceRotation y est automatiquement attaché)
        VuePuits vuePuits = new VuePuits(puits, 30); // Taille de 30 pixels pour bien voir la rotation

        // 3. Configuration de l'Usine en mode CYCLIC
        // Le mode CYCLIC permet de s'assurer d'avoir une pièce qui peut tourner (comme le ITetromino)
        UsineDePiece.setMode(UsineDePiece.CYCLIC);

        // Ajout des pièces pour faire entrer la première sur le plateau
        puits.setPieceSuivante(UsineDePiece.genererTetromino()); // 1. Crée le carré (va dans "suivante")
        puits.setPieceSuivante(UsineDePiece.genererTetromino()); // 2. Le carré passe dans "actuelle", la barre va dans "suivante"
        puits.setPieceSuivante(UsineDePiece.genererTetromino()); // 3. La barre passe dans "actuelle" (le carré est poussé dehors !)

        // 4. On positionne la pièce au centre et suffisamment bas pour observer la rotation sans encombre
        if (puits.getPieceActuelle() != null) {
            puits.getPieceActuelle().setPositions(puits.getLargeur() / 2, 6);
        }

        // 5. Création et affichage de la fenêtre
        JFrame fenetre = new JFrame("Test Contrôleur : Rotation Clic Souris");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setVisible(true);
    }
}