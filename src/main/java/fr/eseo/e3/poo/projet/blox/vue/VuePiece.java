package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

/**
 * Classe servant à l'affichage des pièces.
 */
public class VuePiece {

    // 1. Constante de classe pour le facteur de clarté (valeur entre "0.0" et "1.0").
    // Vous pouvez ajuster cette valeur (ex : 0.3 ou 0.5) selon le rendu visuel désiré
    public static final double MULTIPLIER_TEINTE = 0.4;

    // Variables d'instance immuables (déclarées 'final')
    private final Piece piece;
    private final int taille;

    /**
     * Constructeur de la classe "VuePiece".
     * @param piece Pièce à afficher.
     * @param taille Taille de la pièce à afficher.
     */
    public VuePiece(Piece piece, int taille) {
        this.piece = piece;
        this.taille = taille;
    }

    /**
     * Méthode de classe (statique) appliquant l'algorithme personnalisé
     * pour obtenir une teinte plus claire d'une couleur donnée.
     * @param couleur Couleur dont une teinte plus claire sera tirée.
     * @return Renvoie les valeurs RGB de la nouvelle teinte de couleur.
     */
    public static Color teinte(Color couleur) {
        int r = couleur.getRed();
        int g = couleur.getGreen();
        int b = couleur.getBlue();
        int alpha = couleur.getAlpha();

        // Application de la formule mathématique de l'énoncé
        int nouveauR = (int) (r + (255 - r) * MULTIPLIER_TEINTE);
        int nouveauG = (int) (g + (255 - g) * MULTIPLIER_TEINTE);
        int nouveauB = (int) (b + (255 - b) * MULTIPLIER_TEINTE);

        return new Color(nouveauR, nouveauG, nouveauB, alpha);
    }

    /**
     * Méthode permettant de dessiner les différents éléments de la pièce sur le contexte graphique.
     * @param g2D Graphiques 2D de la pièce.
     */
    public void afficherPiece(Graphics2D g2D) {
        Element[] elements = this.piece.getElements();

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                // Récupération de la couleur de base de l'élément
                Color couleurAffichage = elements[i].getCouleur().getCouleurPourAffichage();

                // Si c'est le premier élément (l'élément de référence), on l'éclaircit
                if (i == 0) {
                    g2D.setColor(teinte(couleurAffichage));
                } else {
                    g2D.setColor(couleurAffichage);
                }

                // Calcul de la position de l'élément en pixels sur l'écran
                int x = elements[i].getCoordonnees().getAbscisse() * this.taille;
                int y = elements[i].getCoordonnees().getOrdonnee() * this.taille;

                // Dessin du carré en relief (effet 3D surélevé -> paramètre 'raised' à true)
                g2D.fill3DRect(x, y, this.taille, this.taille, true);
            }
        }
    }

    // --- Accesseurs (au cas où l'Assignment Centre les réclame pour l'encapsulation) ---

    /**
     * Méthode Getter de la classe "VuePiece", applicable sur "Piece".
     * @return Renvoie la pièce étant affichée.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Méthode Getter de la classe "VuePiece".
     * @return Renvoie la taille du puits.
     */
    public int getTaille() {
        return this.taille;
    }
}