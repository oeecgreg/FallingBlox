package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

public class Element {

    private Coordonnees coordonnees;
    private Couleur couleur;

    // --- Constructeurs ---

    // 1. Constructeur complet (Coordonnees, Couleur)
    public Element(Coordonnees coordonnees, Couleur couleur) {
        this.coordonnees = coordonnees;
        this.couleur = couleur;
    }

    // 2. Constructeur (abscisse, ordonnee, Couleur)
    public Element(int abscisse, int ordonnee, Couleur couleur) {
        this(new Coordonnees(abscisse, ordonnee), couleur);
    }

    // 3. Constructeur (Coordonnees) -> Couleur par défaut
    public Element(Coordonnees coordonnees) {
        // values()[0] permet de prendre la première couleur de l'enum (ROUGE)
        this(coordonnees, Couleur.values()[0]);
    }

    // 4. Constructeur (abscisse, ordonnee) -> Couleur par défaut
    public Element(int abscisse, int ordonnee) {
        this(new Coordonnees(abscisse, ordonnee), Couleur.values()[0]);
    }

    // --- Accesseurs et Mutateurs ---

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    // --- Redéfinitions ---

    // Le format exigé par le sujet est par exemple : "(12, 7) - VIOLET"
    @Override
    public String toString() {
        return this.coordonnees.toString() + " - " + this.couleur;
    }

    // equals basé sur coordonnees ET couleur
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Element element = (Element) obj;
        return Objects.equals(coordonnees, element.coordonnees) && couleur == element.couleur;
    }

    // hashCode indispensable quand equals() est redéfini
    @Override
    public int hashCode() {
        return Objects.hash(coordonnees, couleur);
    }
}