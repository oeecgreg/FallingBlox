package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.eseo.e3.poo.projet.blox.modele.GestionnaireScores;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class PanneauInformation extends JPanel implements PropertyChangeListener {

    private VuePiece vuePiece;
    private int score = 0;
    private int lignes = 0;

    private final Puits puits;
    private List<GestionnaireScores.EntreeScore> meilleursScores;
    private final JButton btnRejouer;

    public PanneauInformation(Puits puits) {
        this.puits = puits;
        this.setLayout(null); // Nécessaire pour positionner le bouton manuellement
        this.setPreferredSize(new Dimension(180, 525)); // Taille calée sur la hauteur du puits

        if (puits != null) {
            puits.addPropertyChangeListener(this);
            this.score = puits.getScore();
        }

        // 1. Création du bouton Rejouer
        this.btnRejouer = new JButton("Rejouer");
        this.btnRejouer.setBounds(40, 450, 100, 40); // (X, Y, Largeur, Hauteur)
        this.btnRejouer.setFocusable(false); // CRUCIAL : Empêche le bouton de bloquer les touches du clavier !

        this.btnRejouer.addActionListener(e -> {
            if (this.puits != null) {
                this.puits.reinitialiser();
            }
        });
        this.add(this.btnRejouer);

        // 2. On charge les scores au démarrage
        this.chargerScores();
    }

    /**
     * Met à jour la liste des scores depuis le fichier et rafraîchit l'affichage.
     */
    public void chargerScores() {
        this.meilleursScores = GestionnaireScores.chargerMeilleursScores();
        this.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(event.getPropertyName())) {
            Piece prochainePiece = (Piece) event.getNewValue();
            this.vuePiece = (prochainePiece != null) ? new VuePiece(prochainePiece, 20) : null;
            this.repaint();
        }
        else if (Puits.MODIFICATION_SCORE.equals(event.getPropertyName())) {
            this.score = (int) event.getNewValue();
            this.repaint();
        }
        else if (Puits.MODIFICATION_LIGNES.equals(event.getPropertyName())) {
            this.lignes = (int) event.getNewValue();
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        FontMetrics fm;

        // A. Dessin de la pièce suivante (en haut)
        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }

        // B. Dessin des Statistiques
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        fm = g2D.getFontMetrics();

        String texteNiveau = "Niveau : " + (this.lignes / 10);
        String texteLignes = "Lignes : " + this.lignes;
        String texteScore = "Score : " + this.score;

        g2D.drawString(texteNiveau, (this.getWidth() - fm.stringWidth(texteNiveau)) / 2, 130);
        g2D.drawString(texteLignes, (this.getWidth() - fm.stringWidth(texteLignes)) / 2, 150);
        g2D.drawString(texteScore, (this.getWidth() - fm.stringWidth(texteScore)) / 2, 170);

        // C. NOUVEAU : Dessin du Tableau des Scores
        g2D.setFont(new Font("Arial", Font.BOLD, 12));
        fm = g2D.getFontMetrics();
        String titreScores = "--- MEILLEURS SCORES ---";
        g2D.drawString(titreScores, (this.getWidth() - fm.stringWidth(titreScores)) / 2, 230);

        if (this.meilleursScores != null) {
            int yScore = 260;
            int limite = Math.min(5, this.meilleursScores.size());
            for (int i = 0; i < limite; i++) {
                GestionnaireScores.EntreeScore entree = this.meilleursScores.get(i);
                // Affichage formaté : "1. Nom : 1500"
                String ligneScore = (i + 1) + ". " + entree.nom + " : " + entree.score;
                g2D.drawString(ligneScore, 15, yScore);
                yScore += 25;
            }
        }
        g2D.dispose();
    }
}