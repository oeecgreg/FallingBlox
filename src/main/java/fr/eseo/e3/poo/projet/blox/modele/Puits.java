package fr.eseo.e3.poo.projet.blox.modele;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;

/**
 * Classe servant à l'affichage de la grille de jeu.
 */
public class Puits {

    public static final int LARGEUR_PAR_DEFAUT = 10;
    public static final int PROFONDEUR_PAR_DEFAUT = 15;

    // Constantes d'identification des propriétés modifiées
    public static final String MODIFICATION_PIECE_ACTUELLE = "pieceActuelle";
    public static final String MODIFICATION_PIECE_SUIVANTE = "pieceSuivante";
    public static final String MODIFICATION_PARTIE_TERMINEE = "partieTerminee";
    private boolean partieTerminee = false;

    private int largeur;
    private int profondeur;

    private Piece pieceActuelle;
    private Piece pieceSuivante;

    // Gestionnaire de notification de changement de propriété
    private final PropertyChangeSupport pcs;

    private Tas tas;

    public static final String MODIFICATION_SCORE = "score";
    private int score = 0;

    public static final String MODIFICATION_PAUSE = "pause";
    private boolean partieEnPause = false;

    public static final String MODIFICATION_LIGNES = "lignes";

    private int nbLignesSupprimees = 0;
    private boolean echangeAutorise = true; // Permet un seul échange par chute

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
        this.tas = new Tas(this);
    }

    /**
     * NOUVEAU : Troisième constructeur permettant d'initialiser un Puits
     * avec un Tas déjà pré-rempli d'éléments aléatoires au fond.
     */
    public Puits(int largeur, int profondeur, int nbElements, int nbLignes) {
        this(largeur, profondeur);
        // Initialisation du tas avec génération aléatoire des briques de fond
        this.tas = new Tas(this, nbElements, nbLignes);
    }

    // --- Gestion des Écouteurs (Delegation vers pcs) ---
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    // --- Accesseurs et Mutateurs ---

    public Tas getTas() {
        return this.tas;
    }

    public void setTas(Tas tas) {
        this.tas = tas;
    }

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

    /**
     * Gère la collision d'une pièce : transfert de ses éléments au Tas
     * et génération de la pièce suivante.
     */
    private void gererCollision() {
        if (this.tas != null && this.pieceActuelle != null) {
            this.tas.ajouterElements(this.pieceActuelle);

            // 1. Vérification du Game Over
            for (Element element : this.pieceActuelle.getElements()) {
                if (element != null && element.getCoordonnees().getOrdonnee() <= 0) {
                    this.setPartieTerminee(true);
                    return;
                }
            }

            // 2. Suppression des lignes, calcul du score et du NIVEAU
            int lignesDetruites = this.tas.supprimerLignes();
            if (lignesDetruites > 0) {
                // Score
                int pointsGagnes = 0;
                switch (lignesDetruites) {
                    case 1: pointsGagnes = 100; break;
                    case 2: pointsGagnes = 300; break;
                    case 3: pointsGagnes = 500; break;
                    case 4: pointsGagnes = 800; break;
                    default: pointsGagnes = 1000;
                }
                this.setScore(this.getScore() + pointsGagnes);

                // Lignes (déclenche l'accélération)
                int anciennesLignes = this.nbLignesSupprimees;
                this.nbLignesSupprimees += lignesDetruites;
                this.pcs.firePropertyChange(MODIFICATION_LIGNES, anciennesLignes, this.nbLignesSupprimees);
            }
        }

        // 3. On continue le jeu
        this.echangeAutorise = true; // On redonne le droit d'échanger la nouvelle pièce
        this.setPieceSuivante(UsineDePiece.genererTetromino());
    }

    /**
     * Tente de faire descendre la pièce d'une case.
     */
    public void gravite() {
        if (this.pieceActuelle != null) {
            try {
                this.pieceActuelle.deplacerDe(0, 1);
            } catch (BloxException e) {
                // Si la pièce heurte le fond ou un bloc du tas, elle déclenche la collision
                if (e.getCode() == BloxException.BLOX_COLLISION) {
                    this.gererCollision();
                }
            }
        }
    }

    public boolean isPartieTerminee() {
        return this.partieTerminee;
    }

    public void setPartieTerminee(boolean partieTerminee) {
        boolean ancienneValeur = this.partieTerminee;
        this.partieTerminee = partieTerminee;
        this.pcs.firePropertyChange(MODIFICATION_PARTIE_TERMINEE, ancienneValeur, this.partieTerminee);
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        int ancienScore = this.score;
        this.score = score;
        // On notifie les écouteurs que le score a changer.
        this.pcs.firePropertyChange(MODIFICATION_SCORE, ancienScore, this.score);
    }

    public boolean isPartieEnPause() {
        return this.partieEnPause;
    }

    public void setPartieEnPause(boolean partieEnPause) {
        boolean ancienneValeur = this.partieEnPause;
        this.partieEnPause = partieEnPause;
        this.pcs.firePropertyChange(MODIFICATION_PAUSE, ancienneValeur, this.partieEnPause);
    }

    /**
     * Permet d'échanger la pièce actuelle avec la pièce suivante.
     * Cette action n'est autorisée qu'une seule fois par chute.
     */
    public void echangerPiece() {
        if (!this.echangeAutorise || this.pieceActuelle == null || this.pieceSuivante == null || this.isPartieTerminee() || this.isPartieEnPause()) {
            return;
        }

        // Sauvegarde temporaire
        Piece ancienneActuelle = this.pieceActuelle;
        Piece ancienneSuivante = this.pieceSuivante;

        // Inversion
        this.pieceActuelle = ancienneSuivante;
        this.pieceActuelle.setPositions(this.largeur / 2, -4); // On place la nouvelle tout en haut

        this.pieceSuivante = ancienneActuelle;

        // On bloque l'échange jusqu'à la prochaine collision
        this.echangeAutorise = false;

        // On notifie la Vue pour mettre à jour l'écran
        this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, ancienneActuelle, this.pieceActuelle);
        this.pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, ancienneSuivante, this.pieceSuivante);
    }

    /**
     * Réinitialise entièrement le Puits pour commencer une nouvelle partie.
     */
    public void reinitialiser() {
        // 1. On vide le tas
        if (this.tas != null) {
            this.tas.getElements().clear();
        }

        // 2. On réinitialise les statistiques
        this.setScore(0);

        int anciennesLignes = this.nbLignesSupprimees;
        this.nbLignesSupprimees = 0;
        this.pcs.firePropertyChange(MODIFICATION_LIGNES, anciennesLignes, this.nbLignesSupprimees);

        // 3. On enlève les états de blocage
        this.setPartieEnPause(false);
        this.setPartieTerminee(false); // Cela va prévenir la Gravité de redémarrer !

        // 4. On génère de nouvelles pièces
        this.pieceActuelle = null;
        this.pieceSuivante = null;
        this.echangeAutorise = true;

        this.setPieceSuivante(UsineDePiece.genererTetromino());
        this.setPieceSuivante(UsineDePiece.genererTetromino());
    }
}