package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Random;

import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;

/**
 * Classe servant à la génération de pièces avec différents modes de génération : <br>
 * - ALEATOIRE_COMPLET | Choisis un type de tetromino aléatoirement, en utilisant une couleur aléatoire. <br>
 * - ALEATOIRE_PIECE | Choisis un type de tetromino aléatoirement, en utilisant la même couleur pour le même type de Tetromino. <br>
 * - CYCLIC | Choisis dans l’ordre les tetrominos (OTetromino, puis ITetromino, …, OTetromino, …) en respectant les couleurs utilisées.
 */
public class UsineDePiece {

    // Constantes pour les différents modes de l'usine
    public static final int ALEATOIRE_COMPLET = 0;
    public static final int ALEATOIRE_PIECE = 1;
    public static final int CYCLIC = 2;

    // Mode actuel (par défaut : ALEATOIRE_PIECE)
    private static int mode = ALEATOIRE_PIECE;

    // Générateur de nombres aléatoires
    private static final Random random = new Random();

    // Compteur pour le mode cyclique (pour savoir à qui le tour)
    private static int compteurCyclique = 0;

    /**
     * Constructeur privé pour empêcher l'instanciation de cette classe.
     * Toutes les méthodes sont statiques.
     */
    private UsineDePiece() {
    }

    /**
     * Méthode définissant le mode de fonctionnement de l'usine.
     * Si on passe en mode CYCLIC, on réinitialise pour que la prochaine soit un OTetromino.
     * @param nouveauMode Choix du nouveau mode parmi ceux définis.
     */
    public static void setMode(int nouveauMode) {
        mode = nouveauMode;
        if (mode == CYCLIC) {
            compteurCyclique = 0; // 0 correspondra au OTetromino
        }
    }

    /**
     * Méthode permettant la génération d'une pièce aux coordonnées (2, 3) selon le mode actuel.
     * @return Renvoie la pièce qui est générée.
     */
    public static Tetromino genererTetromino() {
        Coordonnees coordInitiales = new Coordonnees(2, 3);
        Tetromino pieceGeneree = null;

        if (mode == ALEATOIRE_COMPLET) {
            // Choix aléatoire de la forme (0 ou 1) et de la couleur
            int choixForme = random.nextInt(2);
            Couleur[] toutesLesCouleurs = Couleur.values();
            Couleur couleurAleatoire = toutesLesCouleurs[random.nextInt(toutesLesCouleurs.length)];

            if (choixForme == 0) {
                pieceGeneree = new OTetromino(coordInitiales, couleurAleatoire);
            } else {
                pieceGeneree = new ITetromino(coordInitiales, couleurAleatoire);
            }

        } else if (mode == ALEATOIRE_PIECE) {
            // Choix aléatoire de la forme, mais couleur fixe par défaut
            int choixForme = random.nextInt(2);
            if (choixForme == 0) {
                pieceGeneree = new OTetromino(coordInitiales, Couleur.ROUGE); // Couleur typique du O.
            } else {
                pieceGeneree = new ITetromino(coordInitiales, Couleur.ORANGE); // Couleur typique du I.
            }

        } else if (mode == CYCLIC) {
            // Alternance stricte : O, puis I, puis O, etc.
            if (compteurCyclique == 0) {
                pieceGeneree = new OTetromino(coordInitiales, Couleur.ROUGE);
                compteurCyclique = 1;
            } else {
                pieceGeneree = new ITetromino(coordInitiales, Couleur.ORANGE);
                compteurCyclique = 0;
            }
        }

        return pieceGeneree;
    }
}