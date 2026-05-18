package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

/**
 * Classe fille de "Tetromino", sert à créer un "Tetromino" ligne.
 */
public class ITetromino extends Tetromino {

    /**
     * Constructeur de la classe "ITetromino".
     * @param coordonnees Coordonnées de l'ITetromino.
     * @param couleur Couleur de l'ITetromino.
     */
    public ITetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * Méthode Setter de la classe "ITetromino".
     * @param coordonnees Coordonnées de l'ITetromino.
     * @param couleur Couleur de l'ITetromino.
     */
    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        int x = coordonnees.getAbscisse();
        int y = coordonnees.getOrdonnee();
        this.getElements()[0] = new Element(new Coordonnees(x, y), couleur);
        this.getElements()[1] = new Element(new Coordonnees(x, y - 1), couleur);
        this.getElements()[2] = new Element(new Coordonnees(x, y - 2), couleur);
        this.getElements()[3] = new Element(new Coordonnees(x, y + 1), couleur);
    }
}