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
        // --- MODIFICATION ICI : Création d'un puits avec un Tas pré-rempli ---
        // Puits de 10x15, contenant 15 éléments aléatoires répartis sur les 3 lignes du bas
        Puits puits = new Puits(10, 15, 15, 3);

        VuePuits vuePuits = new VuePuits(puits);

        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);

        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        // On force la pièce actuelle à descendre à l'ordonnée 5 pour la voir tomber vers le tas !
        puits.getPieceActuelle().setPositions(puits.getLargeur() / 2, 5);

        JFrame fenetre = new JFrame("Puits avec Tas au fond");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setLocation(fenetre.getX() - 150, fenetre.getY());
        fenetre.setVisible(true);
    }

    private void testConstructeurPuitsTaille() {
        // --- MODIFICATION ICI : Un tas un peu plus grand pour bien voir la différence ---
        // Puits de 10x15, contenant 30 éléments aléatoires répartis sur les 5 lignes du bas
        Puits puits = new Puits(10, 15, 30, 5);

        VuePuits vuePuits = new VuePuits(puits, 35); // Grosses cases de 35 pixels

        UsineDePiece.setMode(UsineDePiece.CYCLIC);

        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        // On positionne la pièce un peu plus haut (ordonnée 3) pour éviter qu'elle n'apparaisse DANS le grand tas
        puits.getPieceActuelle().setPositions(puits.getLargeur() / 2, 3);

        JFrame fenetre = new JFrame("Puits, Taille et Grand Tas");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(vuePuits);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setLocation(fenetre.getX() + 150, fenetre.getY());
        fenetre.setVisible(true);
    }
}