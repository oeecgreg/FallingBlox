package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class VueOmbre {

    private final VuePuits vuePuits;

    public VueOmbre(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    public void afficher(Graphics2D g2D) {
        Puits puits = this.vuePuits.getPuits();
        if (puits == null || puits.getPieceActuelle() == null || puits.isPartieTerminee()) return;

        Piece piece = puits.getPieceActuelle();
        int taille = this.vuePuits.getTaille();

        // 1. Calcul de la distance de chute maximale autorisée (deltaYMax)
        int deltaYMax = puits.getProfondeur(); // Par défaut, on imagine qu'elle peut tomber tout en bas

        for (Element e : piece.getElements()) {
            if (e == null) continue;
            int x = e.getCoordonnees().getAbscisse();
            int y = e.getCoordonnees().getOrdonnee();

            // Distance jusqu'au fond du puits
            int distanceMax = puits.getProfondeur() - y - 1;

            // Distance jusqu'au point le plus haut du Tas dans cette colonne
            if (puits.getTas() != null) {
                for (Element eTas : puits.getTas().getElements()) {
                    if (eTas.getCoordonnees().getAbscisse() == x && eTas.getCoordonnees().getOrdonnee() > y) {
                        int dist = eTas.getCoordonnees().getOrdonnee() - y - 1;
                        if (dist < distanceMax) {
                            distanceMax = dist;
                        }
                    }
                }
            }

            // On conserve la plus petite distance trouvée parmi les 4 blocs de la pièce
            if (distanceMax < deltaYMax) {
                deltaYMax = distanceMax;
            }
        }

        // 2. Dessin de l'ombre à la position (Y actuel + deltaYMax)
        g2D.setStroke(new BasicStroke(2)); // Contour un peu plus épais
        for (Element e : piece.getElements()) {
            if (e != null) {
                int xPixel = e.getCoordonnees().getAbscisse() * taille;
                int yPixel = (e.getCoordonnees().getOrdonnee() + deltaYMax) * taille;

                Color c = e.getCouleur().getCouleurPourAffichage();

                // Remplissage intérieur très transparent (opacité à 60/255)
                g2D.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 60));
                g2D.fillRect(xPixel, yPixel, taille, taille);

                // Bordure de la même couleur (totalement opaque)
                g2D.setColor(c);
                g2D.drawRect(xPixel + 1, yPixel + 1, taille - 2, taille - 2);
            }
        }
    }
}