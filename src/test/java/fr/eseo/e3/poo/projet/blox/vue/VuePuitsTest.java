package fr.eseo.e3.poo.projet.blox.vue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.Puits;

public class VuePuitsTest {

    @Test
    public void testConstructeurPuitsSeul() {
        Puits puits = new Puits(10, 15);
        VuePuits vue = new VuePuits(puits);

        assertEquals(puits, vue.getPuits(), "La vue doit référencer le bon Puits");
        assertEquals(VuePuits.TAILLE_PAR_DEFAUT, vue.getTaille(), "La taille par défaut doit être utilisée");

        // NOUVEAU : Vérification de la taille de préférence (10*20 = 200, 15*20 = 300)
        Dimension dimensionAttendue = new Dimension(10 * VuePuits.TAILLE_PAR_DEFAUT, 15 * VuePuits.TAILLE_PAR_DEFAUT);
        assertEquals(dimensionAttendue, vue.getPreferredSize(), "La taille de préférence (par défaut) est incorrecte");
    }

    @Test
    public void testConstructeurPuitsEtTaille() {
        Puits puits = new Puits(12, 20);
        int taillePersonnalisee = 30;
        VuePuits vue = new VuePuits(puits, taillePersonnalisee);

        assertEquals(puits, vue.getPuits(), "La vue doit référencer le bon Puits");
        assertEquals(taillePersonnalisee, vue.getTaille(), "La taille personnalisée doit être respectée");

        // NOUVEAU : Vérification de la taille de préférence personnalisée (12*30 = 360, 20*30 = 600)
        Dimension dimensionAttendue = new Dimension(12 * taillePersonnalisee, 20 * taillePersonnalisee);
        assertEquals(dimensionAttendue, vue.getPreferredSize(), "La taille de préférence (personnalisée) est incorrecte");
    }
}