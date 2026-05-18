package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;

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
}