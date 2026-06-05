package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class STetromino extends Tetromino {

    public STetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        this.getElements()[0] = new Element(coordonnees, couleur); // Pivot
        this.getElements()[1] = new Element(new Coordonnees(coordonnees.getAbscisse() - 1, coordonnees.getOrdonnee()), couleur);
        this.getElements()[2] = new Element(new Coordonnees(coordonnees.getAbscisse(), coordonnees.getOrdonnee() - 1), couleur);
        this.getElements()[3] = new Element(new Coordonnees(coordonnees.getAbscisse() + 1, coordonnees.getOrdonnee() - 1), couleur);
    }
}