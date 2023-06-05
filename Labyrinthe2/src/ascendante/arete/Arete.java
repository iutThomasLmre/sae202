/*
 * Arrete.java                                      23 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.arete;

import ascendante.sommet.Sommet;

/** Création d'une arête d'un graphe avec sommet A et B
 * sommet A correspondant au sommet de l'extrémité initiale
 * sommet B correspondant au sommet de l'extrémité finale
 * @author izard.thomas
 * @author constant.nguyen
 */
public class Arete implements Comparable <Arete>{
    
    private Sommet sommetA;
    private Sommet sommetB;
    
    /**
     * Initialise une arête avec un sommet A et un sommet B
     * soit une extrémité initiale et une extrémité finale
     * @param sommetA le sommet de l'extrémité initiale
     * @param sommetB le sommet de l'extrémité finale
     */
    public Arete(Sommet sommetA, Sommet sommetB) {
        this.sommetA = sommetA;
        this.sommetB = sommetB;
    }
    
    /**
     * Renvoie le sommet de l'extremite initiale de l'arete
     * @return sommetA le sommet de l'extremite initiale
     */
    public Sommet getSommetA() {
        return sommetA;
    }
    
    /**
     * Renvoie le sommet de l'extremite finale de l'arete
     * @return sommetB le sommet de l'extremite finale
     */
    public Sommet getSommetB() {
        return sommetB;
    }

    @Override
    public int compareTo(Arete o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
