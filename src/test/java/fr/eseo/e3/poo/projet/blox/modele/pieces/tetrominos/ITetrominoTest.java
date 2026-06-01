package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

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

        int[] xInitiaux = new int[4];
        int[] yInitiaux = new int[4];

        for (int i = 0; i < 4; i++) {
            xInitiaux[i] = piece.getElements()[i].getCoordonnees().getAbscisse();
            yInitiaux[i] = piece.getElements()[i].getCoordonnees().getOrdonnee();
        }

        // ENCAPSULATION DANS UN TRY...CATCH
        try {
            piece.deplacerDe(0, 1);
        } catch (BloxException e) {
            fail("Le déplacement valide a levé une BloxException de manière inattendue : " + e.getMessage());
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(xInitiaux[i], piece.getElements()[i].getCoordonnees().getAbscisse());
            assertEquals(yInitiaux[i] + 1, piece.getElements()[i].getCoordonnees().getOrdonnee());
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

    @Test
    public void testTournerSensHoraire() {
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);

        int pivotX = piece.getElements()[0].getCoordonnees().getAbscisse();
        int pivotY = piece.getElements()[0].getCoordonnees().getOrdonnee();

        int[] dxInitiaux = new int[4];
        int[] dyInitiaux = new int[4];
        for (int i = 1; i < 4; i++) {
            dxInitiaux[i] = piece.getElements()[i].getCoordonnees().getAbscisse() - pivotX;
            dyInitiaux[i] = piece.getElements()[i].getCoordonnees().getOrdonnee() - pivotY;
        }

        // ENCAPSULATION DANS UN TRY...CATCH
        try {
            piece.tourner(true);
        } catch (BloxException e) {
            fail("La rotation valide a levé une BloxException de manière inattendue : " + e.getMessage());
        }

        for (int i = 1; i < 4; i++) {
            int expectedX = pivotX - dyInitiaux[i];
            int expectedY = pivotY + dxInitiaux[i];
            assertEquals(expectedX, piece.getElements()[i].getCoordonnees().getAbscisse());
            assertEquals(expectedY, piece.getElements()[i].getCoordonnees().getOrdonnee());
        }
    }

    @Test
    public void testTournerSensAntiHoraire() {
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.ORANGE);

        int pivotX = piece.getElements()[0].getCoordonnees().getAbscisse();
        int pivotY = piece.getElements()[0].getCoordonnees().getOrdonnee();

        int[] dxInitiaux = new int[4];
        int[] dyInitiaux = new int[4];
        for (int i = 1; i < 4; i++) {
            dxInitiaux[i] = piece.getElements()[i].getCoordonnees().getAbscisse() - pivotX;
            dyInitiaux[i] = piece.getElements()[i].getCoordonnees().getOrdonnee() - pivotY;
        }

        // ENCAPSULATION DANS UN TRY...CATCH
        try {
            piece.tourner(false);
        } catch (BloxException e) {
            fail("La rotation valide a levé une BloxException de manière inattendue : " + e.getMessage());
        }

        for (int i = 1; i < 4; i++) {
            int expectedX = pivotX + dyInitiaux[i];
            int expectedY = pivotY - dxInitiaux[i];
            assertEquals(expectedX, piece.getElements()[i].getCoordonnees().getAbscisse());
            assertEquals(expectedY, piece.getElements()[i].getCoordonnees().getOrdonnee());
        }
    }

    @Test
    public void testDeplacerDeSortiePuitsGauche() {
        Puits puits = new Puits(10, 15);
        // On place la pièce tout à gauche (X = 0)
        ITetromino piece = new ITetromino(new Coordonnees(0, 5), Couleur.ORANGE);
        piece.setPuits(puits);

        // Essayer de se déplacer encore à gauche doit lever BLOX_SORTIE_PUITS
        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(-1, 0);
        }, "Le déplacement au-delà du mur gauche doit lever une BloxException");

        assertEquals(BloxException.BLOX_SORTIE_PUITS, exception.getCode(),
                "Le code de l'exception doit être BLOX_SORTIE_PUITS");
    }

    @Test
    public void testDeplacerDeSortiePuitsDroite() {
        Puits puits = new Puits(10, 15);
        // On place la pièce tout à droite (X = 9 pour une largeur de 10)
        ITetromino piece = new ITetromino(new Coordonnees(9, 5), Couleur.ORANGE);
        piece.setPuits(puits);

        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(1, 0);
        }, "Le déplacement au-delà du mur droit doit lever une BloxException");

        assertEquals(BloxException.BLOX_SORTIE_PUITS, exception.getCode());
    }

    @Test
    public void testDeplacerDeCollisionFond() {
        Puits puits = new Puits(10, 15);
        // On place la pièce tout en bas (Y = 14 pour une profondeur de 15)
        ITetromino piece = new ITetromino(new Coordonnees(5, 14), Couleur.ORANGE);
        piece.setPuits(puits);

        // Essayer de descendre doit lever BLOX_COLLISION
        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(0, 1);
        }, "Le déplacement sous le fond du puits doit lever une BloxException");

        assertEquals(BloxException.BLOX_COLLISION, exception.getCode(),
                "Le code de l'exception doit être BLOX_COLLISION");
    }

    @Test
    public void testDeplacerDeCollisionTas() {
        Puits puits = new Puits(10, 15);
        Tas tas = puits.getTas();
        // On ajoute manuellement un élément fixe dans le tas à la position (5, 8)
        tas.getElements().add(new Element(new Coordonnees(5, 8), Couleur.BLEU));

        // On place la pièce juste au-dessus de cet élément (à la position 5, 7)
        ITetromino piece = new ITetromino(new Coordonnees(5, 7), Couleur.ORANGE);
        piece.setPuits(puits);

        // Tenter de descendre sur l'élément du tas doit être bloqué
        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(0, 1);
        }, "Le déplacement sur un bloc du tas doit lever une BloxException");

        assertEquals(BloxException.BLOX_COLLISION, exception.getCode());
    }

    @Test
    public void testTournerSortiePuits() {
        Puits puits = new Puits(10, 15);
        // On place une grande barre contre le mur gauche
        ITetromino piece = new ITetromino(new Coordonnees(0, 5), Couleur.ORANGE);
        piece.setPuits(puits);

        // Si la rotation fait sortir un des éléments à un index X négatif, elle doit échouer
        assertThrows(BloxException.class, () -> {
            piece.tourner(true);
        }, "La rotation forçant la pièce à sortir du puits doit lever une BloxException");
    }
}