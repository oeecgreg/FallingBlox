package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

/**
 * Interface des pièces.
 */
public interface Piece {

    /**
     * Méthode Getter de la classe "Element".
     * @return Renvoie les QUATRE paramètres de la classe "Element".
     */
    Element[] getElements();

    /**
     * Méthode Setter de la classe "Tetromino".
     * @param abscisse Nouvelle abscisse choisie.
     * @param ordonnee Nouvelle ordonnée choisie.
     */
    void setPositions(int abscisse, int ordonnee);

    /**
     * Méthode permettant le changement de la couleur de tous les éléments de la pièce.
     * @param couleur Nouvelle couleur choisie.
     */
    void setCouleur(Couleur couleur);

    /**
     * Méthode Getter de la classe "Puits".
     * @return Un élément "Puits".
     */
    Puits getPuits();

    /**
     * Méthode Setter de la classe "Puits".
     * @param puits Élément "Puits" que l'on souhaite set.
     */
    void setPuits(Puits puits);

    /**
     * Déplace la pièce entière si le vecteur de déplacement est valide.
     * @param deltax Déplacement horizontal (-1, 0 ou 1)
     * @param deltay Déplacement vertical (0 ou 1)
     * @throws IllegalArgumentException si le déplacement est invalide (ex : vers le haut, ou trop grand).
     */
    void deplacerDe(int deltax, int deltay) throws IllegalArgumentException;

    /**
     * Fait tourner la pièce de 90 degrés autour de son élément de référence.
     * @param sensHoraire true pour une rotation dans le sens des aiguilles d'une montre,
     * false pour le sens anti-horaire.
     */
    void tourner(boolean sensHoraire);
}