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

import application.pile.PileContigue;

/** Construction de labyrinthe par création ascendante de chaîne
 * @author constant.nguyen
 * @author thomas.izard
 * @author thomas.lemaire
 */
public class AscendanteChaine {

    /** 
     * Matrice de representation des portes (arêtes)
     *  <ul><li> 0 : un mur est présent
     * </li><li> 1 : une porte est présente 
     * </li></ul>
     */
    private static int[][] representationAretes;


    /** Initialisation d'un tableau d'objet Sommet de taille 35 */
    private static Sommet[] ensembleSommet;


    /** Initialisation d'une liste d'objet Arete qui contient nos Aretes*/
    public static List<Arete> listeArete;

    /** Initialisation d'une liste de String permettant de stocker nos marque */
    static List<String> marque;




    private static int longueur;
    private static int coupOpti;
    private static boolean compteur = true; 

    private static int hauteur;

    private static int nombreCoups = 0;


    private static int positionJoueur = 0;

    private static int positionArrivee;

    private static boolean jeuEnCours = true;

    private boolean[] passageParcours;



    /** Le nombre de pièces (sommets) que le labyrinthe (graphe) va comporter */
    private static int nombreSommets;


    /** Initialisation de deux objet Sommet */
    static Sommet sommetA;
    static Sommet sommetB;


    /**
     * Constructeur de la classe Labyrinthe, il permet de créer un Labyrinthe
     * avec la taille de notre choix
     * @param longueur 
     * @param hauteur 
     */
    public AscendanteChaine(int longueur, int hauteur) {

        this.nombreSommets = longueur * hauteur;
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.positionArrivee = nombreSommets - 1;
        this.passageParcours = new boolean[nombreSommets];

        this.listeArete  = new ArrayList<Arete>();    

        ensembleSommet = new Sommet[nombreSommets];

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

        marque = new ArrayList<>(nombreSommets);

        for (int i = 0; i < nombreSommets - 1; i++) {
            marque.add("" + i);
        }

    }


    /** 
     *  Cette méthode permet de faire le marquage de nos deux sommet
     *  Elle vérifie plusieurs besoins en fonction du 
     *  marquage déjà présent sur nos deux sommet A et B
     * @param sommetA est le premier sommet à créer
     * @param sommetB est le deuxième sommet à créer avec sommet A

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
     * Puis effectue le marquage nécessaire de ces deux sommet
     */
    public static void créationArete() {
        listeArete = new ArrayList<Arete>();

        while (listeArete.size() < ensembleSommet.length - 1) {
            sommetA = ensembleSommet[(int) (Math.random() * nombreSommets)];
            sommetB = ensembleSommet[(int) (Math.random() * nombreSommets)];

            if (isVoisin(sommetA, sommetB)) {
                marquage(sommetA, sommetB);
            }
        }
        representationAretes = constructionRepresentation();

    }


    /**
     * TODO comment method role
     *
     */
    public void renitialiserParcours() {
        for (int i = 0; i < nombreSommets; i++) {

        }
    }

    /**
     * Permet de parcourir le labyrinthe en profondeur
     * pour trouver le chemin le plus optimal
     * @param sommetEntree 
     * @param sommetSortie 
     */
    public void parcoursProfondeur() {

        PileContigue<Sommet> pile;
        int randomVoisin;
        Sommet sommetEntree = ensembleSommet[0];
        Sommet sommetSortie = ensembleSommet[(ensembleSommet.length - 1)];

        sommetEntree.marquerParcouru();


        // Créer une pile LIFO initialement vide
        pile = new PileContigue<Sommet>();
        // Empiler le sommet courant (initial) à traiter
        pile.empiler(sommetEntree);

        // Parcours en backtrack jusqu'à ce que tous les sommets soient parcourus
        while (pile.sommet() != sommetSortie) {

            Sommet sommetCourant = (Sommet) pile.sommet();
            List<Sommet> voisinsNonParcourusValide 
            = trouverVoisinsNonParcourusValide(sommetCourant);
            if (voisinsNonParcourusValide.isEmpty()) {

                // Dépiler le sommet courant
                pile.depiler();
                // Le nouveau sommet de pile devient le sommet courant (si pile vide)
                if (!pile.isVide()) {

                    sommetCourant = (Sommet) pile.sommet();
                }
            } else {
                // Choisir aléatoirement un des voisins non parcouru
                randomVoisin = (int)(Math.random() * voisinsNonParcourusValide.size());
                Sommet voisinChoisi = voisinsNonParcourusValide.get(randomVoisin);
                sommetCourant = voisinChoisi;
                // Parcourir le voisin
                sommetCourant.marquerParcouru();
                // Devient le sommet courrant en étant empilé
                pile.empiler(sommetCourant);
            }
        }
        coupOpti = pile.getTaille() -1;

    }

