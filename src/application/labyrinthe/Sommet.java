/*
 * Sommet.java                                    16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */

package application.labyrinthe;

/** 
 * Cette classe représente le sommet d'un graph qui sera aussi la représentation
 * d'une pièce pour le labyrinthe.
 * Sommet doit avoir un numéro.
 * 
 * @author thomas.izard
 * @author thomas.lemaire
 */
public class Sommet {
    private int numero;
    private boolean parcouru;

    /**
     * TODO comment initial state
     * @param numero
     */
    public Sommet(int numero) {
        if(!estValide(numero)) {
            throw new IllegalArgumentException();
        }
        this.numero = numero;
        this.parcouru = false;
    }

    /**
     * TODO comment method role
     * @return .
     */
    public int getNumero() {
        return numero;
    }

    /**
     * TODO comment method role
     * @return .
     */
    public boolean estParcouru() {
        return parcouru;
    }

    /**
     * TODO comment method role
     *
     */
    public void marquerParcouru() {
        parcouru = true;
    }

    /**
     * 
     */
    public void demarquerParcouru() {
        parcouru = false;
    }

    public String toString() {
        Integer tempNumero = this.numero;
        return "p" + tempNumero.toString();
    }

    /**
     * TODO comment method role
     * @param numero
     */
    private static boolean estValide(int numero) {
        return numero >= 0 && numero < Integer.MAX_VALUE - 1;
    }
}