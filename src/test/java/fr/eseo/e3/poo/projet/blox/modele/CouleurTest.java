package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class CouleurTest {

    @Test
    public void testCouleursPourAffichage() {
        // On vérifie que chaque constante de notre énumération est bien
        // associée à la bonne couleur de la bibliothèque graphique Java (AWT).

        assertEquals(Color.RED, Couleur.ROUGE.getCouleurPourAffichage(), "ROUGE doit renvoyer Color.RED");
        assertEquals(Color.ORANGE, Couleur.ORANGE.getCouleurPourAffichage(), "ORANGE doit renvoyer Color.ORANGE");
        assertEquals(Color.YELLOW, Couleur.JAUNE.getCouleurPourAffichage(), "JAUNE doit renvoyer Color.YELLOW");
        assertEquals(Color.GREEN, Couleur.VERT.getCouleurPourAffichage(), "VERT doit renvoyer Color.GREEN");
        assertEquals(Color.CYAN, Couleur.CYAN.getCouleurPourAffichage(), "CYAN doit renvoyer Color.CYAN");
        assertEquals(Color.BLUE, Couleur.BLEU.getCouleurPourAffichage(), "BLEU doit renvoyer Color.BLUE");

        // Comme Color.PURPLE n'existe pas par défaut dans AWT, nous avons utilisé MAGENTA
        assertEquals(Color.MAGENTA, Couleur.VIOLET.getCouleurPourAffichage(), "VIOLET doit renvoyer Color.MAGENTA");
    }
}