    /**
     * Cherche les voisins non parcourus d'un sommet
     * @param sommet
     * @return
     */
    private List<Sommet> trouverVoisinsNonParcourusValide(Sommet sommet) {

        List<Sommet> voisinsNonParcourusValide = new ArrayList<Sommet>();

        int numeroSommet = sommet.getNumero();

        // Vérification du voisin du haut
        if (   numeroSommet - longueur >= 0
                && !ensembleSommet[(numeroSommet - longueur)].estParcouru()
                && (representationAretes[numeroSommet-longueur][numeroSommet] == 1
                ||  representationAretes[numeroSommet][numeroSommet-longueur] == 1)) {
            voisinsNonParcourusValide.add(ensembleSommet[(numeroSommet - longueur)]);
        }

        // Vérification du voisin du bas
        if (   numeroSommet + longueur < this.nombreSommets
                && !ensembleSommet[(numeroSommet + longueur)].estParcouru()
                && (representationAretes[numeroSommet+longueur][numeroSommet] == 1
                ||  representationAretes[numeroSommet][numeroSommet+longueur] == 1)){
            voisinsNonParcourusValide.add(ensembleSommet[(numeroSommet + longueur)]);
        }

        // Vérification du voisin de gauche
        if (   numeroSommet % longueur - 1 >= 0
                && !ensembleSommet[(numeroSommet - 1)].estParcouru()
                && (representationAretes[numeroSommet][numeroSommet-1] == 1
                ||  representationAretes[numeroSommet-1][numeroSommet] == 1)){
            voisinsNonParcourusValide.add(ensembleSommet[(numeroSommet - 1)]);
        }

        // Vérification du voisin de droite
        if (   numeroSommet % longueur + 1 < longueur
                && !ensembleSommet[(numeroSommet + 1)].estParcouru()
                && (representationAretes[numeroSommet][numeroSommet+1] == 1
                ||  representationAretes[numeroSommet+1][numeroSommet] == 1)){
            voisinsNonParcourusValide.add(ensembleSommet[(numeroSommet + 1)]);
        }

        return voisinsNonParcourusValide;
    }



    /**
     * TODO comment method role
     * @return
     */
    private static int[][] constructionRepresentation() {

        int[][] aRetourner = new int[nombreSommets][nombreSommets];
        Arete areteCourante;

        for (int i = 0; i < listeArete.size(); i++) {
            areteCourante = listeArete.get(i);
            aRetourner[areteCourante.getSommetA().getNumero()]
                    [areteCourante.getSommetB().getNumero()] = 1;
        }

        return aRetourner;
    }


    /** Boolean qui renvoie True si un sommet est voisin avec l'autre
     * @param sommetA est le sommet avec qui on veut chercher les voisins
     * @param sommetB est le sommet dont l'on cherche s'il est voisin avec SommetA
     * @return 
     */
    private static boolean isVoisin(Sommet sommetA, Sommet sommetB) {

        int numeroSommetA = sommetA.getNumero();
        int numeroSommetB = sommetB.getNumero();
        if (numeroSommetA - longueur >= 0 && numeroSommetB == numeroSommetA -longueur) {
            return true;
        } else if (numeroSommetA + longueur < nombreSommets && numeroSommetB == numeroSommetA + longueur) {
            return true;
        } else if (numeroSommetA % longueur - 1 >= 0 && numeroSommetB == numeroSommetA - 1) {
            return true;    
        } else if (numeroSommetA % longueur + 1 < longueur && numeroSommetB == numeroSommetA + 1) {
            return true;
        }
        return false;




    }



    /** Renvoie la liste des Aretes
     * @return la liste des aretes
     */
    public static List<Arete> getAretes() {
        return listeArete;
    }

    /** @return valeur de hauteur */
    public static int getHauteur() {
        return hauteur;
    }

    /** @return valeur de longueur */
    public int getLongueur() {
        return longueur;
    }

