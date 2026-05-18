package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import  fr.eseo.e3.poo.projet.blox.modele.Puits;

/**
 * Classe Tetromino implémentant l'interface "Pièce", elle sert à créer les différentes variantes de pièces.
 */
public abstract class Tetromino implements Piece {

    private final Element[] elements;
    private Puits puits;

    /**
     * Constructeur de la classe "Tetromino", désormais commun à toutes les classes.
     * @param coordonnees Coordonnées du Tetromino.
     * @param couleur Couleur du Tetromino.
     */
    public Tetromino(Coordonnees coordonnees, Couleur couleur) {
        this.elements = new Element[4];
        this.setElements(coordonnees, couleur);
    }

    /**
     * Méthode permettant de déplacer un objet de la classe "Tetromino" en remplaçant ses nouvelles coordonnées.
     * @param deltax Déplacement horizontal (-1, 0 ou 1).
     * @param deltay Déplacement vertical (0 ou 1).
     * @throws IllegalArgumentException Renvoie une exception si le déplacement de la pièce
     * n'est pas possible pour les raisons suivantes : <br/>
     * – Déplacement supérieur à la valeur absolue UN. <br/>
     * – Déplacement vers le haut. <br/>
     * – Déplacement en diagonale, soit le X soit le Y change.
     */
    @Override
    public void deplacerDe(int deltax, int deltay) throws IllegalArgumentException {
        // 1. Validation du déplacement
        // - Un déplacement ne peut pas être supérieur à UN en valeur absolue (on bouge case par case).
        // - Un déplacement vers le haut (deltay < 0) est strictement interdit.
        // - On ne peut pas déplacer en diagonale en un seul coup (soit X change, soit Y change, mais pas les deux).

        if (Math.abs(deltax) > 1) {
            throw new IllegalArgumentException("Déplacement horizontal invalide : impossible de bouger de plus d'une case (" + deltax + ")");
        }

        if (deltay < 0 || deltay > 1) {
            throw new IllegalArgumentException("Déplacement vertical invalide : impossible de monter ou de descendre de plus d'une case (" + deltay + ")");
        }

        if (deltax != 0 && deltay != 0) {
            throw new IllegalArgumentException("Déplacement en diagonale interdit : deltax et deltay ne peuvent pas être modifiés en même temps.");
        }

        // 2. Si le déplacement est valide, on l'applique à TOUS les éléments composants de la pièce
        for (Element element : this.getElements()) {
            if (element != null) {
                element.deplacerDe(deltax, deltay);
            }
        }
    }

    /**
     * Méthode permettant de tourner une pièce Tetromino de 90°.
     * @param sensHoraire true pour une rotation dans le sens des aiguilles d'une montre,
     * false pour le sens anti-horaire.
     */
    @Override
    public void tourner(boolean sensHoraire) {
        // L'élément de référence est toujours le premier du tableau (index 0)
        Element pivot = this.getElements()[0];
        if (pivot == null) return;

        int pivotX = pivot.getCoordonnees().getAbscisse();
        int pivotY = pivot.getCoordonnees().getOrdonnee();

        // On boucle sur tous les autres éléments (à partir de l'index 1)
        for (int i = 1; i < this.getElements().length; i++) {
            Element element = this.getElements()[i];

            if (element != null) {
                int xActuel = element.getCoordonnees().getAbscisse();
                int yActuel = element.getCoordonnees().getOrdonnee();

                // Étape 1 : Translater l'élément pour que le pivot soit à l'origine (0,0)
                int xRelatif = xActuel - pivotX;
                int yRelatif = yActuel - pivotY;

                int nouveauXRelatif;
                int nouveauYRelatif;

                // Étape 2 : Appliquer la rotation (attention à l'axe Y inversé !)
                if (sensHoraire) {
                    nouveauXRelatif = -yRelatif;
                    nouveauYRelatif = xRelatif;
                } else {
                    nouveauXRelatif = yRelatif;
                    nouveauYRelatif = -xRelatif;
                }

                // Étape 3 : Translater à nouveau pour remettre l'élément autour du pivot
                element.getCoordonnees().setAbscisse(pivotX + nouveauXRelatif);
                element.getCoordonnees().setOrdonnee(pivotY + nouveauYRelatif);
            }
        }
    }

    // Cette méthode reste abstraite, car chaque forme (O, I, etc.) se construit différemment
    protected abstract void setElements(Coordonnees coordonnees, Couleur couleur);

    @Override
    public Element[] getElements() {
        return this.elements;
    }

    @Override
    public void setPositions(int abscisse, int ordonnee) {
        Couleur couleurActuelle = this.elements[0].getCouleur();
        this.setElements(new Coordonnees(abscisse, ordonnee), couleurActuelle);
    }

    @Override
    public void setCouleur(Couleur couleur) {
        for (Element element : this.elements) {
            element.setCouleur(couleur);
        }
    }

    @Override
    public Puits getPuits() {
        return puits;
    }

    @Override
    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // On récupère le nom de la classe réelle (ex : OTetromino).
        sb.append(this.getClass().getSimpleName()).append(" :\n");
        for (int i = 0; i < this.elements.length; i++) {
            sb.append("\t").append(this.elements[i].toString());
            if (i < this.elements.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}