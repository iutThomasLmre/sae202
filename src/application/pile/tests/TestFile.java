/*
 * TestFile.java                                  23 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.pile.tests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.pile.File;
import application.labyrinthe.Sommet;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 */
class TestFile {
    
    /** */
    private List<File> correctes;

    /** 
     * TODO comment method role
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        correctes = new ArrayList<File>(15);
        correctes.add(new File());
    }

    @Test
    @DisplayName("test de la méthode empiler")
    void testEmpiler() {
        correctes.get(0).empiler(new Sommet(1));
    }
    
    @Test
    @DisplayName("test de la méthode getSommet")
    void testGetSommet() {
        System.out.println(correctes.get(0).getSommet());
    }
}