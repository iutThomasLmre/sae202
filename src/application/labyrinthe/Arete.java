/*
 * Arete.java                                     18 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe;

/** 
 * Classe permettant de créer une arête composée de 2 sommets qui représente
 * un lien.
 * @author thomas.izard
 * @author thomas.lemaire
 * @author constant.nguyen
 */
public class Arete {
    private Sommet sommetA;
    private Sommet sommetB;

    /**
     * Constructeur de l'arêtes
     * @param sommetA , premier sommet de l'arête
     * @param sommetB , second sommet de l'arête
     */
    public Arete(Sommet sommetA, Sommet sommetB) {
        this.sommetA = sommetA;
        this.sommetB = sommetB;
    }

    /** @return la valeur du sommet A */
    public Sommet getSommetA() {
        return sommetA;
    }

    /** @return la valeur du sommet B */
    public Sommet getSommetB() {
        return sommetB;
    }
    
    /** @return la représentation en chaine de caractère de l'arête */
    public String toString() {
        return "(" + sommetA + ", " + sommetB + ")";
    }
}