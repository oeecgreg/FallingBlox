package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.BloxException;

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
    public void deplacerDe(int deltax, int deltay) throws IllegalArgumentException, BloxException {
        // 1. Validation des arguments (Inchangé)
        if (Math.abs(deltax) > 1) {
            throw new IllegalArgumentException("Déplacement horizontal supérieur à 1 case.");
        }
        if (deltay < 0 || deltay > 1) {
            throw new IllegalArgumentException("Déplacement vertical vers le haut ou supérieur à 1 case.");
        }
        if (deltax != 0 && deltay != 0) {
            throw new IllegalArgumentException("Déplacement en diagonale interdit.");
        }

        // 2. Vérification préventive (Look-ahead) si le puits est défini
        if (this.puits != null) {
            for (Element element : this.getElements()) {
                if (element != null) {
                    int futurX = element.getCoordonnees().getAbscisse() + deltax;
                    int futurY = element.getCoordonnees().getOrdonnee() + deltay;

                    // A. Sortie du Puits (Gauche / Droite)
                    if (futurX < 0 || futurX >= this.puits.getLargeur()) {
                        throw new BloxException("Mouvement impossible : Sortie des limites horizontales du puits.", BloxException.BLOX_SORTIE_PUITS);
                    }

                    // B. Collision avec le Fond
                    if (futurY >= this.puits.getProfondeur()) {
                        throw new BloxException("Mouvement impossible : Collision avec le fond du puits.", BloxException.BLOX_COLLISION);
                    }

                    // C. Collision avec le Tas d'éléments
                    if (this.puits.getTas() != null) {
                        for (Element eTas : this.puits.getTas().getElements()) {
                            if (eTas.getCoordonnees().getAbscisse() == futurX && eTas.getCoordonnees().getOrdonnee() == futurY) {
                                throw new BloxException("Mouvement impossible : Collision avec un bloc du tas.", BloxException.BLOX_COLLISION);
                            }
                        }
                    }
                }
            }
        }

        // 3. Si aucune exception n'a été levée, on applique réellement le déplacement à tous les éléments
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
    public void tourner(boolean sensHoraire) throws BloxException {
        Element pivot = this.getElements()[0];
        if (pivot == null) return;

        int pivotX = pivot.getCoordonnees().getAbscisse();
        int pivotY = pivot.getCoordonnees().getOrdonnee();

        int[][] futuresCoords = new int[this.getElements().length][2];
        futuresCoords[0][0] = pivotX;
        futuresCoords[0][1] = pivotY;

        // 1. Simulation préventive de la rotation
        for (int i = 1; i < this.getElements().length; i++) {
            Element element = this.getElements()[i];
            if (element != null) {
                int xActuel = element.getCoordonnees().getAbscisse();
                int yActuel = element.getCoordonnees().getOrdonnee();

                int xRelatif = xActuel - pivotX;
                int yRelatif = yActuel - pivotY;

                int nouveauXRelatif = sensHoraire ? -yRelatif : yRelatif;
                int nouveauYRelatif = sensHoraire ? xRelatif : -xRelatif;

                int futurX = pivotX + nouveauXRelatif;
                int futurY = pivotY + nouveauYRelatif;

                // 2. Validation des collisions UNIQUEMENT si le puits existe
                if (this.puits != null) {
                    if (futurX < 0 || futurX >= this.puits.getLargeur()) {
                        throw new BloxException("Rotation impossible : Sortie du puits.", BloxException.BLOX_SORTIE_PUITS);
                    }
                    if (futurY >= this.puits.getProfondeur()) {
                        throw new BloxException("Rotation impossible : Collision avec le fond.", BloxException.BLOX_COLLISION);
                    }
                    if (this.puits.getTas() != null) {
                        for (Element eTas : this.puits.getTas().getElements()) {
                            if (eTas.getCoordonnees().getAbscisse() == futurX && eTas.getCoordonnees().getOrdonnee() == futurY) {
                                throw new BloxException("Rotation impossible : Collision avec le tas.", BloxException.BLOX_COLLISION);
                            }
                        }
                    }
                }

                futuresCoords[i][0] = futurX;
                futuresCoords[i][1] = futurY;
            }
        }

        // 3. Application définitive de la rotation (s'exécute toujours, même sans puits)
        for (int i = 1; i < this.getElements().length; i++) {
            Element element = this.getElements()[i];
            if (element != null) {
                element.getCoordonnees().setAbscisse(futuresCoords[i][0]);
                element.getCoordonnees().setOrdonnee(futuresCoords[i][1]);
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