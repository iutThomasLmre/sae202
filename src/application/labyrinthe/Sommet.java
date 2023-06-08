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
 * @author constant.nguyen
 */
public class Sommet {
    private int numero;
    private boolean parcouru;

    /**
     * Constructeur du sommet
     * @param numero
     */
    public Sommet(int numero) {
        if(!(numero >= 0 && numero < Integer.MAX_VALUE - 1)) {
            throw new IllegalArgumentException();
        }
        this.numero = numero;
        this.parcouru = false;
    }

    /** @return la valeur du numéro du sommet */
    public int getNumero() {
        return numero;
    }

    /** @return true, si le sommet a été parcourus, sinon false */
    public boolean estParcouru() {
        return parcouru;
    }

    /** Méthode qui permet de marquer le sommet comme étant parcourus */
    public void marquerParcouru() {
        parcouru = true;
    }

    /** Méthode qui permet de supprimer la marque parcourus du sommet */
    public void demarquerParcouru() {
        parcouru = false;
    }

    /** Méthode permettant de transformer en chaine de charactère le sommet */
    public String toString() {
        Integer tempNumero = this.numero;
        return "p" + tempNumero.toString();
    }
}