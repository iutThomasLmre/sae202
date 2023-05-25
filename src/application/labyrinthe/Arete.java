/*
 * Arete.java                                     18 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 */
public class Arete {
    private Sommet sommetA;
    private Sommet sommetB;

    /** TODO comment initial state
     * @param sommetA
     * @param sommetB
     */
    public Arete(Sommet sommetA, Sommet sommetB) {
        this.sommetA = sommetA;
        this.sommetB = sommetB;
    }

    /**
     * TODO comment method role
     * @return 0
     */
    public Sommet getSommetA() {
        return sommetA;
    }

    /**
     * TODO comment method role
     * @return 0
     */
    public Sommet getSommetB() {
        return sommetB;
    }
    
    /**
     */
    public String toString() {
        return "(" + sommetA + ", " + sommetB + ")";
    }
}