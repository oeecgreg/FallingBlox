package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;

public class PuitsTest {

    @Test
    public void testConstructeurParDefaut() {
        Puits puits = new Puits();
        assertEquals(10, puits.getLargeur(), "La largeur par défaut doit être 10");
        assertEquals(15, puits.getProfondeur(), "La profondeur par défaut doit être 15");
        assertNull(puits.getPieceActuelle(), "La pièce actuelle doit être null au départ");
        assertNull(puits.getPieceSuivante(), "La pièce suivante doit être null au départ");
    }

    @Test
    public void testConstructeurAvecParametres() {
        Puits puits = new Puits(12, 20);
        assertEquals(12, puits.getLargeur());
        assertEquals(20, puits.getProfondeur());
    }

    @Test
    public void testExceptionsDimensions() {
        // Test de la largeur (doit être entre 5 et 15)
        assertThrows(IllegalArgumentException.class, () -> new Puits(4, 15), "Une largeur de 4 doit déclencher une exception");

        // Test de la profondeur (doit être entre 15 et 25)
        assertThrows(IllegalArgumentException.class, () -> new Puits(10, 30), "Une profondeur de 30 doit déclencher une exception");
    }

    @Test
    public void testGestionDesPieces() {
        Puits puits = new Puits(10, 20);
        OTetromino piece1 = new OTetromino(new Coordonnees(0, 0), Couleur.ROUGE);
        OTetromino piece2 = new OTetromino(new Coordonnees(0, 0), Couleur.BLEU);

        // Ajout de la première pièce
        puits.setPieceSuivante(piece1);
        assertNull(puits.getPieceActuelle(), "Il n'y a pas encore de pièce actuelle");
        assertEquals(piece1, puits.getPieceSuivante());
        assertEquals(puits, piece1.getPuits(), "La pièce doit connaître son puits");

        // Ajout de la deuxième pièce (la première devient actuelle)
        puits.setPieceSuivante(piece2);
        assertEquals(piece1, puits.getPieceActuelle(), "La pièce 1 devient la pièce actuelle");
        assertEquals(piece2, puits.getPieceSuivante(), "La pièce 2 est la nouvelle pièce suivante");

        // Vérification de la position (largeur / 2, -4) soit (5, -4) pour un puits de largeur 10
        assertEquals(new Coordonnees(5, -4), puits.getPieceActuelle().getElements()[0].getCoordonnees(),
                "La pièce actuelle doit être placée aux coordonnées (largeur/2, -4)");
    }

    @Test
    public void testToString() {
        Puits puits = new Puits(10, 15);

        // Test du toString sans aucune pièce
        String attenduVide = "Puits : Dimension 10 x 15\n" +
                "Piece Actuelle : <aucune>\n" +
                "Piece Suivante : <aucune>";
        assertEquals(attenduVide, puits.toString());
    }
}