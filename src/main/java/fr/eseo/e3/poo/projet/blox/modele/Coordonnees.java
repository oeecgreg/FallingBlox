package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

public class Coordonnees {

    private int abscisse;
    private int ordonnee;

    // Constructeur
    public Coordonnees(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    // Accesseurs (Getters)
    public int getAbscisse() {
        return abscisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    // Mutateurs (Setters)
    public void setAbscisse(int abscisse) {
        this.abscisse = abscisse;
    }

    public void setOrdonnee(int ordonnee) {
        this.ordonnee = ordonnee;
    }

    // Redéfinition de toString() au format strict demandé : "(abscisse, ordonnee)"
    @Override
    public String toString() {
        return "(" + this.abscisse + ", " + this.ordonnee + ")";
    }

    // Redéfinition de equals() pour comparer deux coordonnées
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordonnees that = (Coordonnees) obj;
        return abscisse == that.abscisse && ordonnee == that.ordonnee;
    }

    // Redéfinition de hashCode() indispensable quand on redéfinit equals()
    @Override
    public int hashCode() {
        return Objects.hash(abscisse, ordonnee);
    }
}