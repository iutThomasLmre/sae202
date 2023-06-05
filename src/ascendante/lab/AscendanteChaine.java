/*
 * AscendanteChaine.java                                      16 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.lab;

import ascendante.lab.Sommet;
import ascendante.lab.Arete;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import application.labyrinthe.Labyrinthe;

/** Construction de labyrinthe par création ascendante de chaine
 * @author constant.nguyen
 * @author thomas.izard
 */
public class AscendanteChaine {


    /** Initialisation d'un tableau d'objet Sommet de taille 35 */
    private static Sommet[] ensembleSommet = new Sommet[35];


    /** Initialisation d'une liste d'objet Arete qui contient nos Aretes*/
    public static List<Arete> listeArete;

    /** Initialisation d'une liste de String permettant de stocker nos marque */
    static List<String> marque;


    private int longueur;

    private int hauteur;
    
    /** Le nombre de pièces (sommets) que le labyrinthe (graphe) va comporter */
    private int nombreSommets;

    
    /** Initialisation de deux objet Sommet */
    static Sommet sommetA;
    static Sommet sommetB;
    
    
    /**
     * Contructeur de la classe Labyrinthe, il permet de créer un Labyrinthe
     * avec la taille de notre choix
     * @param longueur 
     * @param hauteur 
     */
    public AscendanteChaine() {

        this.nombreSommets = 35;


        // Création des pièces (sommets) du labyrinthe (graphe)
        for (int i = 0; i < ensembleSommet.length; i++) {
            Sommet sommet = new Sommet(i, "");
            ensembleSommet[i] = sommet;
        }
    }



    /**
     *  Crée une Liste de marque utilisable
     */
    public static void créationListeMarque() {


        marque = new ArrayList<>(35);

        for (int i = 0; i < 35; i++) {
            marque.add("" + i);
        }

    }


    /** 
     *  Cette méthode permet de faire le marquage de nos deux sommet
     *  Elle vérifie plusieurs besoins en fonction du 
     *  marquage déja présent sur nos deux sommet A et B
     * @param sommetA est le premier sommet à créer
     * @param sommetB est le deuxieme sommet à créer avec sommet A

     */
    public static void marquage(Sommet sommetA, Sommet sommetB) {

        if (sommetA.getMarque() != sommetB.getMarque() 
                || (!sommetA.estMarque() && !sommetB.estMarque()) 
                && (sommetA.getNumero() != sommetB.getNumero())) {



            if (!sommetA.estMarque() && !sommetB.estMarque()) {

                sommetA.marquage(marque.get(0));
                sommetB.marquage(sommetA.getMarque());
                marque.remove(0);

            } else if (sommetA.estMarque() && !sommetB.estMarque()) {

                sommetB.marquage(sommetA.getMarque());

            } else if (sommetB.estMarque() && !sommetA.estMarque()) {

                sommetA.marquage(sommetB.getMarque());

            } else if(sommetA.getMarque() != sommetB.getMarque()) {


                for (int i = 0; i < ensembleSommet.length; i++) {
                    if (ensembleSommet[i].getMarque() == sommetB.getMarque() && ensembleSommet[i].getNumero() != sommetB.getNumero()) {
                        ensembleSommet[i].marquage(sommetA.getMarque());
                    }
                }

                sommetB.marquage(sommetA.getMarque());

            }

            listeArete.add(new Arete(sommetA, sommetB));
        }
    }


    /** 
     * Permet de créer des Arete avec une génération de deux sommet
     * présent dans notre tableau de sommet créé auparavant.
     * Puis effectue le marquage necessaire de ces deux sommet
     */
    public static void créationArete() {
        
        listeArete  = new ArrayList<Arete>();
        
        final AscendanteChaine lab = new AscendanteChaine();

        
        while (listeArete.size() < ensembleSommet.length - 1) {

            sommetA = ensembleSommet[(int) (Math.random() * 35)];
            sommetB = ensembleSommet[(int) (Math.random() * 35)];

            marquage(sommetA, sommetB);     

        }

    }
    
