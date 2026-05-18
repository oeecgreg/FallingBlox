package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

/**
 * Classe Element permettant la création de pièces.
 */
public class Element {

    private Coordonnees coordonnees;
    private Couleur couleur;

    // --- Constructeurs ---

    /**
     * Constructeur complet de la classe "Element".
     * @param coordonnees Coordonnées de la pièce.
     * @param couleur Couleur de la pièce.
     */
    public Element(Coordonnees coordonnees, Couleur couleur) {
        this.coordonnees = coordonnees;
        this.couleur = couleur;
    }

    /**
     * Constructeur de la classe "Element" permettant le choix de l'abscisse et de l'ordonnée.
     * @param abscisse Abscisse de la pièce.
     * @param ordonnee Ordonnées de la pièce.
     * @param couleur Couleur de la pièce.
     */
    public Element(int abscisse, int ordonnee, Couleur couleur) {
        this(new Coordonnees(abscisse, ordonnee), couleur);
    }

    /**
     * Constructeur de la classe "Element" avec le paramètre couleur par défaut.
     * @param coordonnees Coordonnées de la pièce.
     */
    public Element(Coordonnees coordonnees) {
        // values()[0] permet de prendre la première couleur de l'enum (ROUGE)
        this(coordonnees, Couleur.values()[0]);
    }

    /**
     * Constructeur de la classe "Element" avec le paramètre couleur par défaut
     * et le choix de l'abscisse et de l'ordonnée.
     * @param abscisse Abscisse de la pièce.
     * @param ordonnee Ordonnée de la pièce.
     */
    public Element(int abscisse, int ordonnee) {
        this(new Coordonnees(abscisse, ordonnee), Couleur.values()[0]);
    }

    // --- Accesseurs et Mutateurs ---

    /**
     * Méthode Getter de du paramètre "coordonnees".
     * @return Renvoie le paramètre "coordonnees".
     */
    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    /**
     * Méthode Setter du paramètre "coordonnees".
     * @param coordonnees Coordonnées à modifier.
     */
    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    /**
     * Méthode Getter du paramètre "couleur".
     * @return Renvoie le paramètre "couleur".
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Méthode Setter du paramètre "couleur".
     * @param couleur Couleur à modifier.
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    // --- Redéfinitions ---

    /**
     * Méthode toString de la classe "Element", format comme demander dans le PDF.
     * @return Renvoie un String avec les coordonnées et la couleur.
     */
    @Override
    public String toString() {
        return this.coordonnees.toString() + " - " + this.couleur;
    }

    // equals basé sur coordonnees ET couleur

    /**
     * Méthode permettant de comparer la couleur et les coordonnées d'un "Element".
     * @param obj Élément que l'on souhaite comparer.
     * @return Renvoie si les deux éléments sont égaux.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Element element = (Element) obj;
        return Objects.equals(coordonnees, element.coordonnees) && couleur == element.couleur;
    }

    // hashCode indispensable quand equals() est redéfini

    /**
     * Méthode indispensable lorsque la méthode equals() est redéfini.
     * @return Renvoie le hash de l'objet sur laquelle la méthode est utilisée.
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordonnees, couleur);
    }

    /**
     * Déplace l'élément selon un vecteur de translation (deltax, deltay).
     * * @param deltax Le déplacement sur l'axe horizontal.
     * @param deltay Le déplacement sur l'axe vertical.
     */
    public void deplacerDe(int deltax, int deltay) {
        // On récupère les coordonnées actuelles
        int xActuel = this.coordonnees.getAbscisse();
        int yActuel = this.coordonnees.getOrdonnee();

        // On met à jour l'objet Coordonnees avec les nouvelles valeurs
        this.coordonnees.setAbscisse(xActuel + deltax);
        this.coordonnees.setOrdonnee(yActuel + deltay);
    }
}