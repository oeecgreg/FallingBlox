package fr.eseo.e3.poo.projet.blox;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.PanneauInformation;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class FallingBloxVersion1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Puits puits;

                // 1. Analyse des arguments de la ligne de commande pour le Tas initial
                if (args.length == 1) {
                    int nbElements = Integer.parseInt(args[0]);
                    int nbLignes = (nbElements / Puits.LARGEUR_PAR_DEFAUT) + 1;
                    puits = new Puits(Puits.LARGEUR_PAR_DEFAUT, Puits.PROFONDEUR_PAR_DEFAUT, nbElements, nbLignes);
                } else if (args.length >= 2) {
                    int nbElements = Integer.parseInt(args[0]);
                    int nbLignes = Integer.parseInt(args[1]);
                    puits = new Puits(Puits.LARGEUR_PAR_DEFAUT, Puits.PROFONDEUR_PAR_DEFAUT, nbElements, nbLignes);
                } else {
                    puits = new Puits(); // Aucun argument = puits vide
                }

                // 2. Création des Vues
                VuePuits vuePuits = new VuePuits(puits, 35);
                PanneauInformation panneauInfo = new PanneauInformation(puits);

                // 3. Configuration de la Fenêtre Principale (JFrame)
                JFrame fenetre = new JFrame("Falling Blox");
                fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fenetre.setLayout(new BorderLayout());

                // 4. Ajout des composants aux bons endroits
                fenetre.add(vuePuits, BorderLayout.CENTER);
                fenetre.add(panneauInfo, BorderLayout.EAST);

                // 5. Initialisation du jeu
                UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
                puits.setPieceSuivante(UsineDePiece.genererTetromino());
                puits.setPieceSuivante(UsineDePiece.genererTetromino()); // Déclenche l'affichage dans le PanneauInfo !

                // 6. Affichage final de la fenêtre
                fenetre.pack();
                fenetre.setResizable(false);
                fenetre.setLocationRelativeTo(null);
                fenetre.setVisible(true);

                // 7. Démarrage de la chute automatique
                Gravite gravite = new Gravite(vuePuits);
            }
        });
    }
}