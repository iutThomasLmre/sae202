/*
 * TestLabyrinthe.java                            19 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import application.labyrinthe.*;

/**
 * TODO comment class responsibility (SRP)
 * @author thoma
 *
 */
public class TestLabyrinthe {

    private Labyrinthe labyrinthe;

    /** 
     * TODO comment method role
     * 
     */
    @Before
    public void setUp() {
        // Créer un labyrinthe avec 10 pièces
        labyrinthe = new Labyrinthe(10); 
    }

    /** 
     * TODO comment method role
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreationLabyrintheInvalide() {
        // Tentative de création d'un labyrinthe invalide
        Labyrinthe labyrintheInvalide = new Labyrinthe(1); 
    }

    /** 
     * TODO comment method role
     * 
     */
    @Test
    public void testConstructionBacktracking() {
        // Construire le labyrinthe en utilisant l'algorithme de backtracking
        labyrinthe.constructionBacktracking(); 

        List<Arete> aretes = labyrinthe.getMurs();
        System.out.println(aretes + "dz");
        // Vérifier que la liste des arêtes n'est pas vide
        assertFalse(aretes.isEmpty()); 

        List<Sommet> pieces = labyrinthe.getPieces();
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
    public void testTrouverVoisinsNonParcourus() {
        Sommet sommet = new Sommet(0);
        List<Sommet> voisins = labyrinthe.trouverVoisinsNonParcourus(sommet);

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
    public void testSauvegarder() {
        // TODO: Implémenter ce test une fois que la méthode sauvegarder() est implémentée
    }
}