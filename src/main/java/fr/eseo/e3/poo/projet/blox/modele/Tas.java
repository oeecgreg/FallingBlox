package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tas {

    private Puits puits;
    private List<Element> elements;

    /**
     * Constructeur par défaut (crée un tas vide sans puits associé).
     */
    public Tas() {
        this.puits = null;
        this.elements = new ArrayList<>();
    }

    /**
     * Constructeur à un argument (crée un tas vide associé à un puits).
     */
    public Tas(Puits puits) {
        this.puits = puits;
        this.elements = new ArrayList<>();
    }

    /**
     * Constructeur à deux arguments.
     * Calcule le nombre de lignes nécessaires d'après la formule spécifiée.
     *
     */
    public Tas(Puits puits, int nbElements) {
        this(puits, nbElements, (puits != null) ? (nbElements / puits.getLargeur() + 1) : 0);
    }

    /**
     * Constructeur à trois arguments.
     * Utilise une instance de Random avec un seed quelconque.
     */
    public Tas(Puits puits, int nbElements, int nbLignes) {
        this(puits, nbElements, nbLignes, new Random());
    }

    /**
     * Constructeur à quatre arguments.
     * Permet de passer un objet Random avec un seed connu pour la reproductibilité des tests.
     */
    public Tas(Puits puits, int nbElements, int nbLignes, Random rand) {
        this.puits = puits;
        this.elements = new ArrayList<>();
        this.construireTas(nbElements, nbLignes, rand);
    }

    /**
     * Accesseur permettant de récupérer la liste des éléments du tas.
     */
    public List<Element> getElements() {
        return this.elements;
    }

    /**
     * Accesseur permettant de récupérer le puits associé.
     */
    public Puits getPuits() {
        return this.puits;
    }

    /**
     * Algorithme de construction aléatoire du tas initial au fond du puits.
     * @param nbElements Nombre d'Elements pour la construction du tas.
     * @param nbLignes Nombre de lignes pour la construction du tas.
     * @param rand Entier aléatoire pour répartir les éléments.
     */
    public void construireTas(int nbElements, int nbLignes, Random rand) {
        // 1. Validations de sécurité fondamentales
        if (nbElements < 0 || nbLignes < 0) {
            throw new IllegalArgumentException("Le nombre d'éléments et de lignes ne peut pas être négatif.");
        }
        if (nbElements > 0 && nbLignes == 0) {
            throw new IllegalArgumentException("Le nombre de lignes doit être supérieur à 0 pour insérer des éléments.");
        }

        if (this.puits != null) {
            if (nbLignes > this.puits.getProfondeur()) {
                throw new IllegalArgumentException("Le nombre de lignes du tas dépasse la profondeur du puits.");
            }
            if (nbElements > nbLignes * this.puits.getLargeur()) {
                throw new IllegalArgumentException("Le nombre d'éléments dépasse la capacité maximale des lignes spécifiées.");
            }
        }

        // 2. Répartition aléatoire des éléments selon l'algorithme demandé
        int elementsPlaces = 0;
        while (elementsPlaces < nbElements) {
            int x = rand.nextInt(this.puits.getLargeur());
            // Calcul pour cibler uniquement les lignes tout en bas du puits
            int y = (this.puits.getProfondeur() - nbLignes) + rand.nextInt(nbLignes);

            // Vérification de l'absence d'élément aux coordonnées ciblées (évite les chevauchements)
            boolean occupe = false;
            for (Element e : this.elements) {
                if (e.getCoordonnees().getAbscisse() == x && e.getCoordonnees().getOrdonnee() == y) {
                    occupe = true;
                    break;
                }
            }

            // Si la case est libre, on crée un élément avec une couleur aléatoire
            if (!occupe) {
                Couleur[] couleurs = Couleur.values();
                Couleur couleurAleatoire = couleurs[rand.nextInt(couleurs.length)];

                Element nouvelElement = new Element(new Coordonnees(x, y), couleurAleatoire);
                this.elements.add(nouvelElement);
                elementsPlaces++;
            }
        }
    }
}