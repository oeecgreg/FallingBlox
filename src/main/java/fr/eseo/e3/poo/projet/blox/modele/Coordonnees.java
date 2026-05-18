package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

/**
 * Classe "Coordonnees" permettant de définir des coordonnées d'un repère cartésien orthonormé.
 */
public class Coordonnees {

    private int abscisse;
    private int ordonnee;

    // Constructeur

    /**
     * Constructeur de la classe "Coordonnees".
     * @param abscisse Abscisse de la coordonnée.
     * @param ordonnee Ordonnée de la coordonnée.
     */
    public Coordonnees(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    // --- Accesseurs et Mutateurs ---

    /**
     * Méthode Getter du paramètre "abscisse".
     * @return Renvoie l'abscisse
     */
    public int getAbscisse() {
        return abscisse;
    }

    /**
     * Méthode Getter du paramètre "ordonnee".
     * @return Renvoie l'ordonnee.
     */
    public int getOrdonnee() {
        return ordonnee;
    }

    /**
     * Méthode Setter du paramètre "abscisse".
     */
    public void setAbscisse(int abscisse) {
        this.abscisse = abscisse;
    }

    /**
     * Méthode Setter du paramètre "ordonnee".
     */
    public void setOrdonnee(int ordonnee) {
        this.ordonnee = ordonnee;
    }

    // Redéfinition de toString() au format strict demandé : "(abscisse, ordonnee)"

    /**
     * Méthode toString de la classe "Coordonnees", avec le format demander par le PDF.
     * @return Renvoie un String avec l'abscisse et l'ordonnée.
     */
    @Override
    public String toString() {
        return "(" + this.abscisse + ", " + this.ordonnee + ")";
    }

    // Redéfinition de equals() pour comparer deux coordonnées

    /**
     * Redéfinition de la méthode equals(), déjà existante, pour s'adapter à la
     * classe "Coordonnees".
     * @param obj Coordonnées que l'on souhaite comparer.
     * @return Renvoie si les deux coordonnées sont égales.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordonnees that = (Coordonnees) obj;
        return abscisse == that.abscisse && ordonnee == that.ordonnee;
    }

    // Redéfinition de hashCode() indispensable quand on redéfinit equals()

    /**
     * Redéfinition de la méthode hashCode(), déjà existante, pour s'adapter à
     * la classe "Coordonnees".
     * @return Renvoie le hash de l'objet sur laquelle la méthode est utilisée.
     */
    @Override
    public int hashCode() {
        return Objects.hash(abscisse, ordonnee);
    }
}