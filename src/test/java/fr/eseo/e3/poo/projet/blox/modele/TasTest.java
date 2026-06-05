package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class TasTest {

    @Test
    public void testConstructeursVides() {
        // Test du constructeur par défaut
        Tas tasDefaut = new Tas();
        assertNotNull(tasDefaut.getElements(), "La liste d'éléments ne doit pas être null");
        assertTrue(tasDefaut.getElements().isEmpty(), "Le tas par défaut doit être vide");

        // Test du constructeur à un argument
        Puits puits = new Puits();
        Tas tasUnArg = new Tas(puits);
        assertEquals(puits, tasUnArg.getPuits(), "Le puits associé doit être correct");
        assertTrue(tasUnArg.getElements().isEmpty(), "Le tas à un argument doit être initialement vide");
    }

    @Test
    public void testConstruireTasValideAvecSeed() {
        Puits puits = new Puits(10, 15);
        // Utilisation d'un Random avec un seed fixe (12345) pour garantir la reproductibilité
        Random rand = new Random(12345);
        Tas tas = new Tas(puits, 7, 3, rand);

        // Vérification du nombre d'éléments générés
        assertEquals(7, tas.getElements().size(), "Le tas doit contenir exactement 7 éléments");

        // Vérification que les briques sont bien positionnées tout en bas du puits
        // Bornes de Y : profondeur (15) - nbLignes (3) = ligne 12 incluse jusqu'à 14 incluse
        for (Element element : tas.getElements()) {
            int x = element.getCoordonnees().getAbscisse();
            int y = element.getCoordonnees().getOrdonnee();

            assertTrue(x >= 0 && x < puits.getLargeur(), "L'abscisse doit être dans les limites du puits");
            assertTrue(y >= 12 && y < puits.getProfondeur(), "L'ordonnée doit être confinée aux 3 lignes du bas");
        }
    }

    @Test
    public void testConstruireTasInvalideExceptions() {
        Puits puits = new Puits(10, 15);

        // Cas invalide 1 : Nombre d'éléments négatif
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, -5, 2),
                "Un nombre d'éléments négatif doit lever une exception");

        // Cas invalide 2 : Nombre de lignes négatif
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 5, -1),
                "Un nombre de lignes négatif doit lever une exception");

        // Cas invalide 3 : Des éléments demandés mais 0 lignes allouées
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 5, 0),
                "Demander des éléments sur 0 lignes doit lever une exception");

        // Cas invalide 4 : Nombre de lignes supérieur à la profondeur totale du puits
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 5, 20),
                "Un nombre de lignes supérieur à la profondeur du puits doit lever une exception");

        // Cas invalide 5 : Trop d'éléments par rapport à la grille disponible (Lignes * Largeur)
        // 2 lignes * 10 de largeur = 20 places max. On en demande 25.
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 25, 2),
                "Demander plus d'éléments que de places disponibles doit lever une exception");
    }

    @Test
    public void testAjouterElements() {
        Tas tas = new Tas(new Puits());
        Piece piece = new ITetromino(new Coordonnees(5, 14), Couleur.ORANGE);

        tas.ajouterElements(piece);

        assertEquals(4, tas.getElements().size(), "Le tas devrait contenir les 4 blocs de la pièce");
    }
}