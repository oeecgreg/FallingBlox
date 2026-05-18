package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import  fr.eseo.e3.poo.projet.blox.modele.Puits;

public abstract class Tetromino implements Piece {

    private final Element[] elements;
    private Puits puits;

    // Le constructeur est maintenant commun à tous les Tetrominos
    public Tetromino(Coordonnees coordonnees, Couleur couleur) {
        this.elements = new Element[4];
        this.setElements(coordonnees, couleur);
    }

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