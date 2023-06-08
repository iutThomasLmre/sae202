/*
 * PileVideException.java                                      23 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package application.pile;

/** 
 * Exception levée sur une opération sur pile LIFO vide
 * nécessitant une pile non vide (pré-condition en échec)
 * @author info1 2022-2023
 */
public class PileVideException extends RuntimeException {

    /** Exception non documentée */
    public PileVideException() {
        super();
    }

    /** 
     * Exception avec explication de la cause
     * @param message texte expliquant la cause de cette exception
     */
    public PileVideException(String message) {
        super(message);
    }
}
