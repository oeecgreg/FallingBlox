package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import org.junit.jupiter.api.Test;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;

import static org.junit.jupiter.api.Assertions.*;

public class OTetrominoTest {

    @Test
    public void testConstructionPosition() {
        Coordonnees ref = new Coordonnees(6, 5);
        OTetromino oTetromino = new OTetromino(ref, Couleur.CYAN);

        assertEquals(4, oTetromino.getElements().length, "Un OTetromino doit avoir 4 éléments");

        // Vérification des positions relatives selon l'énoncé (Figure 8)
        assertEquals(new Coordonnees(6, 5), oTetromino.getElements()[0].getCoordonnees());
        assertEquals(new Coordonnees(7, 5), oTetromino.getElements()[1].getCoordonnees());
        assertEquals(new Coordonnees(6, 4), oTetromino.getElements()[2].getCoordonnees());
        assertEquals(new Coordonnees(7, 4), oTetromino.getElements()[3].getCoordonnees());
    }

    @Test
    public void testSetPositions() {
        Coordonnees ref = new Coordonnees(6, 5);
        OTetromino oTetromino = new OTetromino(ref, Couleur.CYAN);

        // Déplacement de la pièce vers de nouvelles coordonnées (10, 10)
        oTetromino.setPositions(10, 10);

        // On vérifie que tous les éléments ont bien suivi le mouvement
        assertEquals(new Coordonnees(10, 10), oTetromino.getElements()[0].getCoordonnees());
        assertEquals(new Coordonnees(11, 10), oTetromino.getElements()[1].getCoordonnees());
        assertEquals(new Coordonnees(10, 9), oTetromino.getElements()[2].getCoordonnees());
        assertEquals(new Coordonnees(11, 9), oTetromino.getElements()[3].getCoordonnees());
    }

    @Test
    public void testCouleur() {
        OTetromino oTetromino = new OTetromino(new Coordonnees(0, 0), Couleur.JAUNE);
        assertEquals(Couleur.JAUNE, oTetromino.getElements()[0].getCouleur());
        assertEquals(Couleur.JAUNE, oTetromino.getElements()[3].getCouleur());
    }

    @Test
    public void testToString() {
        OTetromino oTetromino = new OTetromino(new Coordonnees(6, 5), Couleur.CYAN);

        // Le format doit être strictement celui de l'énoncé : Nom : \n \t Element.toString()
        String attendu = "OTetromino :\n" +
                "\t(6, 5) - CYAN\n" +
                "\t(7, 5) - CYAN\n" +
                "\t(6, 4) - CYAN\n" +
                "\t(7, 4) - CYAN";

        assertEquals(attendu, oTetromino.toString(), "Le format de toString ne correspond pas à l'énoncé");
    }

    @Test
    public void testDeplacerDeValide() {
        OTetromino piece = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);

        // ENCAPSULATION DANS UN TRY...CATCH
        try {
            piece.deplacerDe(-1, 0);
        } catch (BloxException e) {
            fail("Le déplacement valide a levé une BloxException de manière inattendue : " + e.getMessage());
        }

        assertEquals(4, piece.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(5, piece.getElements()[0].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeInvalideVersLeHaut() {
        OTetromino piece = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
    }

    @Test
    public void testDeplacerDeInvalideDiagonale() {
        OTetromino piece = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(-1, 1));
    }

    @Test
    public void testTourner() {
        OTetromino piece = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);

        int[] xInitiaux = new int[4];
        int[] yInitiaux = new int[4];
        for (int i = 0; i < 4; i++) {
            xInitiaux[i] = piece.getElements()[i].getCoordonnees().getAbscisse();
            yInitiaux[i] = piece.getElements()[i].getCoordonnees().getOrdonnee();
        }

        // ENCAPSULATION DANS UN TRY...CATCH
        try {
            piece.tourner(true);
        } catch (BloxException e) {
            fail("La rotation (qui ne fait rien) ne devrait pas lever d'exception : " + e.getMessage());
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(xInitiaux[i], piece.getElements()[i].getCoordonnees().getAbscisse());
            assertEquals(yInitiaux[i], piece.getElements()[i].getCoordonnees().getOrdonnee());
        }
    }

    @Test
    public void testDeplacerDeSortiePuitsGauche() {
        Puits puits = new Puits(10, 15);
        // Le carré occupe deux colonnes (ici 0 et 1). Son pivot est à 0.
        OTetromino piece = new OTetromino(new Coordonnees(0, 5), Couleur.ROUGE);
        piece.setPuits(puits);

        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(-1, 0);
        });
        assertEquals(BloxException.BLOX_SORTIE_PUITS, exception.getCode());
    }

    @Test
    public void testDeplacerDeSortiePuitsDroite() {
        Puits puits = new Puits(10, 15);
        // Le carré fait 2 de large. Si le pivot est à 9, le deuxième bloc est à X=10 (dehors).
        // Plaçons le pivot à 9 pour provoquer immédiatement l'erreur de bord.
        OTetromino piece = new OTetromino(new Coordonnees(9, 5), Couleur.ROUGE);
        piece.setPuits(puits);

        // Le constructeur ou le look-ahead du déplacement doit refuser
        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(0, 0); // La simple validation de sa position actuelle par rapport au puits échoue
        });
        assertEquals(BloxException.BLOX_SORTIE_PUITS, exception.getCode());
    }

    @Test
    public void testDeplacerDeCollisionFond() {
        Puits puits = new Puits(10, 15);
        // MODIFICATION ICI : On passe Y à 14 pour forcer la collision avec le fond (15)
        OTetromino piece = new OTetromino(new Coordonnees(5, 14), Couleur.ROUGE);
        piece.setPuits(puits);

        BloxException exception = assertThrows(BloxException.class, () -> {
            piece.deplacerDe(0, 1);
        }, "Le déplacement sous le fond du puits doit lever une BloxException");

        assertEquals(BloxException.BLOX_COLLISION, exception.getCode());
    }
}