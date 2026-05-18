package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import org.junit.jupiter.api.Test;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;

public class ITetrominoTest {

    @Test
    public void testPositionsRelatives() {
        ITetromino iPiece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);
        Element[] elms = iPiece.getElements();

        // On vérifie l'alignement vertical sur l'abscisse 5.
        assertEquals(new Coordonnees(5, 5), elms[0].getCoordonnees());
        assertEquals(new Coordonnees(5, 4), elms[1].getCoordonnees());
        assertEquals(new Coordonnees(5, 3), elms[2].getCoordonnees());
        assertEquals(new Coordonnees(5, 6), elms[3].getCoordonnees());
    }

    @Test
    public void testDeplacerDeValide() {
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);

        // 1. On mémorise les positions initiales des 4 éléments avant le mouvement
        int[] xInitiaux = new int[4];
        int[] yInitiaux = new int[4];

        for (int i = 0; i < 4; i++) {
            xInitiaux[i] = piece.getElements()[i].getCoordonnees().getAbscisse();
            yInitiaux[i] = piece.getElements()[i].getCoordonnees().getOrdonnee();
        }

        // 2. On déplace la pièce vers le bas (+1 en Y).
        piece.deplacerDe(0, 1);

        // 3. On vérifie que chaque élément a bien bougé par rapport à SA position initiale
        for (int i = 0; i < 4; i++) {
            assertEquals(xInitiaux[i], piece.getElements()[i].getCoordonnees().getAbscisse(),
                    "L'abscisse de l'élément " + i + " ne devrait pas changer");

            assertEquals(yInitiaux[i] + 1, piece.getElements()[i].getCoordonnees().getOrdonnee(),
                    "L'ordonnée de l'élément " + i + " aurait dû augmenter de 1");
        }
    }

    @Test
    public void testDeplacerDeInvalideVersLeHaut() {
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1),
                "Un déplacement vers le haut (-1 en Y) doit lever une exception");
    }

    @Test
    public void testDeplacerDeInvalideTropLoin() {
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0),
                "Un déplacement de plus d'une case (2 en X) doit lever une exception");
    }

    @Test
    public void testDeplacerDeInvalideDiagonale() {
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1),
                "Un déplacement en diagonale (1 en X et 1 en Y) doit lever une exception");
    }
}