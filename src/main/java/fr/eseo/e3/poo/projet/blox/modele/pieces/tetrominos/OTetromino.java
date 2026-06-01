package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

/**
 * Classe fille de "Tetromino", sert à créer un "Tetromino" carré.
 */
public class OTetromino extends Tetromino {

    /**
     * Constructeur de la classe "OTetromino".
     * @param coordonnees Coordonnées du OTetromino.
     * @param couleur Couleur de la OTetromino.
     */
    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur); // Appel au constructeur de la classe mère
    }

    /**
     * Méthode Setter de la classe "OTetromino".
     * @param coordonnees Coordonnées du OTetromino.
     * @param couleur Couleur du OTetromino.
     */
    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        int x = coordonnees.getAbscisse();
        int y = coordonnees.getOrdonnee();
        this.getElements()[0] = new Element(new Coordonnees(x, y), couleur);
        this.getElements()[1] = new Element(new Coordonnees(x + 1, y), couleur);
        this.getElements()[2] = new Element(new Coordonnees(x, y - 1), couleur);
        this.getElements()[3] = new Element(new Coordonnees(x + 1, y - 1), couleur);
    }

    /**
     * Méthode permettant de tourner une pièce, ici non utilisée, car un
     * OTetromino (carré) ne tourne pas.
     * @param sensHoraire true pour une rotation dans le sens des aiguilles d'une montre,
     * false pour le sens anti-horaire.
     */
    @Override
    public void tourner(boolean sensHoraire) throws BloxException {
        // Surcharge volontaire : Un OTetromino (carré) ne tourne pas.
        // On ne fait donc absolument rien ici !
    }
}