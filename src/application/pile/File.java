/*
 * File.java                                      16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */

package application.pile;

import java.util.ArrayList;
import java.util.List;

import application.labyrinthe.Sommet;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 *
 */
public class File {
    
    /** Liste des éléments de la pile LIFO */
    private List<Object> pile;
    
    /** 
     * TODO comment initial state
     * 
     */
    public File() {
        this.pile = new ArrayList<Object>();
    }
    
    /**
     * TODO comment method role
     * @param objet
     */
    public void empiler(Object objet) {
        pile.add(objet);
    }
    
    /**
     * TODO comment method role
     *
     */
    public void depiler() {
       pile.remove(pile.size() - 1); 
    }
    
    /**
     * TODO comment method role
     * @return 0
     */
    public boolean estVide() {
        return pile.size() == 0;
    }
    
    /**
     * TODO comment method role
     * @return 0
     */
    public Object getSommet() {
        if (this.estVide()) {
            throw new IllegalArgumentException();
        }
        return pile.get(pile.size() - 1);
    }
    
    /**
     * TODO comment method role
     * @return 0
     */
    public int getTaille() {
        return pile.size();
    }

}
