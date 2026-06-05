package fr.eseo.e3.poo.projet.blox.modele;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GestionnaireScores {

    private static final String NOM_FICHIER = "scores.txt";

    /**
     * Classe interne pour représenter un Score facilement triable.
     */
    public static class EntreeScore implements Comparable<EntreeScore> {
        public String nom;
        public int score;

        public EntreeScore(String nom, int score) {
            this.nom = nom;
            this.score = score;
        }

        @Override
        public int compareTo(EntreeScore autre) {
            // Tri décroissant : le plus grand score se place en premier
            return Integer.compare(autre.score, this.score);
        }
    }

    /**
     * Ajoute un nouveau score à la fin du fichier texte.
     */
    public static void sauvegarderScore(String nom, int score) {
        // Le paramètre "true" dans FileWriter permet d'ajouter à la suite sans écraser le fichier
        try (PrintWriter out = new PrintWriter(new FileWriter(NOM_FICHIER, true))) {
            out.println(nom + "," + score);
        } catch (IOException e) {
            System.err.println("Impossible de sauvegarder le score : " + e.getMessage());
        }
    }

    /**
     * Lit le fichier texte, récupère tous les scores et les renvoie triés.
     */
    public static List<EntreeScore> chargerMeilleursScores() {
        List<EntreeScore> listeScores = new ArrayList<>();
        File fichier = new File(NOM_FICHIER);

        // Si le fichier n'existe pas encore (première partie), on renvoie une liste vide
        if (!fichier.exists()) {
            return listeScores;
        }

        try (Scanner scanner = new Scanner(fichier)) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] parties = ligne.split(","); // On coupe la ligne au niveau de la virgule
                if (parties.length == 2) {
                    listeScores.add(new EntreeScore(parties[0], Integer.parseInt(parties[1])));
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture des scores : " + e.getMessage());
        }

        // On trie la liste du plus grand au plus petit
        Collections.sort(listeScores);
        return listeScores;
    }
}