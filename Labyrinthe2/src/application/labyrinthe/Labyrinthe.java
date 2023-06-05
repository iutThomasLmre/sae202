/*
 * Labyrinthe.java                                18 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.labyrinthe;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

    private int longueur;

    private int hauteur;

    /**
     * TODO comment initial state
     * @param longueur 
     * @param hauteur 
     */
    public Labyrinthe(int longueur, int hauteur) {
        if (!isValide(longueur * hauteur)) {
            throw new IllegalArgumentException();
        }

        this.nombreSommets = longueur * hauteur;
        this.aretes  = new ArrayList<Arete>();
        this.sommets = new ArrayList<Sommet>();

        this.longueur = longueur;
        this.hauteur  = hauteur;

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
    public void constructionBacktracking() {

        File file;
        int randomSommet;
        int randomVoisin;
        Sommet sommetInitial;

        // Choisir aléatoirement un sommet initial
        randomSommet  = (int)(Math.random() * this.nombreSommets);
        sommetInitial = sommets.get(randomSommet);
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
    private boolean isValide(int nombrePieces) {
        return nombrePieces > 1;
    }

    /**
     * TODO comment method role
     * @param positionJoueur 
     */
    public void afficher(int positionJoueur) {
        int numeroSommet = 0;

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

    /** @return valeur de hauteur */
    public int getHauteur() {
        return hauteur;
    }

    /** @return valeur de longueur */
    public int getLongueur() {
        return longueur;
    }

    /**
     * TODO comment method role
     * @param args
     */
    public static void main(String args[]) {
        final Labyrinthe lab = new Labyrinthe(14, 10);
        lab.constructionBacktracking();
        lab.resetParcouru();
        lab.parcoursProfondeur();

        JFrame myJFrame = new JFrame();

        myJFrame.addKeyListener(new KeyAdapter() {
            int positionJoueur = 0;
            public void keyPressed(KeyEvent e) {


                lab.afficher(positionJoueur);  // init

                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    positionJoueur -= lab.getLongueur();
                    lab.afficher(positionJoueur);
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    positionJoueur += lab.getLongueur();
                    lab.afficher(positionJoueur);
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    positionJoueur--;
                    lab.afficher(positionJoueur);
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    positionJoueur++;
                    lab.afficher(positionJoueur);
                }
            }
        });

        myJFrame.setVisible(true);
    }
    
    /**
     * 
     */
    public void resetParcouru() {
        for (int i = 0; i < nombreSommets; i++) {
            sommets.get(i).demarquerParcouru();
        }
    }

    /**
     * @param sommetEntree 
     * @param sommetSortie 
     * 
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
                sommetCourant = voisinChoisi;
                // Parcourir le voisin
                sommetCourant.marquerParcouru();
                // Devient le sommet courrant en étant empilé
                file.empiler(sommetCourant);
            }
        }
        System.out.println("Nombre de déplacements : " + file.getTaille());
        List<Sommet> parcours = new ArrayList<Sommet>();
        for (int i = file.getTaille(); i > 0 ;i--) {
            parcours.add((Sommet) file.getSommet());
            file.depiler();
        }
        for (int i = 0; i < parcours.size(); i++) {
            System.out.print(parcours.get(i).getNumero() + "|");
        }
    }
}

