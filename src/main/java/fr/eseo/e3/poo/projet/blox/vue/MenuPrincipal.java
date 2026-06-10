package fr.eseo.e3.poo.projet.blox.vue;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuPrincipal extends JPanel {

    private final Runnable actionLancerJeu;
    private final Timer animationTimer;
    private final Random random = new Random();

    // Dimensions de la grille de simulation en arrière-plan
    private final int NB_LIGNES = 40;
    private final int NB_COLONNES = 60;
    private final int[][] grilleFond = new int[NB_LIGNES][NB_COLONNES];
    private final Color[][] couleursFond = new Color[NB_LIGNES][NB_COLONNES];
    private final List<PieceMenu> piecesChute = new ArrayList<>();

    // Matrice du Logo dessiné bloc par bloc (chaque lettre correspond à son caractère pour la couleur)
    private static final String[] LOGO = {
            "FFFF  AA  L    L    III N   N GGGG   BBBB L    OOOO X   X",
            "F    A  A L    L     I  NN  N G      B  B L    O  O  X X ",
            "FFF  AAAA L    L     I  N N N G GG   BBBB L    O  O   X  ",
            "F    A  A L    L     I  N  NN G  G   B  B L    O  O  X X ",
            "F    A  A LLLL LLLL III N   N GGGG   BBBB LLLL OOOO X   X"
    };

    // Structure interne pour gérer les pièces indépendantes de l'arrière-plan
    private static class PieceMenu {
        int x, y;
        int[][] forme;
        Color couleur;

        PieceMenu(int x, int y, int[][] forme, Color couleur) {
            this.x = x; this.y = y; this.forme = forme; this.couleur = couleur;
        }
    }

    // Formes des pièces classiques pour l'animation du fond
    private final int[][][] FORMES_PIECES = {
            {{1, 1}, {1, 1}},          // O
            {{1, 1, 1, 1}},            // I
            {{0, 1, 0}, {1, 1, 1}},    // T
            {{1, 0, 0}, {1, 1, 1}},    // L
            {{0, 0, 1}, {1, 1, 1}},    // J
            {{0, 1, 1}, {1, 1, 0}},    // S
            {{1, 1, 0}, {0, 1, 1}}     // Z
    };

    private final Color[] COULEURS_TETRIS = {
            Color.YELLOW, Color.CYAN, new Color(128, 0, 128),
            Color.ORANGE, Color.BLUE, Color.GREEN, Color.RED
    };

    public MenuPrincipal(Runnable actionLancerJeu) {
        this.actionLancerJeu = actionLancerJeu;
        this.setBackground(Color.BLACK);

        // Timer principal cadencé à 60 FPS pour les animations et la pluie de briques
        this.animationTimer = new Timer(16, e -> rafraichirAnimation());
        this.animationTimer.start();

        // Écouteurs d'événements pour démarrer la partie sur n'importe quelle action
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { démarrerPartie(); }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { démarrerPartie(); }
        });

        this.setFocusable(true);
    }

    private void démarrerPartie() {
        this.animationTimer.stop();
        this.actionLancerJeu.run();
    }

    private int compteurChute = 0;

    private void rafraichirAnimation() {
        compteurChute++;

        // Toutes les 10 frames (~160ms), on fait progresser la pluie de briques
        if (compteurChute >= 10) {
            compteurChute = 0;

            // Faire apparaître une nouvelle pièce aléatoirement
            if (piecesChute.size() < 12 && random.nextInt(5) == 0) {
                int type = random.nextInt(FORMES_PIECES.length);
                int colAléatoire = random.nextInt(NB_COLONNES - 4);
                piecesChute.add(new PieceMenu(colAléatoire, -2, FORMES_PIECES[type], COULEURS_TETRIS[type]));
            }

            // Gérer la chute et l'empilement des briques de fond
            for (int i = piecesChute.size() - 1; i >= 0; i--) {
                PieceMenu p = piecesChute.get(i);
                boolean collision = false;

                // Vérifier si la pièce touche le bas ou un bloc empilé
                for (int r = 0; r < p.forme.length; r++) {
                    for (int c = 0; c < p.forme[r].length; c++) {
                        if (p.forme[r][c] == 1) {
                            int nextY = p.y + r + 1;
                            int currX = p.x + c;
                            if (nextY >= NB_LIGNES || (nextY >= 0 && grilleFond[nextY][currX] == 1)) {
                                collision = true;
                                break;
                            }
                        }
                    }
                }

                if (collision) {
                    // Empiler la pièce dans la grille de fond
                    for (int r = 0; r < p.forme.length; r++) {
                        for (int c = 0; c < p.forme[r].length; c++) {
                            if (p.forme[r][c] == 1 && p.y + r >= 0) {
                                grilleFond[p.y + r][p.x + c] = 1;
                                couleursFond[p.y + r][p.x + c] = p.couleur;
                            }
                        }
                    }
                    piecesChute.remove(i);

                    // Si le tas s'approche trop du haut, on nettoie progressivement le bas
                    if (p.y <= 5) {
                        for (int r = NB_LIGNES - 1; r > 0; r--) {
                            System.arraycopy(grilleFond[r - 1], 0, grilleFond[r], 0, NB_COLONNES);
                            System.arraycopy(couleursFond[r - 1], 0, couleursFond[r], 0, NB_COLONNES);
                        }
                        java.util.Arrays.fill(grilleFond[0], 0);
                    }
                } else {
                    p.y++;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        // Calcul dynamique de la taille des blocs pour occuper tout l'écran
        int tailleBlocFond = Math.max(w / NB_COLONNES, h / NB_LIGNES) + 1;

        // 1. Dessin des briques empilées au fond
        for (int r = 0; r < NB_LIGNES; r++) {
            for (int c = 0; c < NB_COLONNES; c++) {
                if (grilleFond[r][c] == 1) {
                    g2D.setColor(couleursFond[r][c]);
                    g2D.fillRect(c * tailleBlocFond, r * tailleBlocFond, tailleBlocFond, tailleBlocFond);
                    g2D.setColor(Color.BLACK);
                    g2D.drawRect(c * tailleBlocFond, r * tailleBlocFond, tailleBlocFond, tailleBlocFond);
                }
            }
        }

        // 2. Dessin des pièces en cours de chute
        for (PieceMenu p : piecesChute) {
            g2D.setColor(p.couleur);
            for (int r = 0; r < p.forme.length; r++) {
                for (int c = 0; c < p.forme[r].length; c++) {
                    if (p.forme[r][c] == 1 && p.y + r >= 0) {
                        g2D.fillRect((p.x + c) * tailleBlocFond, (p.y + r) * tailleBlocFond, tailleBlocFond, tailleBlocFond);
                        g2D.setColor(Color.BLACK);
                        g2D.drawRect((p.x + c) * tailleBlocFond, (p.y + r) * tailleBlocFond, tailleBlocFond, tailleBlocFond);
                        g2D.setColor(p.couleur);
                    }
                }
            }
        }

        // 3. Voile d'ombrage translucide pour faire ressortir le premier plan
        g2D.setColor(new Color(0, 0, 0, 190));
        g2D.fillRect(0, 0, w, h);

        // 4. Dessin du Logo avec de vrais blocs Tetris 3D
        int tailleBlocLogo = Math.min(w / 65, 22);
        int logoLargeur = LOGO[0].length() * tailleBlocLogo;
        int startX = (w - logoLargeur) / 2;
        int startY = h / 3;

        for (int row = 0; row < LOGO.length; row++) {
            for (int col = 0; col < LOGO[row].length(); col++) {
                char lettre = LOGO[row].charAt(col);
                if (lettre != ' ') {
                    Color couleurBloc;
                    switch (lettre) {
                        case 'F': case 'X': couleurBloc = Color.RED; break;
                        case 'A': couleurBloc = Color.GREEN; break;
                        case 'L': couleurBloc = Color.ORANGE; break;
                        case 'I': case 'O': couleurBloc = Color.CYAN; break;
                        case 'N': couleurBloc = Color.BLUE; break;
                        case 'G': couleurBloc = new Color(138, 43, 226); break; // Violet
                        case 'B': couleurBloc = Color.YELLOW; break;
                        default: couleurBloc = Color.GRAY;
                    }
                    int bx = startX + (col * tailleBlocLogo);
                    int by = startY + (row * tailleBlocLogo);

                    g2D.setColor(couleurBloc);
                    g2D.fill3DRect(bx, by, tailleBlocLogo, tailleBlocLogo, true);
                    g2D.setColor(Color.BLACK);
                    g2D.drawRect(bx, by, tailleBlocLogo, tailleBlocLogo);
                }
            }
        }

        // 5. Texte d'invitation clignotant
        if ((System.currentTimeMillis() % 1200) < 600) {
            g2D.setFont(new Font("Arial", Font.BOLD, 28));
            g2D.setColor(Color.WHITE);
            String msg = "APPUYEZ SUR UNE TOUCHE POUR LANCER";
            FontMetrics fm = g2D.getFontMetrics();
            g2D.drawString(msg, (w - fm.stringWidth(msg)) / 2, (int) (h * 0.75));
        }
    }
}