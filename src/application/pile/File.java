/*
 * File.java                                      16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */

package application.pile;

import application.labyrinthe.Sommet;

import java.util.ArrayList;
import java.util.List;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 *
 */
public class File {
    
    /** Liste des éléments de la pile LIFO */
    private List<Object> pile;
    
    /** 
     * Création d'une Pile en faisant une list
     * 
     */
    public File() {
        this.pile = new ArrayList<Object>();
    }
    
    /**
     * Cette méthode permet d'empiler un Objet dans notre pile
     * @param objet
     */
    public void empiler(Object objet) {
        pile.add(objet);
    }
    
    /**
     * Cette méthode depile le dernier élément de notre pile
     *
     */
    public void depiler() {
       pile.remove(pile.size() - 1); 
    }
    
    /**
     * Permet de vérifier si notre pile est vide ou non.
     * ELle renvoie True si la pile est vide et False si elle ne l'est pas
     * @return 0
     */
    public boolean estVide() {
        return pile.size() == 0;
    }
    
    /**
     * Permet de récupérer le sommet (dernier élément de notre pile)
     * et le renvoie
     * @return 0
     */
    public Object getSommet() {
        if (this.estVide()) {
            throw new IllegalArgumentException();
        }
        return pile.get(pile.size() - 1);
    }

}
