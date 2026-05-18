package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}