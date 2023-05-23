/*
 * TestLabyrinthe.java                            19 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe.tests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.labyrinthe.Labyrinthe;
import application.labyrinthe.Sommet;
import application.labyrinthe.Arete;

/**
 * TODO comment class responsibility (SRP)
 * @author thoma
 *
 */
class TestLabyrinthe {

    private List<Labyrinthe> correctes;
    
    /** 
     * TODO comment method role
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        // Créer un labyrinthe avec 10 pièces
        correctes = new ArrayList<Labyrinthe>(15);
        correctes.add(new Labyrinthe(10));
    }

    /** 
     * TODO comment method role
     * 
     */
    @Test
    @DisplayName("Test de la construction du labyrinthe en backtracking")
    void testConstructionBacktracking() {
        // Construire le labyrinthe en utilisant l'algorithme de backtracking
        correctes.get(0).constructionBacktracking(); 
        
        List<Arete> aretes = correctes.get(0).getMurs();
  
        // Vérifier que la liste des arêtes n'est pas vide
        assertFalse(aretes.isEmpty()); 

        List<Sommet> pieces = correctes.get(0).getPieces();
        
        for (Sommet sommet : pieces) {
            // Vérifier que tous les sommets ont été parcourus
            assertTrue(sommet.estParcouru()); 
        }
    }

    /** 
     * TODO comment method role
     * 
     */
    @Test
    @DisplayName("Test des voisins parcourus")
    void testTrouverVoisinsNonParcourus() {
        Sommet sommet = new Sommet(0);
        List<Sommet> voisins = correctes.get(0).trouverVoisinsNonParcourus(sommet);

        // Vérifier le nombre de voisins non parcourus
        assertEquals(1, voisins.size()); 
        // Vérifier que le voisin n'est pas parcouru
        assertFalse(voisins.get(0).estParcouru()); 
    }

    /** 
     * TODO comment method role
     * 
     */
    @Test
    @DisplayName("Test de la sauvegarde du labyrinthe")
    void testSauvegarder() {
        // TODO: Implémenter ce test une fois que la méthode sauvegarder() est implémentée
    }
}