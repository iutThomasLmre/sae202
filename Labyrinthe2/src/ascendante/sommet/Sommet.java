/*
 * Sommet.java                                      16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.sommet;


/** TODO comment class responsibility (SRP)
 * @author izard.thomas
 * @author constant.nguyen
 *
 */
public class Sommet implements Comparable <Sommet>{
    
    private int numero;
    
    private String marque;
    
    /**
     * TODO comment method role 
     * @param numero
     * @param marque 
     */
    public Sommet(int numero, String marque) {
        
        if (!isValide(numero, marque)) {
            throw new IllegalArgumentException("Le sommet n'est pas valide");
        }
        
        this.numero = numero;  
        this.marque = marque;
    }
    
    /** @return le numéro du sommet */
    public int getNumero() {
        return numero;
    }
    
    /** @return le numéro du sommet */
    public String getMarque() {
        return marque;
    }
    
    /**
     * @return boolean marque
     */
    public boolean estMarque() {
        return marque != "";
    }
    
    /**
     * @param marque
     */
    public void marquer(String marque) {
        this.marque = marque;
    }
    /**
     * TODO comment method role
     * @param numero
     * @param marque
     * @return
     */
    
    private static boolean isValide(int numero, String marque) {
        return 0 <= numero && marque == "";
        
    }

    /* non javadoc - @see java.lang.Comparable#compareTo(java.lang.Object) */
    @Override
    public int compareTo(Sommet o) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    
    
    
    
    
}
