/*
 * JeuLabyrinthe.java                             3 juin 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package application.jeu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

import application.labyrinthe.Labyrinthe;

/** 
 * Classe principale du jeu de labyrinthe.
 * Elle se charge de lancer l'application labyrinthe et de gérer les actions
 * du joueur.
 * @author thomas.lemaire
 */
public class JeuLabyrinthe {

    /** Création de l'application */
    private final static JFrame APPLICATION = new JFrame();

    /** Chemin d'origine de localisation des décorations */
    private final static String CHEMIN_DECORATION
                                = "src/application/jeu/decorations/";

    /** Chemin de la decoration "labyrinthe" */
    private final static String CHEMIN_DECORATION_LABYRINTHE = CHEMIN_DECORATION 
                                                             + "labyrinthe.txt";

    /** Chemin de la decoration "jouer" */
    private final static String CHEMIN_DECORATION_JOUER = CHEMIN_DECORATION 
                                                        + "jouer.txt";
    /** Chemin de la decoration "parametres" */
    private final static String CHEMIN_DECORATION_PARAMETRES = CHEMIN_DECORATION 
                                                             + "parametres.txt";
    /** Chemin de la decoration "regles" */
    private final static String CHEMIN_DECORATION_REGLES = CHEMIN_DECORATION 
                                                         + "regles.txt";

    /** Liste des décorations (titres) de cahque menu */
    private final static String[] CHEMINS_DECORATIONS = 
                                                 {CHEMIN_DECORATION_LABYRINTHE,
                                                  CHEMIN_DECORATION_JOUER,
                                                  CHEMIN_DECORATION_REGLES,
                                                  CHEMIN_DECORATION_PARAMETRES};

    /** Charactère qui représente le sous-menu ou l'option ciblé par le joueur*/
    private final static char CIBLE_CHOIX_OPTION = '►';

    /** Liste du menu et de leurs options */
    private final static String[][] CHOIX_MENU = {{"JOUER", 
                                                     "Nouvelle Partie",
                                                     "Charger une partie",
                                                     "Retour"},
                                                  {"RÈGLES",
                                                     "Retour"},
                                                  {"PARAMÈTRES",
                                                     "Difficulté : %s",
                                                     "Dimensions : %d x %d",
                                                     "Retour"},
                                                  {"QUITTER"}};
    
    /** Initialisations des touches utilisées */
    private final static int DEPLACEMENT_NORD  = KeyEvent.VK_UP;
    private final static int DEPLACEMENT_SUD   = KeyEvent.VK_DOWN;
    private final static int DEPLACEMENT_EST   = KeyEvent.VK_RIGHT;
    private final static int DEPLACEMENT_OUEST = KeyEvent.VK_LEFT;
    private final static int VALIDER    = KeyEvent.VK_ENTER;
    private final static int QUITTER    = KeyEvent.VK_Q;
    private final static int RELANCER   = KeyEvent.VK_R;
    private final static int ABANDONNER = KeyEvent.VK_A;
    private final static int FERMER = KeyEvent.VK_ESCAPE;

    /** Menu actif, sur lequel le joueur se situe */
    private static int menuActif = 0;
    
    /** Sous-menu ou option ciblé par le joueur dans le menu */
    private static int optionMenuCible = 1;
    
    /** Taille du menu pour le calcul des limites de choix */
    private static int tailleMenuActif = CHOIX_MENU.length;
    
    /** Booléen qui vérifie la présence du joueur dans le menu */
    private static boolean joueurDansLeMenu    = true;
    
    /** Booléen qui vérifie la présence du joueur dans le jeu */
    private static boolean joueurEnjeu = false;

    /** Choix de la difficulte
     *  <ul><li> 0 : labyrinthe visible pour le joueur
     * </li><li> 1 : labyrinthe invisible pour le joueur
     * </li></ul>
     */
    private static int difficulteLabyrinthe = 0;

    /** Dimesions maximale et minimale du labyrinthe */
    private final static int[] BORNES_DIMENSIONS_LABYRINTHE = {2, 30};
    
    /** Dimensions du labyrinthe choisis par le joueur */
    private static int[] dimensionsLabyrinthe = {7, 5};

