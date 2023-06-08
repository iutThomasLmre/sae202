/*
 * Labyrinthe.java                                18 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe;

import java.util.ArrayList;
import java.util.List;

import application.pile.File;

/** 
 * TODO comment class responsibility (SRP)
 * @author thomas.lemaire
 */
public class Labyrinthe {

    /** Le nombre de pièces (sommets) que le labyrinthe (graphe) va comporter */
    private int nombreSommets;

    /** Liste des pièces (sommets) du labyrinthe */
    private List<Sommet> sommets;

    /** Liste des portes (arêtes) qui va former le lybinrinthe */
    private List<Arete> aretes;

    /** 
     * Matrice de representation des portes (arêtes)
     *  <ul><li> 0 : un mur est présent
     * </li><li> 1 : une porte est présente 
     * </li></ul>
     */
    private boolean[][] representationAretes;

    /** Longueur du labyrinthe */
    private int longueur;

    /** Hauteur du labyrinthe */
    private int hauteur;

    private int difficulte;

    private int positionJoueur = 0;

    private int positionArrivee;
    
    private boolean[] passageParcours;
    
    private int nombreCoupsJoueur = 0;
    private int nombreCoupsMachine;
    
    private boolean jeuEnCours = true;
    
    private boolean joueurGagne = false;

    /**
     * TODO comment initial state
     * @param longueur 
     * @param hauteur 
     * @param difficulte 
     */
    public Labyrinthe(int longueur, int hauteur, int difficulte) {
        if (!isValide(longueur * hauteur)) {
            throw new IllegalArgumentException();
        }

        this.nombreSommets = longueur * hauteur;
        this.aretes  = new ArrayList<Arete>();
        this.sommets = new ArrayList<Sommet>();

        this.longueur = longueur;
        this.hauteur  = hauteur;
        this.difficulte = difficulte;

        this.passageParcours = new boolean[nombreSommets];
        this.positionArrivee = nombreSommets-1;

        // Création des pièces (sommets) du labyrinthe (graphe)
        for (int i = 0; i < this.nombreSommets; i++) {
            Sommet sommet = new Sommet(i);
            this.sommets.add(sommet);
        }
    }

    /**
     * TODO comment method role
     *
     */
    public void constructionParcours() {

        File file;
        int sommetAleatoire;
        int voisinAleatoire;
        Sommet sommetInitial;

        // Choisir aléatoirement un sommet initial
        sommetAleatoire  = (int)(Math.random() * this.nombreSommets);
        sommetInitial    = sommets.get(sommetAleatoire);
        sommetInitial.marquerParcouru();

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

                // Le nouveau sommet de pile devient le sommet courant
                if (!file.estVide()) {
                    sommetCourant = (Sommet) file.getSommet();
                }
            } else {
                // Choisir aléatoirement un des voisins non parcouru
                voisinAleatoire 
                = (int)(Math.random() * voisinsNonParcourus.size());
                Sommet voisinChoisi = voisinsNonParcourus.get(voisinAleatoire);

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

        this.representationAretes = constructionRepresentation();
    }

    /**
     * TODO comment method role
     * @param sommet
     * @return 0
     */
    public List<Sommet> trouverVoisinsNonParcourus(Sommet sommet) {
        List<Sommet> voisinsNonParcourus = new ArrayList<Sommet>();

        int numeroSommet = sommet.getNumero();

        // Vérification du voisin du haut
        if (   numeroSommet - longueur >= 0
            && !sommets.get(numeroSommet - longueur).estParcouru()) {
            voisinsNonParcourus.add(sommets.get(numeroSommet - longueur));
        }

        // Vérification du voisin du bas
        if (   numeroSommet + longueur < this.nombreSommets
            && !sommets.get(numeroSommet + longueur).estParcouru()) {
            voisinsNonParcourus.add(sommets.get(numeroSommet + longueur));
        }

        // Vérification du voisin de gauche
        if (   numeroSommet % longueur - 1 >= 0
            && !sommets.get(numeroSommet - 1).estParcouru()) {
            voisinsNonParcourus.add(sommets.get(numeroSommet - 1));
        }

        // Vérification du voisin de droite
        if (   numeroSommet % longueur + 1 < longueur
            && !sommets.get(numeroSommet + 1).estParcouru()) {
            voisinsNonParcourus.add(sommets.get(numeroSommet + 1));
        }

        // Retourner la liste des voisins non parcourus
        return voisinsNonParcourus;
    }

