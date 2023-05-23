/*
 * Labyrinthe.java                                18 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.pile.File;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 */
public class Labyrinthe {

    /** Le nombre de pièces (sommets) que le labyrinthe (graphe) va comporter */
    @SuppressWarnings("unused")
    private int nombreSommets;

    /** Liste des pièces (sommets) du labyrinthe */
    private List<Sommet> sommets;

    /** Liste des portes (arêtes) qui va former le lybinrinthe */
    private List<Arete> aretes;

    /**
     * TODO comment initial state
     * @param nombrePieces > 1
     */
    public Labyrinthe(int nombrePieces) {
        if (!estValide(nombrePieces)) {
            throw new IllegalArgumentException();
        }

        this.nombreSommets = nombrePieces;
        this.aretes  = new ArrayList<Arete>();
        this.sommets = new ArrayList<Sommet>();

        // Création des pièces (sommets) du labyrinthe (graphe)
        for (int i = 0; i < nombrePieces; i++) {
            Sommet sommet = new Sommet(i);
            this.sommets.add(sommet);
        }
    }

    /**
     * TODO comment method role
     *
     */
    public void constructionBacktracking() {

        File file;
        int randomSommet;
        int randomVoisin;
        Sommet sommetInitial;

        // Choisir aléatoirement un sommet initial
        randomSommet  = (int)(Math.random() * this.nombreSommets);
        sommetInitial = sommets.get(randomSommet);
        sommetInitial.marquerParcouru();

        System.out.println(sommetInitial.toString());

        // Créer une pile LIFO initialement vide
        file = new File();
        // Empiler le sommet courant (initial) à traiter
        file.empiler(sommetInitial);  

        // Parcours en backtrack jusqu'à ce que tous les sommets soient parcourus
        while (!file.estVide()) {
            Sommet sommetCourant = (Sommet) file.getSommet();
            List<Sommet> voisinsNonParcourus 
                         = trouverVoisinsNonParcourus(sommetCourant);

            if (voisinsNonParcourus.isEmpty()) {
                // Dépiler le sommet courant
                file.depiler();
                // Le nouveau sommet de pile devient le sommet courant (si pile vide)
                if (!file.estVide()) {
                    sommetCourant = (Sommet) file.getSommet();
                }
            } else {
                // Choisir aléatoirement un des voisins non parcouru
                randomVoisin = (int)(Math.random() * voisinsNonParcourus.size());
                Sommet voisinChoisi = voisinsNonParcourus.get(randomVoisin);
                // Créer l'arête sommet courant -- voisin
                Arete porte = new Arete(sommetCourant, voisinChoisi);
                // casser le mur et créer une porte
                aretes.add(porte);
                // Parcourir le voisin
                voisinChoisi.marquerParcouru();
                // Devient le sommet courrant en étant empilé
                file.empiler(voisinChoisi);
            }
        }
        System.out.println("fin backtrack");
    }

    /**
     * TODO comment method role
     * @param sommet
     * @return 0
     */
    public List<Sommet> trouverVoisinsNonParcourus(Sommet sommet) {
        List<Sommet> voisinsNonParcourus = new ArrayList<Sommet>();

        int numeroSommet = sommet.getNumero();
        System.out.println(numeroSommet);
        
        // Vérification du voisin de gauche
        if (   numeroSommet - 1 >= 0
            && !sommets.get(numeroSommet - 1).estParcouru()) {
            voisinsNonParcourus.add(sommets.get(numeroSommet - 1));
        }


        // Vérification du voisin de droite
        if (   numeroSommet + 1 < this.nombreSommets
            && !sommets.get(numeroSommet + 1).estParcouru()) {
            voisinsNonParcourus.add(sommets.get(numeroSommet + 1));
        }


        // Retourner la liste des voisins non parcourus
        return voisinsNonParcourus;
    }

    /**
     * TODO comment method role
     *
     */
    public void sauvegarder() {
        // TODO Faire le script de sauvegarde
    }

    /** TODO comment method role
     * @return 0
     */
    public List<Arete> getAretes() {
        return this.aretes;
    }

    /**
     * TODO comment method role
     * @return 0
     */
    public List<Sommet> getSommets() {
        return this.sommets;
    }

    /**
     * TODO comment method role
     * @param nombrePiece
     * @return
     */
    private static boolean estValide(int nombrePieces) {
        return nombrePieces > 1;
    }
    
    /**
     * TODO comment method role
     * @param args
     */
    public static void main(String args[]) {
        Labyrinthe lab = new Labyrinthe(10);
        lab.constructionBacktracking();
    }
}
