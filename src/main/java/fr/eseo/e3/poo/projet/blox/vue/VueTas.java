package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Tas;

public class VueTas {

    // Constante pour assombrir la couleur (valeur arbitraire entre 0.0 et 1.0)
    // 0.3 ou 0.4 donne un bon rendu visuel
    public static final double MULTIPLIER_NUANCE = 0.4;

    private final VuePuits vuePuits;
    private final Tas tas;

    /**
     * Constructeur de la VueTas.
     * Le Tas est immuable en tant qu'objet (seul son contenu change),
     * on peut donc le récupérer une seule fois lors de la construction.
     */
    public VueTas(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.tas = vuePuits.getPuits().getTas();
    }

    /**
     * Deuxième constructeur pour respecter la signature du diagramme UML si la taille est passée.
     */
    public VueTas(VuePuits vuePuits, int taille) {
        this(vuePuits); // On ignore la taille ici car on peut la récupérer via vuePuits.getTaille()
    }

    public VuePuits getVuePuits() {
        return this.vuePuits;
    }

    /**
     * Algorithme pour calculer une nuance plus sombre de la couleur d'origine.
     */
    public static Color nuance(Color couleur) {
        int r = (int) (couleur.getRed() * (1 - MULTIPLIER_NUANCE));
        int g = (int) (couleur.getGreen() * (1 - MULTIPLIER_NUANCE));
        int b = (int) (couleur.getBlue() * (1 - MULTIPLIER_NUANCE));

        return new Color(r, g, b);
    }

    /**
     * Dessine les éléments du tas sur le Graphics2D fourni.
     */
    public void afficher(Graphics2D g2D) {
        if (this.tas != null && this.tas.getElements() != null) {
            int taille = this.vuePuits.getTaille();

            for (Element element : this.tas.getElements()) {
                int x = element.getCoordonnees().getAbscisse() * taille;
                int y = element.getCoordonnees().getOrdonnee() * taille;

                // On applique la nuance pour assombrir la brique
                Color couleurNuancee = nuance(element.getCouleur().getCouleurPourAffichage());
                g2D.setColor(couleurNuancee);

                // Le paramètre false donne un effet 3D "enfoncé" (en dessous de la surface)
                g2D.fill3DRect(x, y, taille, taille, false);
            }
        }
    }
}