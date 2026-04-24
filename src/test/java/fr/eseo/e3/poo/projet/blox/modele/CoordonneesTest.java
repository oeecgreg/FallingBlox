package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class CoordonneesTest {

    @Test
    public void testConstructeurEtGetters() {
        Coordonnees coord = new Coordonnees(5, 10);
        assertEquals(5, coord.getAbscisse(), "L'abscisse doit être 5");
        assertEquals(10, coord.getOrdonnee(), "L'ordonnée doit être 10");
    }

    @Test
    public void testSetters() {
        Coordonnees coord = new Coordonnees(0, 0);
        coord.setAbscisse(15);
        coord.setOrdonnee(20);
        assertEquals(15, coord.getAbscisse(), "L'abscisse doit être mise à jour à 15");
        assertEquals(20, coord.getOrdonnee(), "L'ordonnée doit être mise à jour à 20");
    }

    @Test
    public void testToString() {
        Coordonnees coord = new Coordonnees(15, 12);
        // Le format doit être exactement "(15, 12)" avec un espace après la virgule
        assertEquals("(15, 12)", coord.toString(), "Le format de toString est incorrect");
    }

    @Test
    public void testEqualsEtHashCode() {
        Coordonnees c1 = new Coordonnees(5, 5);
        Coordonnees c2 = new Coordonnees(5, 5);
        Coordonnees c3 = new Coordonnees(10, 10);

        assertEquals(c1, c2, "Deux coordonnées avec les mêmes valeurs doivent être égales");
        assertEquals(c1.hashCode(), c2.hashCode(), "Deux objets égaux doivent avoir le même hashCode");
        assertNotEquals(c1, c3, "Des coordonnées différentes ne doivent pas être égales");
    }
}