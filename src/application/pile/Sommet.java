/*
 * Sommet.java                                    16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */

package application.pile;

/** 
 * Cette classe représente le sommet d'un graph qui sera aussi la représentation
 * d'une pièce pour le labyrinthe.
 * Sommet doit avoir un identifiant qui sera aussi son nom.
 * 
 * @author thomas.izard
 * @author thomas.lemaire
 */
public class Sommet {

    /** */
    private boolean visiter;
    
    /** */
    private String identifiant;
    
    /** 
     * TODO comment initial state
     * @param identifiant 
     */
    public Sommet(String identifiant) {
        this.identifiant = identifiant;
        this.visiter     = false;
    }
    
    /** 
     * TODO comment method role
     * @return boolean
     */
    public boolean estVisiter() {
        return this.visiter;
    }
    
    /** 
     * TODO comment method role
     * @param dejaVisiter
     */
    public void setVisiter(boolean dejaVisiter) {
        this.visiter = dejaVisiter;
    }
    
    /** 
     * TODO comment method role
     * @return String
     */
    public String getIdentifiant() {
        return this.identifiant;
    }
}