    /**
     * TODO comment method role
     * @return
     */
    private boolean[][] constructionRepresentation() {

        boolean[][] aRetourner = new boolean[nombreSommets][nombreSommets];
        Arete areteCourante;

        for (int i = 0; i < aretes.size(); i++) {
            areteCourante = aretes.get(i);
            aRetourner[areteCourante.getSommetA().getNumero()]
                      [areteCourante.getSommetB().getNumero()] = true;
        }

        return aRetourner;
    }

    /**
     * TODO comment method role
     *
     */
    public void renitialiserParcours() {
        for (int i = 0; i < nombreSommets; i++) {
            sommets.get(i).demarquerParcouru();
        }
    }
    
    /**
     * TODO comment method role
     * @param sommet
     * @return
     */
    private List<Sommet> trouverVoisinsNonParcourusValide(Sommet sommet) {
        
        List<Sommet> voisinsNonParcourusValide = new ArrayList<Sommet>();

        int numeroSommet = sommet.getNumero();

        // Vérification du voisin du haut
        if (   numeroSommet - longueur >= 0
            && !sommets.get(numeroSommet - longueur).estParcouru()
            && (representationAretes[numeroSommet-longueur][numeroSommet]
            ||  representationAretes[numeroSommet][numeroSommet-longueur])) {
            voisinsNonParcourusValide.add(sommets.get(numeroSommet - longueur));
        }

        // Vérification du voisin du bas
        if (   numeroSommet + longueur < this.nombreSommets
            && !sommets.get(numeroSommet + longueur).estParcouru()
            && (representationAretes[numeroSommet+longueur][numeroSommet]
            ||  representationAretes[numeroSommet][numeroSommet+longueur])){
            voisinsNonParcourusValide.add(sommets.get(numeroSommet + longueur));
        }

        // Vérification du voisin de gauche
        if (   numeroSommet % longueur - 1 >= 0
            && !sommets.get(numeroSommet - 1).estParcouru()
            && (representationAretes[numeroSommet][numeroSommet-1]
            ||  representationAretes[numeroSommet-1][numeroSommet])){
            voisinsNonParcourusValide.add(sommets.get(numeroSommet - 1));
        }

        // Vérification du voisin de droite
        if (   numeroSommet % longueur + 1 < longueur
            && !sommets.get(numeroSommet + 1).estParcouru()
            && (representationAretes[numeroSommet][numeroSommet+1]
            ||  representationAretes[numeroSommet+1][numeroSommet])){
            voisinsNonParcourusValide.add(sommets.get(numeroSommet + 1));
        }
        
        return voisinsNonParcourusValide;
    }

