/*
 * TestSommet.java                                      31 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ascendante.lab.Sommet;

/** 
 * Réalise des tests sur la classe Sommet
 * @author constant.nguyen
 * @author thomas.izard
 */
class TestSommet {

    /**
     * Fixture de text = jeu de test à l'image fixe 
     * utilisable dans plusieurs tests
     */
    private List<Sommet> correctes;
    


    /**
     * Ajoute des Sommet corrects 
     * dans une liste de sommet pour les tests futurs
     */
    @BeforeEach
    public void testSommetOk() {         
        correctes = new ArrayList<>(15);
        correctes.add(new Sommet(0, ""));
        correctes.add(new Sommet(10, ""));
        correctes.add(new Sommet(100, ""));
        correctes.add(new Sommet(1000, ""));
    }

    /**
     * Test des sommets qui ne sont pas corrects pour vérifier
     * que notre méthode isValide de la classe Sommet marche correctement
     */
    @Test
    void testSommetNOk() {         
        assertThrows(IllegalArgumentException.class, () -> new Sommet(-1, "marque"));
        assertThrows(IllegalArgumentException.class, () -> new Sommet(-10, ""));
        assertThrows(IllegalArgumentException.class, () -> new Sommet(10, "marque"));
    }

    
    /**
     * Test de notre méthode qui renvoie le numéro d'un sommet
     * à l'aide d'un tableau qui possède les résultats attendus
     */
    @Test
    void testGetNumero() {
        final int[] NUMERO_OK = {0, 10, 100, 1000};

        for (int i = 0; i < NUMERO_OK.length; i++) {
            assertEquals(NUMERO_OK[i], correctes.get(i).getNumero());
        }
    }

    /**
     * Test de notre méthode qui renvoie la marque d'un sommet
     * à l'aide d'un tableau qui possède les résultats attendus
     */
    @Test
    void testGetMarque() {
        final String[] MARQUE_OK = {"", "", "", ""};

        for (int i = 0; i < MARQUE_OK.length; i++) {
            assertEquals(MARQUE_OK[i], correctes.get(i).getMarque());
        }
    }

   
    /**
     * Vérifie que les sommets de départ ne sont pas marqués
     */
    @Test
    void testEstMarque() {

        for (int i = 0; i < correctes.size(); i++) {
            assertFalse(correctes.get(i).estMarque());
        }
    }

    /**
     * Vérifie que notre méthode de marquage d'un sommet fonctionne
     * à l'aide d'un tableau contenant les marques attendues après le marquage
     */
    @Test
    void testMarquage() {
        
        final String[] MARQUAGE_OK = {"0", "1", "2", "3"};
        
        for (int i = 0; i < correctes.size(); i++) {
            correctes.get(i).marquage("" + i);            
        }
        
        for (int i = 0; i < MARQUAGE_OK.length; i++) {
            assertEquals(MARQUAGE_OK[i], correctes.get(i).getMarque());
        }
    }

    /** Test de comaprer deux sommet*/
    @Test
    void testCompareTo() {
        fail("Not yet implemented");
    }

}
