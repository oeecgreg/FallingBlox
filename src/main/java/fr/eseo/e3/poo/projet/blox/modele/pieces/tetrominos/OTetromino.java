package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class OTetromino extends Tetromino {

    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur); // Appel au constructeur de la classe mère
    }

    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        int x = coordonnees.getAbscisse();
        int y = coordonnees.getOrdonnee();
        this.getElements()[0] = new Element(new Coordonnees(x, y), couleur);
        this.getElements()[1] = new Element(new Coordonnees(x + 1, y), couleur);
        this.getElements()[2] = new Element(new Coordonnees(x, y - 1), couleur);
        this.getElements()[3] = new Element(new Coordonnees(x + 1, y - 1), couleur);
    }
}