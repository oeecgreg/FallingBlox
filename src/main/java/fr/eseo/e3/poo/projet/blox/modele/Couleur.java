package fr.eseo.e3.poo.projet.blox.modele;

import java.awt.Color;

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
     * Constructeur privé de l'énumération.
     * En Java, le constructeur d'un enum est toujours privé par défaut.
     */
    Couleur(Color couleurPourAffichage) {
        this.couleurPourAffichage = couleurPourAffichage;
    }

    /**
     * Accesseur permettant de récupérer la couleur d'affichage pour Swing.
     */
    public Color getCouleurPourAffichage() {
        return this.couleurPourAffichage;
    }
}