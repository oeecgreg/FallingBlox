package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class ITetromino extends Tetromino {

    public ITetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

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