    /**
     * TODO comment method role
     * @param args
     */
    public static void main(String[] args) {

        // Création de l'instance qui récupère les frappes de clavier
        APPLICATION.setVisible(true);

        // Initialisation des fonctions associées aux touches frappée
        APPLICATION.addKeyListener(new KeyAdapter() {

            int dimensionModifiee = 0;
            boolean modificationDimensions = false;

            public void keyPressed(KeyEvent e) {

                // Récupération du code de la touche frappée
                int toucheFrappee = e.getKeyCode();

                if (modificationDimensions) {
                    keyListenerDimensions(toucheFrappee);
                } else if (joueurDansLeMenu) {
                    keyListenerMenu(toucheFrappee);
                }
            }

            /** 
             * 
             * @param toucheCode
             */
            private void keyListenerMenu(int toucheCode) {
                switch (toucheCode) {

                // Choix du sous-menu supérieur
                case DEPLACEMENT_NORD:
                    if(optionMenuCible > 1) {
                        optionMenuCible--;
                        afficherMenu();
                    }
                    break;
                    
                // Choix du sous-menu inférieur
                case DEPLACEMENT_SUD:
                    if(   menuActif == 0 && optionMenuCible < tailleMenuActif
                    || menuActif != 0 && optionMenuCible < tailleMenuActif -1) {
                        optionMenuCible++;
                        afficherMenu();
                    }
                    break;
                
                // Dispertion des méthodes appellées par la touche [ENTER]
                case VALIDER:
                    if (menuActif == 0) {

                        if (optionMenuCible == tailleMenuActif) {
                            APPLICATION.dispose();
                        } else { 
                            menuActif = optionMenuCible;
                        }
                        optionMenuCible = 1;

                    } else if (optionMenuCible == tailleMenuActif - 1){
                        
                        optionMenuCible = menuActif;
                        menuActif = 0;
                        
                    } else {
                        
                        if (menuActif == 1 && optionMenuCible == 1) {
                            joueurDansLeMenu = false;
                            joueurEnjeu = true;
                            creerPartie();
                        }
                        if (menuActif == 3 && optionMenuCible == 1) {
                            setDifficulte();
                        }
                        if (menuActif == 3 && optionMenuCible == 2) {
                            modificationDimensions = true;
                        }
                        
                    }
                    if (joueurDansLeMenu) {
                        afficherMenu();
                    }
                    break;
                
                // Fermer l'application
                case FERMER:
                    APPLICATION.dispose();
                    break;
                    
                // Touches non prises en compte
                default:
                    break;
                }
            }

            /** 
             * 
             * @param toucheCode
             */
            private void keyListenerDimensions(int toucheCode) {
                switch (toucheCode) {

                // Incrementation de la valeur dimension traité en cours
                case DEPLACEMENT_NORD:
                    dimensionsLabyrinthe[dimensionModifiee]++;
                    setBornesDimension(dimensionModifiee);
                    afficherMenu();
                    break;
                    
                // Decrementation de la valeur dimension traité en cours
                case DEPLACEMENT_SUD:
                    dimensionsLabyrinthe[dimensionModifiee]--;
                    setBornesDimension(dimensionModifiee);
                    afficherMenu();
                    break;
                    
                // Modification de la longueur et hauteur dans les paramètres
                case VALIDER:
                    if(dimensionModifiee == 0) {
                        dimensionModifiee++;
                    } else {
                        dimensionModifiee = 0; 
                        modificationDimensions = false;
                    }
                    break;
                    
                // Fermer l'application
                case FERMER:
                    APPLICATION.dispose();
                    break;
                
                // Touches non prises en compte
                default:
                    break;
                }
            }
        });
        
        afficherMenu();
    }

    /**
     * TODO comment method role
     *
     */
    private static void afficherMenu() {
        nettoyerConsole(50);
        afficherDecorationMenu();
        System.out.println();

        if (menuActif == 0) {
            tailleMenuActif = CHOIX_MENU.length;

            for (int i = 0; i < tailleMenuActif; i++) {
                if (optionMenuCible -1 == i) {
                    System.out.println(" " + CIBLE_CHOIX_OPTION + " " 
                            + CHOIX_MENU[i][0]);
                } else {
                    System.out.println("   " + CHOIX_MENU[i][0]);
                }
            }
        } else if (menuActif == 3){
            tailleMenuActif = CHOIX_MENU[menuActif -1].length;

            for (int i = 1; i < tailleMenuActif; i++) {
                String choix = CHOIX_MENU[menuActif -1][i] + "\n";

                if (optionMenuCible == i) {
                    if (CHOIX_MENU[menuActif -1][i].contains("%s")) {
                        System.out.printf(" " + CIBLE_CHOIX_OPTION + " " 
                                + choix, difficulteLabyrinthe == 0 
                                ? "Facile" : "Difficile");
                    } else if (choix.contains("%d")) {
                        System.out.printf(" " + CIBLE_CHOIX_OPTION + " " 
                                + choix, dimensionsLabyrinthe[0],
                                dimensionsLabyrinthe[1]);
                    } else {
                        System.out.println(" " + CIBLE_CHOIX_OPTION + " " 
                                + choix);
                    }
                } else {
                    if (CHOIX_MENU[menuActif -1][i].contains("%s")) {
                        System.out.printf("   " + choix, difficulteLabyrinthe==0 
                                ? "Facile" : "Difficile");
                    } else if (choix.contains("%d")) {
                        System.out.printf("   " + choix,dimensionsLabyrinthe[0],
                                dimensionsLabyrinthe[1]);
                    } else {
                        System.out.println("   " + choix);
                    }
                }
            }
        } else {
            tailleMenuActif = CHOIX_MENU[menuActif -1].length;

            for (int i = 1; i < tailleMenuActif; i++) {
                if (optionMenuCible == i) {
                    System.out.println(" " + CIBLE_CHOIX_OPTION + " " 
                            + CHOIX_MENU[menuActif -1][i]);
                } else {
                    System.out.println("   " + CHOIX_MENU[menuActif -1][i]);
                }
            }
        }
    }

