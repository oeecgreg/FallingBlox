package fr.eseo.e3.poo.projet.blox.modele;

import java.awt.Color;

/**
 * Classe correspondant aux couleurs possibles des différentes pièces.
 */
public enum Couleur {

    // On associe chaque constante de l'énumération à une vraie couleur d'affichage
    ROUGE(Color.RED),
    ORANGE(Color.ORANGE),
    JAUNE(Color.YELLOW),
    VERT(Color.GREEN),
    CYAN(Color.CYAN),
    BLEU(Color.BLUE),
    VIOLET(Color.MAGENTA);

    // Variable d'instance pour stocker la couleur visuelle
    private final Color couleurPourAffichage;

    /**
     * Constructeur de l'énumération "Couleur".
     * @param couleurPourAffichage Couleur à afficher.
     */
    Couleur(Color couleurPourAffichage) {
        this.couleurPourAffichage = couleurPourAffichage;
    }

    /**
     * Accesseur permettant de récupérer la couleur d'affichage pour Swing.
     * @return Renvoie la couleur.
     */
    public Color getCouleurPourAffichage() {
        return this.couleurPourAffichage;
    }
}