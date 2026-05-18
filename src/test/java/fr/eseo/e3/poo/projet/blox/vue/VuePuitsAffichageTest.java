package fr.eseo.e3.poo.projet.blox.vue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class VuePuitsAffichageTest {

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

    private void testConstructeurPuits() {
        Puits puits = new Puits();
        VuePuits vuePuits = new VuePuits(puits);

        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);

        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        // --- LA LIGNE MAGIQUE POUR LE TEST ---
        // On force la pièce actuelle à descendre à l'ordonnée 5 pour la voir !
        puits.getPieceActuelle().setPositions(puits.getLargeur() / 2, 5);

        JFrame fenetre = new JFrame("Puits");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setLocation(fenetre.getX() - 150, fenetre.getY());
        fenetre.setVisible(true);
    }

    private void testConstructeurPuitsTaille() {
        Puits puits = new Puits();
        VuePuits vuePuits = new VuePuits(puits, 35);

        UsineDePiece.setMode(UsineDePiece.CYCLIC);

        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        // --- LA LIGNE MAGIQUE POUR LE TEST ---
        puits.getPieceActuelle().setPositions(puits.getLargeur() / 2, 5);

        JFrame fenetre = new JFrame("Puits et taille");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setLocation(fenetre.getX() + 150, fenetre.getY());
        fenetre.setVisible(true);
    }
}