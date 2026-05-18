package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class Puits {

    // Constantes par défaut (souvent 10x20 ou 10x15 dans les sujets Tetris)
    public static final int LARGEUR_PAR_DEFAUT = 10;
    public static final int PROFONDEUR_PAR_DEFAUT = 15;

    private int largeur;
    private int profondeur;

    private Piece pieceActuelle;
    private Piece pieceSuivante;

    // --- Constructeurs ---
    public Puits() {
        this(LARGEUR_PAR_DEFAUT, PROFONDEUR_PAR_DEFAUT);
    }

    public Puits(int largeur, int profondeur) {
        this.setLargeur(largeur);
        this.setProfondeur(profondeur);
    }

    // --- Accesseurs et Mutateurs basiques ---
    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        if (largeur < 5 || largeur > 15) {
            throw new IllegalArgumentException("La largeur doit être comprise entre 5 et 15.");
        }
        this.largeur = largeur;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        if (profondeur < 15 || profondeur > 25) {
            throw new IllegalArgumentException("La profondeur doit être comprise entre 15 et 25.");
        }
        this.profondeur = profondeur;
    }

    public Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public Piece getPieceSuivante() {
        return pieceSuivante;
    }

    // --- Logique métier : setPieceSuivante ---
    public void setPieceSuivante(Piece piece) {
        if (this.pieceSuivante != null) {
            // L'ancienne pièce suivante devient la pièce actuelle
            this.pieceActuelle = this.pieceSuivante;
            // On la place en haut au milieu selon les consignes du sujet : (largeur/2, -4)
            this.pieceActuelle.setPositions(this.largeur / 2, -4);
        }

        // On assigne la nouvelle pièce suivante
        this.pieceSuivante = piece;

        if (this.pieceSuivante != null) {
            this.pieceSuivante.setPuits(this);
        }
    }

    // --- Redéfinition (Format strict) ---
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
            // Pas de \n à la fin de la dernière ligne
            sb.append(this.pieceSuivante);
        }

        return sb.toString();
    }
}