    /**
     * @param sommetEntree 
     * @param sommetSortie 
     */
    public void parcoursProfondeur() {

        File file;
        int randomVoisin;
        Sommet sommetEntree = sommets.get(0);
        Sommet sommetSortie = sommets.get(sommets.size() - 1);

        sommetEntree.marquerParcouru();

        // Créer une pile LIFO initialement vide
        file = new File();
        // Empiler le sommet courant (initial) à traiter
        file.empiler(sommetEntree);
        // Parcours en backtrack jusqu'à ce que tous les sommets soient parcourus
        while (file.getSommet() != sommetSortie) {
            Sommet sommetCourant = (Sommet) file.getSommet();
            List<Sommet> voisinsNonParcourusValide 
                = trouverVoisinsNonParcourusValide(sommetCourant);
            if (voisinsNonParcourusValide.isEmpty()) {
                // Dépiler le sommet courant
                file.depiler();
                // Le nouveau sommet de pile devient le sommet courant (si pile vide)
                if (!file.estVide()) {
                    sommetCourant = (Sommet) file.getSommet();
                }
            } else {
                // Choisir aléatoirement un des voisins non parcouru
                randomVoisin = (int)(Math.random() * voisinsNonParcourusValide.size());
                Sommet voisinChoisi = voisinsNonParcourusValide.get(randomVoisin);
                sommetCourant = voisinChoisi;
                // Parcourir le voisin
                sommetCourant.marquerParcouru();
                // Devient le sommet courrant en étant empilé
                file.empiler(sommetCourant);
            }
        }
        
        this.nombreCoupsMachine = file.getTaille() -1;
        
        List<Sommet> parcours = new ArrayList<Sommet>();
        
        for (int i = file.getTaille(); i > 0 ;i--) {
            parcours.add((Sommet) file.getSommet());
            file.depiler();
        }
        
        for (int i = 0; i < parcours.size(); i++) {
            passageParcours[parcours.get(i).getNumero()] = true;
        }
    }

    /**
     * TODO comment method role
     * @param direction
     */
    public void deplacerJoueur(char direction) {

        switch (direction) {
        case 'N':
            if (    positionJoueur - longueur >= 0
            && (representationAretes[positionJoueur][positionJoueur-longueur]
            ||  representationAretes[positionJoueur-longueur][positionJoueur])
            && jeuEnCours) {
                positionJoueur -= longueur;
                nombreCoupsJoueur++;
            }
            break;
        case 'E':
            if (    positionJoueur + 1 < nombreSommets
                && (representationAretes[positionJoueur][positionJoueur+1]
                ||  representationAretes[positionJoueur+1][positionJoueur])
                && jeuEnCours) {
                positionJoueur++;
                nombreCoupsJoueur++;
            }
            break;
        case 'S':
            if (    positionJoueur + longueur < nombreSommets
                && (representationAretes[positionJoueur][positionJoueur+longueur]
                ||  representationAretes[positionJoueur+longueur][positionJoueur])
                && jeuEnCours) {
                positionJoueur += longueur;
                nombreCoupsJoueur++;
            }
            break;
        case 'W':
            if (    positionJoueur - 1 >= 0
                && (representationAretes[positionJoueur][positionJoueur-1]
                ||  representationAretes[positionJoueur-1][positionJoueur])
                && jeuEnCours) {
                positionJoueur--;
                nombreCoupsJoueur++;
            }
            break;
        default:
            // Touches non prises en compte
            break;
        }

        if (positionArrivee == positionJoueur) {
            joueurGagne = true;
            terminer();
        } else {
            afficher();
        }
    }

    /**
     * TODO comment method role
     *
     */
    public void terminer() {
        this.jeuEnCours = false;
        this.difficulte = 0;
        
        renitialiserParcours();
        parcoursProfondeur();
        
        afficher();
        
        if (joueurGagne) {
            System.out.print("\nVous avez obtenu un score de : " 
                             + calculScore()
                             +"\nNombre de coups optimal : " 
                             + nombreCoupsMachine
                             +", votre nombre de coups : " + nombreCoupsJoueur);
        }
    }
    
    /**
     * TODO comment method role
     * @return
     */
    private int calculScore() {
        return (nombreSommets * 1000 / (nombreCoupsJoueur * (nombreCoupsMachine + 1)) * 1000) 
                * (1 + difficulte * (nombreSommets / 2)) / 1000;
    }

