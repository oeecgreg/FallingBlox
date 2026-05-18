package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

public interface Piece {

    // Retourne les 4 éléments composant la pièce
    Element[] getElements();

    // Change la position de la pièce en déplaçant son élément de référence
    void setPositions(int abscisse, int ordonnee);

    // Change la couleur de tous les éléments de la pièce
    void setCouleur(Couleur couleur);

    Puits getPuits();

    void setPuits(fr.eseo.e3.poo.projet.blox.modele.Puits puits);
}