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
        Puits puits = new Puits(); // 10x15 par défaut
        VuePuits vuePuits = new VuePuits(puits);

        // 1. Génération d'une pièce via l'UsineDePiece (Mode par défaut : ALEATOIRE_PIECE)
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        Piece piece = UsineDePiece.genererTetromino();
        puits.setPieceSuivante(piece); // Ajout de la pièce dans le modèle (Puits)

        // 2. Création de la VuePiece et association avec le VuePuits
        VuePiece vuePiece = new VuePiece(piece, vuePuits.getTaille());
        vuePuits.setVuePiece(vuePiece); // Liaison visuelle

        // 3. Configuration de la fenêtre (JFrame)
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
        VuePuits vuePuits = new VuePuits(puits, 35); // Gros carreaux de 35 pixels

        // 1. Génération d'une autre pièce via l'UsineDePiece (Mode CYCLIC pour voir une autre forme/couleur)
        UsineDePiece.setMode(UsineDePiece.CYCLIC);
        Piece piece = UsineDePiece.genererTetromino();
        puits.setPieceSuivante(piece); // Ajout de la pièce dans le modèle

        // 2. Création de la VuePiece et association avec le VuePuits
        VuePiece vuePiece = new VuePiece(piece, vuePuits.getTaille());
        vuePuits.setVuePiece(vuePiece); // Liaison visuelle

        // 3. Configuration de la fenêtre (JFrame)
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