    /**
     * TODO comment method role 
     */
    public void afficher() {

        int k = 0;

        System.out.printf("\nNombre de coups réalisés : "
                          + "%d \n\n", nombreCoupsJoueur);

        for (int j = 0; j < longueur; j++) {
            if (   j == 0 && difficulte == 0
                    || (j == 0 && difficulte == 1 && positionJoueur - j == 0)) {
                System.out.print("┌─▬▬");
            } else if (   difficulte == 0
                    || (difficulte == 1 && positionJoueur - j == 0)){
                System.out.print("┬─▬▬");
            } else {
                System.out.print("    ");
            }
        }

        if (    difficulte == 0
                || (difficulte == 1 && positionJoueur-longueur+1 == 0)) {
            System.out.print("┐");
        } else {
            System.out.print(" ");
        }

        System.out.print("\n│");

        for (int i = 0; i < hauteur; i++) {

            // Vérification des voisins de droite
            for (int j = 0; j < longueur; j++) {
                
                String remplirSalle = passageParcours[j+k] ? 
                                     "#" : positionJoueur == j + k ? 
                                     "o" : positionArrivee == j + k ? "X" : " ";

                if (   j+k+1 < nombreSommets
                        && (representationAretes[j+k][j+k+1]
                        ||  representationAretes[j+k+1][j+k])
                        &&  (difficulte == 0
                        ||   (difficulte == 1 && positionJoueur-j-k == 0)
                        ||   (difficulte == 1 && positionJoueur-j-k == 1))) {
                    System.out.printf(" %s  ", remplirSalle);
                } else if (   difficulte == 0
                        || (difficulte == 1 && positionJoueur-j-k == 0)
                        || (difficulte == 1 && positionJoueur-j-k == 1)) {
                    System.out.printf(" %s │", remplirSalle);
                } else {
                    System.out.print("    ");
                }
            }

            if (i < hauteur-1) {
                if ( difficulte == 0
                        || difficulte == 1 && positionJoueur-k ==0) {
                    System.out.print("\n├");
                } else {
                    System.out.print("\n ");
                }


                // Vérification des voisins du haut et du bas
                for (int j = 0; j < longueur; j++) {

                    String separation = j != longueur-1 ? "┼" : "┤";

                    if (   j+k+longueur < nombreSommets
                            && (representationAretes[j+k][j+k+longueur]
                            ||  representationAretes[j+k+longueur][j+k]
                            &&  (difficulte == 0
                            ||  (difficulte == 1 && positionJoueur-j-k == 0)
                            ||  (difficulte == 1 && positionJoueur-j-k == longueur)))){
                        System.out.printf("   %s", separation);
                    } else if (   difficulte == 0
                            || (difficulte == 1 && positionJoueur-j-k == 0)
                            || (difficulte == 1 && positionJoueur-j-k == longueur)){
                        System.out.printf("─▬▬%s", separation);
                    } else {
                        System.out.print("    ");
                    }
                }  

                k += longueur;
                System.out.print("\n│");

            } else {
                System.out.println();
            }   
        }

        for (int j = 0; j < longueur; j++) {
            if (j == 0) {
                System.out.print("└─▬▬");
            } else {
                System.out.print("┴─▬▬");
            }
        }
        System.out.print("┘\n");
    }
    
    /**
     * @param  nombreSalles , le résultat de la multiplication de la longueur et 
     *         de la hauteur du labyrinthe
     * @return true si le nombre de salles du labyrinthe est supérieur ou égal à 4,
     *         false sinon
     */
    private boolean isValide(int nombreSalles) {
        return nombreSalles >= 4;
    }
    
    /** @return La liste des aretes représentant les portes du labyrinthe */
    public List<Arete> getAretes() {
        return this.aretes;
    }

    /** @return La liste des sommets qui forment les salles du labyrinthe */
    public List<Sommet> getSommets() {
        return this.sommets;
    }

    /** @return valeur de hauteur */
    public int getHauteur() {
        return hauteur;
    }

    /** @return valeur de longueur */
    public int getLongueur() {
        return longueur;
    }
}

