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
    private int nombrePieces;

    /** Liste des pièces du labyrinthe */
    private List<Sommet> pieces;

    /** Liste des murs (arêtes) qui va former le lybinrinthe */
    private List<Arete> murs;

    /** TODO comment */
    private File file;

    /**
     * TODO comment initial state
     * @param nombrePieces > 1
     */
    public Labyrinthe(int nombrePieces) {
        if (!estValide(nombrePieces)) {
            throw new IllegalArgumentException();
        }

        this.nombrePieces = nombrePieces;
        this.murs = new ArrayList<Arete>();

        // Création des pièces (sommets) du labyrinthe (graphe)
        for (int i = 0; i < nombrePieces; i++) {
            Sommet sommet = new Sommet(i);
            pieces.add(sommet);
        }
    }

    /**
     * TODO comment method role
     *
     */
    public void constructionBacktracking() {
        // Choisir aléatoirement un sommet initial
        Random randomSommet = new Random();
        Sommet sommetInitial = pieces.get(randomSommet.nextInt(pieces.size()));
        sommetInitial.marquerParcouru();
        file.empiler(sommetInitial);

        // Parcours en backtrack jusqu'à ce que tous les sommets soient parcourus
        while (!file.estVide()) {
            Sommet sommetCourant = file.getSommet();
            List<Sommet> voisinsNonParcourus 
                         = trouverVoisinsNonParcourus(sommetCourant);

            if (voisinsNonParcourus.isEmpty()) {
                file.depiler();
                if (!file.estVide()) {
                    sommetCourant = file.getSommet();
                }
            } else {
                Sommet voisinChoisi = voisinsNonParcourus.get(
                                      randomSommet.nextInt(
                                      voisinsNonParcourus.size()));
                Arete arete = new Arete(sommetCourant, voisinChoisi);
                murs.add(arete);
                voisinChoisi.marquerParcouru();
                file.empiler(voisinChoisi);
            }
        }
    }

    /**
     * TODO comment method role
     * @param sommet
     * @return 0
     */
    public List<Sommet> trouverVoisinsNonParcourus(Sommet sommet) {
        List<Sommet> voisinsNonParcourus = new ArrayList<Sommet>();

        int numeroSommet = sommet.getNumero();
        int nombreSommets = pieces.size();

        // Vérification du voisin de gauche
        if (   numeroSommet -1 >= 0
            && !pieces.get(numeroSommet + 1).estParcouru()) {
            voisinsNonParcourus.add(pieces.get(numeroSommet - 1));
        }

        // Vérification du voisin de droite
        if (   numeroSommet + 1 < nombreSommets
            && !pieces.get(numeroSommet + 1).estParcouru()) {
            voisinsNonParcourus.add(pieces.get(numeroSommet + 1));
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
    public List<Arete> getMurs() {
        return this.murs;
    }
    
    /**
     * TODO comment method role
     * @return 0
     */
    public List<Sommet> getPieces() {
        return this.pieces;
    }
    
    /**
     * TODO comment method role
     * @param nombrePiece
     * @return
     */
    private static boolean estValide(int nombrePieces) {
        return nombrePieces > 1;
    }
}
