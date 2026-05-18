package fr.eseo.e3.poo.projet.blox.modele;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

/**
 * Classe servant à l'affichage de la grille de jeu.
 */
public class Puits {

    public static final int LARGEUR_PAR_DEFAUT = 10;
    public static final int PROFONDEUR_PAR_DEFAUT = 15;

    // Constantes d'identification des propriétés modifiées
    public static final String MODIFICATION_PIECE_ACTUELLE = "pieceActuelle";
    public static final String MODIFICATION_PIECE_SUIVANTE = "pieceSuivante";

    private int largeur;
    private int profondeur;

    private Piece pieceActuelle;
    private Piece pieceSuivante;

    // Gestionnaire de notification de changement de propriété
    private final PropertyChangeSupport pcs;

    // --- Constructeurs ---

    /**
     * Constructeur de la classe "Puits", utilisant la largeur et la profondeur par défaut.
     */
    public Puits() {
        this(LARGEUR_PAR_DEFAUT, PROFONDEUR_PAR_DEFAUT);
    }

    /**
     * Constructeur de la classe "Puits".
     * @param largeur Largeur du puit.
     * @param profondeur Profondeur du puit.
     */
    public Puits(int largeur, int profondeur) {
        // Initialisation obligatoire de PropertyChangeSupport en passant 'this'
        this.pcs = new PropertyChangeSupport(this);
        this.setLargeur(largeur);
        this.setProfondeur(profondeur);
    }

    // --- Gestion des Écouteurs (Delegation vers pcs) ---
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    // --- Accesseurs et Mutateurs ---

    /**
     * Méthode Getter du paramètre "largeur".
     * @return Renvoie la largeur.
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Méthode Setter du paramètre "largeur".
     * @param largeur Nouvelle largeur.
     */
    public void setLargeur(int largeur) {
        if (largeur < 5 || largeur > 15) {
            throw new IllegalArgumentException("La largeur doit être comprise entre 5 et 15.");
        }
        this.largeur = largeur;
    }

    /**
     * Méthode Getter du paramètre "profondeur".
     * @return Renvoie la profondeur.
     */
    public int getProfondeur() {
        return profondeur;
    }

    /**
     * Méthode Setter du paramètre "profondeur".
     * @param profondeur Nouvelle profondeur.
     */
    public void setProfondeur(int profondeur) {
        if (profondeur < 15 || profondeur > 25) {
            throw new IllegalArgumentException("La profondeur doit être comprise entre 15 et 25.");
        }
        this.profondeur = profondeur;
    }

    /**
     * Méthode Getter permettant de récupérer la pièce actuelle.
     * @return Renvoie la pièce actuelle.
     */
    public Piece getPieceActuelle() {
        return pieceActuelle;
    }

    /**
     * Méthode Getter permettant de récupérer la pièce suivante.
     * @return Renvoie la pièce suivante.
     */
    public Piece getPieceSuivante() {
        return pieceSuivante;
    }

    // --- Logique métier avec Notifications ---

    /**
     * Méthode Setter permettant de modifier la prochaine pièce.
     * @param piece Pièce dont les valeurs seront modifiées.
     */
    public void setPieceSuivante(Piece piece) {
        // 1. Sauvegarde des anciennes valeurs avant modification
        Piece ancienneActuelle = this.pieceActuelle;
        Piece ancienneSuivante = this.pieceSuivante;

        // 2. Traitement métier de base
        if (this.pieceSuivante != null) {
            this.pieceActuelle = this.pieceSuivante;
            this.pieceActuelle.setPositions(this.largeur / 2, -4);
        }

        this.pieceSuivante = piece;

        if (this.pieceSuivante != null) {
            this.pieceSuivante.setPuits(this);
        }

        // 3. Déclenchement des notifications si des changements ont eu lieu
        this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, ancienneActuelle, this.pieceActuelle);
        this.pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, ancienneSuivante, this.pieceSuivante);
    }

    /**
     * Méthode toString de la classe "Puits", avec le format demander par le PDF.
     * @return Renvoie un String avec la taille du puits, la pièce actuelle et la pièce suivante.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Puits : Dimension ").append(this.largeur).append(" x ").append(this.profondeur).append("\n");

        sb.append("Piece Actuelle : ");
        if (this.pieceActuelle == null) {
            sb.append("<aucune>\n");
        } else {
            sb.append(this.pieceActuelle).append("\n");
        }

        sb.append("Piece Suivante : ");
        if (this.pieceSuivante == null) {
            sb.append("<aucune>");
        } else {
            sb.append(this.pieceSuivante);
        }

        return sb.toString();
    }
}