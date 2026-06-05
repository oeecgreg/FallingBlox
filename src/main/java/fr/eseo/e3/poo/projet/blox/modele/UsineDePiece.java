package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Random;

import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.*;

public class UsineDePiece {

    public static final int ALEATOIRE_COMPLET = 0;
    public static final int ALEATOIRE_PIECE = 1;
    public static final int CYCLIC = 2;

    private static int mode = ALEATOIRE_PIECE;
    private static final Random random = new Random();
    private static int compteurCyclique = 0;

    private UsineDePiece() {}

    public static void setMode(int nouveauMode) {
        mode = nouveauMode;
        if (mode == CYCLIC) {
            compteurCyclique = 0;
        }
    }

    /**
     * Méthode interne pour centraliser la création de la pièce en fonction de son index (0 à 6)
     */
    private static Tetromino creerPiece(int type, Coordonnees coord, Couleur couleur) {
        switch (type) {
            case 0: return new OTetromino(coord, couleur);
            case 1: return new ITetromino(coord, couleur);
            case 2: return new TTetromino(coord, couleur);
            case 3: return new LTetromino(coord, couleur);
            case 4: return new JTetromino(coord, couleur);
            case 5: return new STetromino(coord, couleur);
            case 6: return new ZTetromino(coord, couleur);
            default: return new OTetromino(coord, couleur); // Sécurité
        }
    }

    public static Tetromino genererTetromino() {
        Coordonnees coordInitiales = new Coordonnees(2, 3);
        Couleur[] toutesLesCouleurs = Couleur.values();

        if (mode == ALEATOIRE_COMPLET) {
            int choixForme = random.nextInt(7); // 7 pièces
            Couleur couleurAleatoire = toutesLesCouleurs[random.nextInt(toutesLesCouleurs.length)];
            return creerPiece(choixForme, coordInitiales, couleurAleatoire);

        } else if (mode == ALEATOIRE_PIECE) {
            int choixForme = random.nextInt(7);
            Couleur couleurFixe = toutesLesCouleurs[choixForme]; // Associe 1 couleur = 1 forme
            return creerPiece(choixForme, coordInitiales, couleurFixe);

        } else { // CYCLIC
            Couleur couleurFixe = toutesLesCouleurs[compteurCyclique];
            Tetromino pieceGeneree = creerPiece(compteurCyclique, coordInitiales, couleurFixe);

            compteurCyclique++;
            if (compteurCyclique > 6) {
                compteurCyclique = 0;
            }
            return pieceGeneree;
        }
    }
}