    /**
     * Affichage du Labyrinthe
     * @param positionJoueur position du joueur dans le labyrinthe
     */
    public static void afficher(int positionJoueur) {
        int numeroSommet = 0;
        
        System.out.println("[Q] pour quitter la partie");
        

        for (int j = 0; j < hauteur; j++) {
            if (j == 0) {
                for (int i = 0; i < longueur; i++) {
                    if (i == 0) {
                        System.out.print("┌─▬▬");
                    } else {
                        System.out.print("┬─▬▬");
                    }
                }
                System.out.print("┐\n");
            }
            int numeroSommetTemporaire = numeroSommet;
            for (int i = 0; i < longueur + 1; i++) {

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
                        if (i == longueur - 1 && j == hauteur - 1) {
                            System.out.print("│ X ");
                        } else if (   positionJoueur == numeroSommetTemporaire+1
                                && positionJoueur%longueur != 0) {
                            System.out.print("│ O ");
                        } else {
                            System.out.print("│   ");
                        }
                    } else {
                        if (i == longueur - 1 && j == hauteur - 1) {
                            System.out.print("  X ");
                        } else if (   positionJoueur == numeroSommetTemporaire+1
                                && positionJoueur%longueur != 0) {
                            System.out.print("  O ");
                        } else {
                            System.out.print("    ");
                        }
                    }

                    numeroSommetTemporaire++;
                }

            }
            System.out.print("\n");
            for (int i = 0; i < longueur; i++) {
                boolean porte = false;
                for (int k = 0; k < getAretes().size(); k++) {
                    Arete areteCourante = getAretes().get(k);
                    porte =    (areteCourante.getSommetA().getNumero() 
                            == numeroSommet
                            &&  areteCourante.getSommetB().getNumero() 
                            == numeroSommet + longueur)
                            || (areteCourante.getSommetA().getNumero() 
                                    == numeroSommet + longueur
                                    &&  areteCourante.getSommetB().getNumero()
                                    == numeroSommet)
                            ||  porte;
                }
                if (!porte) {
                    if (i == 0 && j < hauteur-1) {
                        System.out.print("├─▬▬");
                    } else if (i > 0 && j == hauteur-1) {
                        System.out.print("┴─▬▬");
                    } else if (i == 0) {
                        System.out.print("└─▬▬");
                    } else {
                        System.out.print("┼─▬▬");
                    }

                } else {
                    if (i == 0 && j < longueur-1) {
                        System.out.print("├   ");
                    }
                    else {
                        System.out.print("┼   ");
                    }
                }

                numeroSommet++;
            }
            if (j == hauteur-1) {
                System.out.print("┘\n");
            } else {
                System.out.print("┤\n");
            }          
        }
    }

    /**
     * Permet les déplacement dans le labyrinthe et gère 
     * la collision avec les murs
     * @param direction dans laquelle le joueur se déplace
     */
    public void deplacerJoueur(char direction) {

        switch (direction) {
        case 'N':
            if (    positionJoueur - longueur >= 0
            && (representationAretes[positionJoueur][positionJoueur-longueur] == 1
            ||  representationAretes[positionJoueur-longueur][positionJoueur] == 1)
            && jeuEnCours) {
                positionJoueur -= longueur;
                nombreCoups++;
            }
            break;
        case 'E':
            if (    positionJoueur + 1 < nombreSommets
                && (representationAretes[positionJoueur][positionJoueur+1] == 1
                ||  representationAretes[positionJoueur+1][positionJoueur] == 1)
                && jeuEnCours) {
                positionJoueur++;
                nombreCoups++;
            }
            break;
        case 'S':
            if (    positionJoueur + longueur < nombreSommets
                && (representationAretes[positionJoueur][positionJoueur+longueur] == 1
                ||  representationAretes[positionJoueur+longueur][positionJoueur] == 1)
                && jeuEnCours) {
                positionJoueur += longueur;
                nombreCoups++;
            }
            break;
        case 'W':
            if (    positionJoueur - 1 >= 0
                && (representationAretes[positionJoueur][positionJoueur-1] == 1
                ||  representationAretes[positionJoueur-1][positionJoueur] == 1)
                && jeuEnCours) {
                positionJoueur--;
                nombreCoups++;
            }
            break;
        default:
            // Touches non prises en compte
            break;
        }

        if (positionArrivee == positionJoueur) {
            terminer();
        } else {
            System.out.println("Nombre de coups : " + nombreCoups); 
        }
        afficher(positionJoueur);

    }




    /**
     * Il fige l'affichage du labyrinthe quand le joueur a terminé la partie
     *
     */
    public void terminer() {
        this.jeuEnCours = false;

        renitialiserParcours();
        if (compteur) {
            parcoursProfondeur();
        }
        compteur = false;
        System.out.println("Le nombre de coups optimal est : " + coupOpti);
        System.out.printf("Félicitation ! Tu as gagné en %d coups\n", nombreCoups);
    }

    /**
     * permet de laisser un espace entre les labyrinthe 
     * pour "nettoyer" la console et rendre l'affichage plus propre
     * @param ligneSautee
     */
    private static void nettoyerConsole(int ligneSautee) {
        for (int i = 0; i < ligneSautee; i++) {
            System.out.println();
        }

    }

    /**
     * Permet de construire un labyrinthe par création Ascendante de chaîne
     */
    public void constructionAscendance() {

        this.representationAretes = constructionRepresentation();

        créationListeMarque();

        créationArete();

    }

}