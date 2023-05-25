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
 * @author thomas.lemaire
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
        // Créer un labyrinthe avec 35 pièces
        correctes = new ArrayList<Labyrinthe>();
        correctes.add(new Labyrinthe(35));
        correctes.add(new Labyrinthe(2));
        correctes.add(new Labyrinthe(24));
        correctes.add(new Labyrinthe(4));
    }
    
    @Test
    @DisplayName("Test labyrinthe")
    void testLabyrinthe() {
        // TODO
    }

    /** TODO comment method role */
    @Test
    @DisplayName("Test de la construction du labyrinthe en backtracking")
    void testConstructionBacktracking() {
        for (int i = 0; i < correctes.size(); i++) {
            // Construire le labyrinthe en utilisant l'algorithme de backtracking
            correctes.get(i).constructionBacktracking(); 
            
            // Vérifier que la liste des arêtes n'est pas vide
            List<Arete> aretes = correctes.get(i).getAretes();
            assertFalse(aretes.isEmpty()); 
            
            // Vérifier que tous les sommets ont été parcourus
            List<Sommet> pieces = correctes.get(i).getSommets();
            for (int j = 0; j < pieces.size(); j++) {
                assertTrue(pieces.get(j).estParcouru()); 
            }
            
           // Affichage des pairs de sommets qui forment les aretes s
            System.out.println("Pour le labyrinthe : " + i);
            for (int j = 0; j < aretes.size(); j++) {
                System.out.println(aretes.get(j));
            }
        }
    }

    /** Test de la méthode trouverVoisinsNonParcouruss */
    @Test
    @DisplayName("Test des voisins non parcourus")
    void testTrouverVoisinsNonParcourus() {
        // Sommets testés pour regarder leur nombre de voisin
        int[] SOMMET_VOISIN_OK = { 0, 3, 12, 20, 24, 34};
        // Nombre de sommet voisin par rapport au sommet courant
        int[] NOMBRE_VOISIN_OK = { 2, 3,  4,  3,  4,  2};

        for (int i = 0; i < NOMBRE_VOISIN_OK.length; i++) {
            Sommet sommet = new Sommet(SOMMET_VOISIN_OK[i]);
            List<Sommet> voisins 
                         = correctes.get(0).trouverVoisinsNonParcourus(sommet);
            
            // Vérifier le nombre de voisins non parcourus
            assertEquals(NOMBRE_VOISIN_OK[i], voisins.size()); 
            // Vérifier que les voisins ne sont pas parcourus
            for (int j = 0; j < voisins.size(); j++) {
                assertFalse(voisins.get(j).estParcouru());
            }            
        } 
    }

    @Test
    public void testTaille() {
        int[] taille = correctes.get(0).taille(35);      
        int[] taille1 = correctes.get(0).taille(4);
    }
    
    /** TODO comment method role */
    @Test
    @DisplayName("Test de la sauvegarde du labyrinthe")
    void testSauvegarder() {
        // TODO: Implémenter ce test une fois que la méthode sauvegarder() est implémentée
    }
}