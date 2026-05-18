package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;

import static org.junit.jupiter.api.Assertions.*;

public class UsineDePieceTest {

    @Test
    public void testCoordonneesParDefaut() {
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        Tetromino piece = UsineDePiece.genererTetromino();

        assertNotNull(piece, "L'usine doit générer une pièce");
        // On vérifie que la coordonnée de référence est bien (2, 3) comme demandé
        assertEquals(new Coordonnees(2, 3), piece.getElements()[0].getCoordonnees(),
                "La pièce générée doit être aux coordonnées initiales (2, 3)");
    }

    @Test
    public void testModeCyclic() {
        UsineDePiece.setMode(UsineDePiece.CYCLIC);

        // 1. La première pièce doit être un OTetromino (rouge).
        Tetromino piece1 = UsineDePiece.genererTetromino();
        assertInstanceOf(OTetromino.class, piece1, "La 1ère pièce cyclique doit être un OTetromino");
        assertEquals(Couleur.ROUGE, piece1.getElements()[0].getCouleur(), "La couleur du OTetromino doit être rouge");

        // 2. La deuxième pièce doit être un ITetromino (orange).
        Tetromino piece2 = UsineDePiece.genererTetromino();
        assertInstanceOf(ITetromino.class, piece2, "La 2ème pièce cyclique doit être un ITetromino");
        assertEquals(Couleur.ORANGE, piece2.getElements()[0].getCouleur(), "La couleur du ITetromino doit être orange");

        // 3. La troisième pièce doit de nouveau être un OTetromino (ça boucle).
        Tetromino piece3 = UsineDePiece.genererTetromino();
        assertInstanceOf(OTetromino.class, piece3, "La 3ème pièce cyclique doit recommencer sur un OTetromino");
    }

    @Test
    public void testModeAleatoirePieceCouleurs() {
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);

        // On génère plusieurs pièces pour s'assurer que les couleurs par défaut sont respectées
        for (int i = 0; i < 50; i++) {
            Tetromino piece = UsineDePiece.genererTetromino();
            if (piece instanceof OTetromino) {
                assertEquals(Couleur.ROUGE, piece.getElements()[0].getCouleur(), "En mode ALEATOIRE_PIECE, le O doit être ROUGE");
            } else if (piece instanceof ITetromino) {
                assertEquals(Couleur.ORANGE, piece.getElements()[0].getCouleur(), "En mode ALEATOIRE_PIECE, le I doit être ORANGE");
            }
        }
    }

    @Test
    public void testModeAleatoireComplet() {
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_COMPLET);
        Tetromino piece = UsineDePiece.genererTetromino();
        // On s'assure juste que l'aléatoire total ne fait pas planter le programme.
        assertNotNull(piece, "Le mode ALEATOIRE_COMPLET doit générer une pièce valide");
    }
}