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
        this.setLayout(null);

        // 1. On élargit le panneau à 250 pixels de large
        this.setPreferredSize(new Dimension(250, 0));

        if (puits != null) {
            puits.addPropertyChangeListener(this);
            this.score = puits.getScore();
        }

        this.btnRejouer = new JButton("Rejouer");
        // 2. On descend et on centre le bouton
        this.btnRejouer.setBounds(75, 500, 100, 40);
        this.btnRejouer.setFocusable(false);

        this.btnRejouer.addActionListener(e -> {
            if (this.puits != null) {
                this.puits.reinitialiser();
            }
        });
        this.add(this.btnRejouer);
        this.chargerScores();
    }

    public void chargerScores() {
        this.meilleursScores = GestionnaireScores.chargerMeilleursScores();
        this.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(event.getPropertyName())) {
            Piece prochainePiece = (Piece) event.getNewValue();
            // 3. On affiche la pièce suivante avec des briques plus grosses (30 pixels)
            this.vuePiece = (prochainePiece != null) ? new VuePiece(prochainePiece, 30) : null;
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

        // On décale très légèrement la pièce vers le bas pour l'aérer
        if (this.vuePiece != null) {
            g2D.translate(0, 20);
            this.vuePiece.afficherPiece(g2D);
            g2D.translate(0, -20);
        }

        g2D.setColor(Color.BLACK);
        // 4. On utilise une police plus grande (Taille 18)
        g2D.setFont(new Font("Arial", Font.BOLD, 18));
        fm = g2D.getFontMetrics();

        String texteNiveau = "Niveau : " + (this.lignes / 10);
        String texteLignes = "Lignes : " + this.lignes;
        String texteScore = "Score : " + this.score;

        g2D.drawString(texteNiveau, (this.getWidth() - fm.stringWidth(texteNiveau)) / 2, 200);
        g2D.drawString(texteLignes, (this.getWidth() - fm.stringWidth(texteLignes)) / 2, 230);
        g2D.drawString(texteScore, (this.getWidth() - fm.stringWidth(texteScore)) / 2, 260);

        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        fm = g2D.getFontMetrics();
        String titreScores = "--- MEILLEURS SCORES ---";
        g2D.drawString(titreScores, (this.getWidth() - fm.stringWidth(titreScores)) / 2, 330);

        if (this.meilleursScores != null) {
            int yScore = 370;
            int limite = Math.min(5, this.meilleursScores.size());
            for (int i = 0; i < limite; i++) {
                GestionnaireScores.EntreeScore entree = this.meilleursScores.get(i);
                String ligneScore = (i + 1) + ". " + entree.nom + " : " + entree.score;
                g2D.drawString(ligneScore, 20, yScore);
                yScore += 30;
            }
        }
        g2D.dispose();
    }
}