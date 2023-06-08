/*
 * PileContigue.java                                      23 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package application.pile;

import java.util.Arrays;

/** 
 * Collection à loi de gestion LIFO Last In First Out
 * <p>
 * Classe non immuable (comme toute collection)
 * @author info1 2022-2023
 * @param <E> type générique des références des éléments empilés (polymorphisme)
 */
public class PileContigue<E> {
    
    /** taille initiale du bloc contigu = 10 réf. */
    private static final int CAPACITE_INITIALE = 10;
    
    /** représentation par bloc contigu de mémoire = tableau */
    private E[] elements;
    
    /** nombre effectif d'éléments empilés */
    private int taille;

    /** Pile vide d'objets */
    public PileContigue() {
        super();
        elements = (E[]) new Object[CAPACITE_INITIALE];
        // taille = 0;  // pile vide
    }

    /**
     * Prédicat testant si aucun élément n'est présent dans cette pile
     * @return true si aucun élément empilé, false sinon
     */
    public boolean isVide() {
        return taille == 0; 
    }
    
    /**
     * Ajoute un élément au sommet de cette pile
     * <p>
     * Commande (fonctionnelle) modifiant l'état de this.
     * @param aAjouter réf de l'élément à empiler (non nulle)
     * @return cette pile après empilement (style fonctionnel)
     * @throws NullPointerException si aAjouter est null
     */
    public PileContigue<E> empiler(E aAjouter) {
        if (aAjouter == null) {
            throw new NullPointerException("Pas d'ajout de réf. nulle");
        }
        assurerCapacite();
        elements[taille] = aAjouter;
        taille++;
        return this;
    }
    
    /** 
     * Réallouer un nouveau bloc (tableau) pour permettre
     * le grossissement de la pile au delà de la capacité initiale
     */
    private void assurerCapacite() {
        final double COEF_GROSSISSEMENT = 1.30; // 30% de plus
        if (taille == elements.length) {
            elements = Arrays.copyOf(elements, 
                                     (int)Math.ceil(taille * COEF_GROSSISSEMENT)
                                    );
        }
        
    }

    /**
     * Retire le dernier élément ajouté cette pile (le plus récent).
     * Dernier Arrivé Premier Sorti (LIFO).
     * <p>
     * Commande (fonctionnelle) modifiant l'état de this.
     * @return cette pile après "dépilement" (style fonctionnel)
     * @throws PileVideException si cette pile est vide
     */
    public PileContigue<E> depiler() {
        preConditionPileNonVide();
        taille--;
        // TODO diminuer la capacité en dessous d'un taux de remplissage
        return this;
    }
    
    /**
     * Accès à l'élément le plus récemment empilé
     * @return réf du dernier élément empilé
     * @throws PileVideException si cette pile est vide
     */
    public E sommet() {
        preConditionPileNonVide();
        return elements[taille -1];  
    }
    
    /** @return la taille de la pile */
    public int taille() {
        return taille;
    }

    /** 
     * Test de pré-condition pour des opérations sur pile non vide
     * @throws PileVideException si la pile est vide
     */
    private void preConditionPileNonVide() throws PileVideException {
        if (isVide()) {
            throw new PileVideException("Pré-condition pile non vide fausse");
        }
    }
}
