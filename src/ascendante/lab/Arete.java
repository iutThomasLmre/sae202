/*
 * Arrete.java                                      23 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.lab;

import ascendante.lab.Sommet;

/** 
 * Création d'Arete permetant de créer notre labyrinthe
 * @author constant.nguyen
 * @author thomas.izard
 */
public class Arete implements Comparable <Arete>{
    
    private Sommet sommetA;
    private Sommet sommetB;
    
    /**
     * Initialise une Arete entre deux sommet valide
     * @param sommetA 
     * @param sommetB  
     */
    public Arete(Sommet sommetA, Sommet sommetB) {
        this.sommetA = sommetA;
        this.sommetB = sommetB;
    }
    
    /** @return le sommet A */
    public Sommet getSommetA() {
        return sommetA;
    }
    
    /** @return le sommet B */
    public Sommet getSommetB() {
        return sommetB;
    }

    /* non javadoc - @see java.lang.Comparable#compareTo(java.lang.Object) */
    @Override
    public int compareTo(Arete o) {
        // TODO Auto-generated method stub
        return 0;
    }



}
