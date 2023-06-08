/*
 * TestPileContigue.java                                      23 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package application.pile.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import application.pile.PileContigue;
import application.pile.PileVideException;

/** 
 * Tests unitaires de iut.info1.sdd.PileContigue
 * @author info1 2022-2023
 *
 */
class TestPileContigue {
    
    /* fixture de test */
    
    private PileContigue<Number> lifoVide = new PileContigue();
    
    private PileContigue<Integer> pileEntier;

    private PileContigue<String> pileTexte;
    
    private PileContigue<Number> pileGrande;
    
    /** 
     * Piles régénérées avant chaque test (jeu de test stable)
     * @throws java.lang.Exception
     */
    @BeforeEach
    void InitialiserPiles() {
        
        pileEntier = new PileContigue();
        
        pileTexte = new PileContigue();
        
        pileGrande = new PileContigue();
        

        pileEntier.empiler(Integer.valueOf(-3));
        
        pileTexte.empiler("un");
        pileTexte.empiler("deux");
        pileTexte.empiler("trois");
        
        for (int valeur = 1 ; valeur <= 100000 ; valeur++) {
            pileGrande.empiler(valeur);
        }
    }

    /**
     * Test method for {@link iut.info1.sdd.PileContigue#PileContigue()}.
     */
    @Test
    void testPileContigue() {
        assertTrue(new PileContigue().isVide());
    }

    /**
     * Test method for {@link iut.info1.sdd.PileContigue#isVide()}.
     */
    @Test
    void testIsVide() {
        assertTrue(lifoVide.isVide());
        assertFalse(pileEntier.isVide());
    }

    /**
     * Test method for
     * {@link iut.info1.sdd.PileContigue#empiler(java.lang.Object)}.
     */
    @Test
    void testEmpiler() {
        assertThrows(NullPointerException.class, 
                     new Executable() {
    /* non javadoc - @see org.junit.jupiter.api.function.Executable#execute() */
                        public void execute() throws Throwable {
                            pileEntier.empiler(null);
                        }
                    });
        
        assertDoesNotThrow(new Executable() {
    /* non javadoc - @see org.junit.jupiter.api.function.Executable#execute() */
            public void execute() throws Throwable {
                pileEntier.empiler(Integer.valueOf(0));
            }
        });
        
        final PileContigue grandePile = new PileContigue();
        for (int valeur = 1 ; valeur <= 100000 ; valeur++) {
            final Integer objet = Integer.valueOf(valeur);
            assertDoesNotThrow(new Executable() {
    /* non javadoc - @see org.junit.jupiter.api.function.Executable#execute() */
                public void execute() throws Throwable {
                    grandePile.empiler(objet);
                }
            });
        }
    }

    /**
     * Test method for {@link iut.info1.sdd.PileContigue#depiler()}.
     */
    @Test
    void testDepiler() {
        assertThrows(PileVideException.class, 
                     new Executable() {
    /* non javadoc - @see org.junit.jupiter.api.function.Executable#execute() */
                        public void execute() throws Throwable {
                            lifoVide.depiler();
                        }
                    });
        
        /* vérification de la loi LIFO sur grande pile */
        for (int valeur = 100000 ; valeur >= 1 ; valeur--) {
            assertEquals(Integer.valueOf(valeur), pileGrande.sommet());
            pileGrande.depiler();
        }
        assertTrue(pileGrande.isVide());
    }

    /**
     * Test method for {@link iut.info1.sdd.PileContigue#sommet()}.
     */
    @Test
    void testSommet() {
        assertThrows(PileVideException.class, 
                     new Executable() {
    /* non javadoc - @see org.junit.jupiter.api.function.Executable#execute() */
                        public void execute() throws Throwable {
                            lifoVide.sommet();
                        }
                    });
        
        assertEquals(Integer.valueOf(-3), pileEntier.sommet());
        assertEquals("trois", pileTexte.sommet());
    }

}