    /** Renvoie la liste des Aretes
     * @return la liste des aretes
     */
    public static List<Arete> getAretes() {
        return listeArete;
    }

  
    /**
     * Affichage du Labyrinthe
     * @param positionJoueur 
     */
    public static void afficher(int positionJoueur) {
        int numeroSommet = 0;

        for (int j = 0; j < 5; j++) {
            if (j == 0) {
                for (int i = 0; i < 7; i++) {
                    if (i == 0) {
                        System.out.print("┌─▬▬");
                    } else {
                        System.out.print("┬─▬▬");
                    }
                }
                System.out.print("┐\n");
            }
            int numeroSommetTemporaire = numeroSommet;
            for (int i = 0; i <7 + 1; i++) {
                if (i == 0) {
                    if (positionJoueur == numeroSommetTemporaire) {
                        System.out.print("│ O ");
                    } else {
                        System.out.print("│   ");
                    }
                } else {
                    boolean porte = false;
                    for (int k = 0; k < getAretes().size(); k++) {
                        Arete areteCourante = getAretes().get(k);
                        porte =    (areteCourante.getSommetA().getNumero() 
                                == numeroSommetTemporaire
                                &&  areteCourante.getSommetB().getNumero()
                                == numeroSommetTemporaire + 1)
                                || (areteCourante.getSommetA().getNumero()
                                        == numeroSommetTemporaire + 1
                                        &&  areteCourante.getSommetB().getNumero()
                                        == numeroSommetTemporaire)
                                ||  porte;
                    }
                    if (!porte) {
                        if (i == 7 - 1 && j == 5 - 1) {
                            System.out.print("│ X ");
                        } else if (   positionJoueur == numeroSommetTemporaire+1
                                && positionJoueur%7 != 0) {
                            System.out.print("│ O ");
                        } else {
                            System.out.print("│   ");
                        }
                    } else {
                        if (i == 7 - 1 && j == 5 - 1) {
                            System.out.print("  X ");
                        } else if (   positionJoueur == numeroSommetTemporaire+1
                                && positionJoueur%7 != 0) {
                            System.out.print("  O ");
                        } else {
                            System.out.print("    ");
                        }
                    }

                    numeroSommetTemporaire++;
                }

            }
            System.out.print("\n");
            for (int i = 0; i < 7; i++) {
                boolean porte = false;
                for (int k = 0; k < getAretes().size(); k++) {
                    Arete areteCourante = getAretes().get(k);
                    porte =    (areteCourante.getSommetA().getNumero() 
                            == numeroSommet
                            &&  areteCourante.getSommetB().getNumero() 
                            == numeroSommet + 7)
                            || (areteCourante.getSommetA().getNumero() 
                                    == numeroSommet + 7
                                    &&  areteCourante.getSommetB().getNumero()
                                    == numeroSommet)
                            ||  porte;
                }
                if (!porte) {
                    if (i == 0 && j < 5-1) {
                        System.out.print("├─▬▬");
                    } else if (i > 0 && j == 5-1) {
                        System.out.print("┴─▬▬");
                    } else if (i == 0) {
                        System.out.print("└─▬▬");
                    } else {
                        System.out.print("┼─▬▬");
                    }

                } else {
                    if (i == 0 && j < 7-1) {
                        System.out.print("├   ");
                    }
                    else {
                        System.out.print("┼   ");
                    }
                }

                numeroSommet++;
            }
            if (j == 5-1) {
                System.out.print("┘\n");
            } else {
                System.out.print("┤\n");
            }          
        }
    }

    
    
    /**
     * Affiche les méthode de création de nos sommet, marque et Arete
     * @param args non utilisés
     */
    public static void main(String[] args) {
        
        
        

        créationListeMarque();
        
        créationArete();
        
        afficher(0);

        for (int i = 0; i < listeArete.size(); i++) {
            System.out.println(listeArete.get(i).getSommetA().getNumero() + " | " + listeArete.get(i).getSommetB().getNumero());
        }
        
    }


}
