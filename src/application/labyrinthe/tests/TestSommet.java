/*
 * TestSommet.java                                16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe.tests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.labyrinthe.Sommet;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 */
class TestSommet {
    
    /** */
    private List<Sommet> correctes;

    /** 
     * TODO comment method role
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        correctes = new ArrayList<Sommet>(15);
        correctes.add(new Sommet(1));
        correctes.add(new Sommet(2));
        correctes.add(new Sommet(3));
        
        correctes.get(1).marquerParcouru();
    }

    @Test
    @DisplayName("Tests du getter Identifiant")
    void testGetNumero() {
        final int[] IDENTIFIANT_OK = {1, 2, 3};
        for (int i = 0; i < IDENTIFIANT_OK.length; i++) {
            assertEquals(IDENTIFIANT_OK[i], correctes.get(i).getNumero());
        }
    }
    
    @Test
    @DisplayName("Tests de la méthode estVisiter")
    void testGetParcouru() {
        final boolean[] VISITER_OK = {false, true, false};
        for (int i = 0; i < VISITER_OK.length; i++) {
            assertEquals(VISITER_OK[i], correctes.get(i).estParcouru());
        }
    }
    
    @Test
    @DisplayName("Tests de la méthode toString")
    void testToString() {
        final String[] STRING_OK = {"p1", "p2", "p3"};
        for (int i = 0; i < STRING_OK.length; i++) {
            assertEquals(STRING_OK[i], correctes.get(i).toString());
        }
    }
}
