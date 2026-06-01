package fr.eseo.e3.poo.projet.blox.controleur;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class PieceDeplacementTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PieceDeplacementTest().testDeplacementEtMoletteSouris();
            }
        });
    }

    private void testDeplacementEtMoletteSouris() {
        // 1. Initialisation du Puits (Modèle)
        Puits puits = new Puits();

        // 2. Initialisation de la Vue (Le contrôleur PieceDeplacement y est automatiquement attaché aux 3 écouteurs)
        VuePuits vuePuits = new VuePuits(puits);

        // 3. Ajout des pièces pour faire entrer la première sur le plateau
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        // 4. On téléporte la pièce vers le milieu (ordonnée 2 ou 3) pour avoir de la place en dessous pour tester la chute
        if (puits.getPieceActuelle() != null) {
            puits.getPieceActuelle().setPositions(puits.getLargeur() / 2, 2);
        }

        // 5. Création et affichage de la fenêtre avec le titre mis à jour
        JFrame fenetre = new JFrame("Test Contrôleur : Déplacement X et Roulette Y");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setVisible(true);
    }
}