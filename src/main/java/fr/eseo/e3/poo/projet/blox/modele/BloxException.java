package fr.eseo.e3.poo.projet.blox.modele;

/**
 * Exception spécifique au jeu FallingBlox permettant de signaler
 * les anomalies de déplacement (collisions, sorties du puits).
 * Étant une checked exception (héritant de Exception), elle doit obligatoirement
 * être capturée (try-catch) ou propagée (throws).
 */
public class BloxException extends Exception {

    // --- Constantes de classe ---
    public static final int BLOX_COLLISION = 1;
    public static final int BLOX_SORTIE_PUITS = 2;

    // Attribut d'instance stockant le code de la cause
    private final int codeCause;

    /**
     * Constructeur de l'exception BloxException.
     * @param message   Le message descriptif de l'erreur.
     * @param codeCause L'entier représentant la cause (BLOX_COLLISION ou BLOX_SORTIE_PUITS).
     */
    public BloxException(String message, int codeCause) {
        // Transmission du message au constructeur de la sur-classe (Exception)
        super(message);
        // Stockage du code d'erreur dans l'instance
        this.codeCause = codeCause;
    }

    /**
     * Accesseur permettant de lire l'entier représentant la cause de l'exception.
     * * Note : Nous n'utilisons pas le nom "getCause()" car cette méthode existe déjà
     * dans la classe Throwable de Java et retourne un objet de type Throwable.
     * @return L'entier codant la raison de la levée d'exception.
     */
    public int getCode() {
        return this.codeCause;
    }
}