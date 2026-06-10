package fr.eseo.e3.poo.projet.blox;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.modele.GestionnaireScores;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.MenuPrincipal;
import fr.eseo.e3.poo.projet.blox.vue.PanneauInformation;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class FallingBloxVersion1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // 1. Configuration de la fenêtre en Plein Écran total
            JFrame fenetre = new JFrame("Falling Blox - Master Edition");
            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fenetre.setUndecorated(true); // Supprime la barre supérieure pour combler tout l'écran

            // 2. Gestionnaire d'écrans (CardLayout)
            CardLayout cardLayout = new CardLayout();
            JPanel conteneurEcrans = new JPanel(cardLayout);
            fenetre.add(conteneurEcrans);

            // --- INSTANCIATION DE LA ZONE DE JEU ---
// --- INSTANCIATION DE LA ZONE DE JEU ---
            JPanel ecranJeu = new JPanel(new BorderLayout());
            Puits puitsJeu = new Puits(10, 20);

            // CORRECTION : On demande à la grille de prendre 100% de la hauteur de l'écran
            java.awt.Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int tailleBloc = tailleEcran.height / puitsJeu.getProfondeur();

            VuePuits vuePuitsJeu = new VuePuits(puitsJeu, tailleBloc);
            PanneauInformation panneauInfo = new PanneauInformation(puitsJeu);

            JPanel centreurJeu = new JPanel();
            centreurJeu.setBackground(Color.DARK_GRAY);
            centreurJeu.add(vuePuitsJeu);

            ecranJeu.add(centreurJeu, BorderLayout.CENTER);
            ecranJeu.add(panneauInfo, BorderLayout.EAST);

            // --- INSTANCIATION DU MENU PRINCIPAL ---
            MenuPrincipal menuAccueil = new MenuPrincipal(() -> {
                // Action exécutée au clic/touche : Bascule vers l'écran de jeu
                cardLayout.show(conteneurEcrans, "JEU");

                // Initialisation des pièces du modèle de jeu
                UsineDePiece.setMode(UsineDePiece.ALEATOIRE_COMPLET);
                puitsJeu.setPieceSuivante(UsineDePiece.genererTetromino());
                puitsJeu.setPieceSuivante(UsineDePiece.genererTetromino());

                // Instanciation de l'horloge de chute
                new Gravite(vuePuitsJeu);

                // Capture immédiate du focus clavier pour éviter les touches bloquées
                vuePuitsJeu.requestFocusInWindow();
            });

            // 3. Assemblage des calques
            conteneurEcrans.add(menuAccueil, "MENU");
            conteneurEcrans.add(ecranJeu, "JEU");
            cardLayout.show(conteneurEcrans, "MENU");

            // 4. Écouteur de fin de partie pour l'enregistrement du score
            puitsJeu.addPropertyChangeListener(event -> {
                if (Puits.MODIFICATION_PARTIE_TERMINEE.equals(event.getPropertyName())) {
                    boolean terminee = (boolean) event.getNewValue();
                    if (terminee) {
                        SwingUtilities.invokeLater(() -> {
                            int scoreFinal = puitsJeu.getScore();
                            String nom = JOptionPane.showInputDialog(
                                    fenetre,
                                    "Partie terminée !\nVotre score : " + scoreFinal + "\nEntrez votre nom :",
                                    "Game Over",
                                    JOptionPane.QUESTION_MESSAGE
                            );
                            if (nom != null && !nom.trim().isEmpty()) {
                                GestionnaireScores.sauvegarderScore(nom.trim(), scoreFinal);
                                panneauInfo.chargerScores();
                            }
                        });
                    }
                }
            });

            fenetre.setVisible(true);
            menuAccueil.requestFocusInWindow(); // Donne le focus au menu dès l'ouverture
        });
    }
}