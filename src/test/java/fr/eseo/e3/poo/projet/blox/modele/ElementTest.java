package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class ElementTest {

    @Test
    public void testConstructeurComplet() {
        Coordonnees coord = new Coordonnees(12, 7);
        Element element = new Element(coord, Couleur.VIOLET);
        assertEquals(12, element.getCoordonnees().getAbscisse(), "L'abscisse doit être 12");
        assertEquals(7, element.getCoordonnees().getOrdonnee(), "L'ordonnée doit être 7");
        assertEquals(Couleur.VIOLET, element.getCouleur(), "La couleur doit être VIOLET");
    }

    @Test
    public void testConstructeurCoordonneesSeules() {
        Coordonnees coord = new Coordonnees(5, 5);
        Element element = new Element(coord);
        // Le constructeur doit attribuer la première couleur de l'énumération par défaut
        assertEquals(Couleur.ROUGE, element.getCouleur(), "La couleur par défaut doit être la première de l'enum (ROUGE)");
    }

    @Test
    public void testConstructeurEntiersEtCouleur() {
        Element element = new Element(10, 15, Couleur.BLEU);
        assertEquals(10, element.getCoordonnees().getAbscisse(), "L'abscisse doit être 10");
        assertEquals(15, element.getCoordonnees().getOrdonnee(), "L'ordonnée doit être 15");
        assertEquals(Couleur.BLEU, element.getCouleur(), "La couleur doit être BLEU");
    }

    @Test
    public void testConstructeurEntiersSeuls() {
        Element element = new Element(20, 25);
        assertEquals(20, element.getCoordonnees().getAbscisse(), "L'abscisse doit être 20");
        assertEquals(25, element.getCoordonnees().getOrdonnee(), "L'ordonnée doit être 25");
        assertEquals(Couleur.ROUGE, element.getCouleur(), "La couleur par défaut doit être ROUGE");
    }

    @Test
    public void testToString() {
        Element element = new Element(12, 7, Couleur.VIOLET);
        // Le format doit être exactement "(12, 7) | VIOLET".
        assertEquals("(12, 7) - VIOLET", element.toString(), "Le format de toString est incorrect");
    }

    @Test
    public void testEqualsEtHashCode() {
        Element e1 = new Element(10, 10, Couleur.VERT);
        Element e2 = new Element(10, 10, Couleur.VERT);
        Element e3 = new Element(10, 10, Couleur.BLEU);
        Element e4 = new Element(5, 10, Couleur.VERT);

        assertEquals(e1, e2, "Deux éléments avec les mêmes coordonnées et couleur doivent être égaux");
        assertEquals(e1.hashCode(), e2.hashCode(), "Deux objets égaux doivent avoir le même hashCode");

        assertNotEquals(e1, e3, "Deux éléments de couleurs différentes ne doivent pas être égaux");
        assertNotEquals(e1, e4, "Deux éléments de coordonnées différentes ne doivent pas être égaux");
    }

    @Test
    public void testDeplacerDe() {
        Element element = new Element(new Coordonnees(5, 5));

        // Déplacement vers la droite
        element.deplacerDe(1, 0);
        assertEquals(6, element.getCoordonnees().getAbscisse(), "L'abscisse devrait être 6 après un déplacement à droite");
        assertEquals(5, element.getCoordonnees().getOrdonnee(), "L'ordonnée ne devrait pas changer");

        // Déplacement vers le bas
        element.deplacerDe(0, 1);
        assertEquals(6, element.getCoordonnees().getAbscisse(), "L'abscisse ne devrait pas changer");
        assertEquals(6, element.getCoordonnees().getOrdonnee(), "L'ordonnée devrait être 6 après un déplacement vers le bas");

        // Déplacement vers la gauche
        element.deplacerDe(-1, 0);
        assertEquals(5, element.getCoordonnees().getAbscisse(), "L'abscisse devrait revenir à 5 après un déplacement à gauche");
        assertEquals(6, element.getCoordonnees().getOrdonnee(), "L'ordonnée ne devrait pas changer");
    }
}