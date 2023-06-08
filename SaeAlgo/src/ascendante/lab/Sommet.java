/*
 * Sommet.java                                      16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.lab;


/** 
 * Création d'un sommet utilisable dans la création de notre labyrinthe
 * @author constant.nguyen
 * @author thomas.izard
 */
public class Sommet implements Comparable <Sommet>{
    
    private int numero;
    private String marque;
    private boolean parcouru;
    
    /**
     * Initialise un sommet avec un numéro est une marque vide si il est valide
     * @param numero
     * @param marque 
     */
    public Sommet(int numero, String marque) {
        
        if (!isValide(numero, marque)) {
            throw new IllegalArgumentException("Le sommet n'est pas valide");
        }
        
        this.numero = numero;  
        this.marque = marque;
        this.parcouru = false;
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
     * Vérifie qu'un sommet créé est valide
     * @param récupère le numéro du sommet
     * @param récupère la marque du sommet
     * @return
     */
    
    private static boolean isValide(int num, String marque) {
        return 0 <= num  && marque == "";
        
    }
    
    /** Vérifie si un sommet possède déja une marque ou non
     * @return Si le sommet est déja marque ou non
     */
    public boolean estMarque() {
        return marque != "";
    }
    
    /**
    * return le boolean parcouru
    * @return .
    */
   public boolean estParcouru() {
       return parcouru;
   }
    
    /**
     * Marque un sommet comme étant parcouru à l'aide d'un boolean
     *
     */
    public void marquerParcouru() {
        parcouru = true;
    }
    
    /**
     * Enleve la marque d'un sommet parcouru     */
    public void demarquerParcouru() {
        parcouru = false;
    }
    
    /** Permet d'attribuer une marque à un sommet
     * @param marque que l'on va attribuer au sommet
     */
    public void marquage(String marque) {
        this.marque = marque;
    }
    

    
    /* non javadoc - @see java.lang.Comparable#compareTo(java.lang.Object) */
    @Override
    public int compareTo(Sommet o) {
        // TODO Auto-generated method stub
        return 0;
    }
}