    /**
     * Méthode qui ouvre les différents fichiers qui contiennent les décorations
     * de titre de chaque sous-menu.
     * Et affiche la décoration du sous-menu en cours.
     */
    private static void afficherDecorationMenu() {
        try {
            // Créer une instance de File en spécifiant le chemin du fichier
            File fichier = new File(CHEMINS_DECORATIONS[menuActif]);

            // Créer une instance de Scanner pour lire le contenu du fichier
            Scanner scanner = new Scanner(fichier);

            // Lire chaque ligne du fichier et l'afficher
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine() + "\n";

                if (menuActif == 3 && ligne.contains("%d")) {
                    System.out.printf(ligne, BORNES_DIMENSIONS_LABYRINTHE[0],
                            BORNES_DIMENSIONS_LABYRINTHE[1]);
                } else {
                    System.out.print(ligne);
                }
            }

            /* Fermer le scanner une fois que vous avez terminé 
               la lecture du fichier */
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO comment method role
     * @param ligneSautee
     */
    private static void nettoyerConsole(int ligneSautee) {
        for (int i = 0; i < ligneSautee; i++) {
            System.out.println();
        }
    }

    private static void setDifficulte() {
        difficulteLabyrinthe = (difficulteLabyrinthe + 1) % 2;
    }

    /**
     * TODO comment method role
     * @param dimensionModifiee
     */
    private static void setBornesDimension(int dimensionModifiee) {
        if (dimensionsLabyrinthe[dimensionModifiee] 
                > BORNES_DIMENSIONS_LABYRINTHE[1]) {
            dimensionsLabyrinthe[dimensionModifiee] 
                    = BORNES_DIMENSIONS_LABYRINTHE[0];
        } else if (dimensionsLabyrinthe[dimensionModifiee] 
                < BORNES_DIMENSIONS_LABYRINTHE[0]) {
            dimensionsLabyrinthe[dimensionModifiee] 
                    = BORNES_DIMENSIONS_LABYRINTHE[1];
        }
    }
    
    /**
     * TODO comment method role
     * @return
     */
    private static Labyrinthe creerLabyrinthe() {
        Labyrinthe labyrinthe = new Labyrinthe(dimensionsLabyrinthe[0],
                                dimensionsLabyrinthe[1],
                                difficulteLabyrinthe);
        
        labyrinthe.constructionParcours();
        
        nettoyerConsole(50);
        labyrinthe.afficher();
        
        return labyrinthe;
    }
    
    private static Labyrinthe labyrintheEnCours;
    
    private static void afficherLabyrinthe(char deplacement) {
        nettoyerConsole(50);
        
        System.out.println("[A] Voir la solution");
        System.out.println("[S] Sauvegarder (impossible pour le moment)");
        System.out.println("[R] Relancer une partie");
        System.out.println("[Q] Quitter le jeu");
        
        labyrintheEnCours.deplacerJoueur(deplacement);
    }
    
    private static void creerPartie() {
        
        final char NORD  = 'N',
                   EST   = 'E',
                   SUD   = 'S',
                   OUEST = 'W';
                
        labyrintheEnCours = creerLabyrinthe();
        
        afficherLabyrinthe(NORD);

        APPLICATION.addKeyListener(new KeyAdapter() {
                        
            public void keyPressed(KeyEvent e) {

                int toucheCode = e.getKeyCode();

                if (joueurEnjeu) {
                    switch (toucheCode) {
                    
                    // Affichage et déplacement du joueur vers le nord
                    case DEPLACEMENT_NORD:
                        afficherLabyrinthe(NORD);
                        break;
                        
                    // Affichage et déplacement du joueur vers le sud
                    case DEPLACEMENT_SUD:
                        afficherLabyrinthe(SUD);
                        break;
                        
                    // Affichage et déplacement du joueur vers l'ouest
                    case DEPLACEMENT_OUEST:
                        afficherLabyrinthe(OUEST);
                        break;
                      
                    // Affichage et déplacement du joueur vers l'est
                    case DEPLACEMENT_EST:
                        afficherLabyrinthe(EST);
                        break;
                        
                    // Fermeture de la fenetre de jeu et retour au salon
                    case QUITTER:
                        joueurEnjeu      = false;
                        joueurDansLeMenu = true;
                        
                        menuActif       = 0;
                        optionMenuCible = 1;
                        afficherMenu();
                        break;
                        
                    case RELANCER:
                        labyrintheEnCours = creerLabyrinthe();
                        afficherLabyrinthe(NORD);
                        break;
                        
                    // Abandon du joueur et don de la solution
                    case ABANDONNER:
                        nettoyerConsole(50);
                        labyrintheEnCours.terminer();
                        break;
                        
                    // Fermeture de l'application
                    case FERMER:
                        APPLICATION.dispose();
                        break;
                        
                    // Touches non prises en compte
                    default:
                        break;
                    }
                }
            }
        });
    }
}
