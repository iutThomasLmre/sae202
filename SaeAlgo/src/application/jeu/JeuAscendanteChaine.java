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

import ascendante.lab.AscendanteChaine;

/** 
 * 
 * @author thomas.lemaire
 * @author constant.nguyen
 * @author thomas.izard
 */
public class JeuAscendanteChaine {

    final static JFrame application = new JFrame();

    final static String CHEMIN_DECORATION = "src/application/jeu/decorations/";

    final static String CHEMIN_DECORATION_LABYRINTHE = CHEMIN_DECORATION 
            + "labyrinthe.txt";

    final static String CHEMIN_DECORATION_JOUER      = CHEMIN_DECORATION 
            + "jouer.txt";

    final static String CHEMIN_DECORATION_PARAMETRES = CHEMIN_DECORATION 
            + "parametres.txt";

    final static String CHEMIN_DECORATION_REGLES     = CHEMIN_DECORATION 
            + "regles.txt";

    final static String[] CHEMINS_DECORATIONS = { CHEMIN_DECORATION_LABYRINTHE,
            CHEMIN_DECORATION_JOUER,
            CHEMIN_DECORATION_REGLES,
            CHEMIN_DECORATION_PARAMETRES};

    final static char CIBLE_CHOIX_OPTION = '►';

    final static String[][] CHOIX_MENU = {{"JOUER", 
        "Nouvelle Partie",
        "Charger une partie",
    "Retour"},

            {"RÈGLES",
        "Retour"   
            },

            {"PARAMÈTRES",
                "Difficulté : %s",
                "Dimensions : %d x %d",
            "Retour"},

            {"QUITTER"}};

    static int menuActif       = 0;
    static int optionMenuCible = 1;
    static int tailleMenuActif = CHOIX_MENU.length;

    static boolean applicationEnMarche = true;
    static boolean joueurDansLeMenu = true;
    static boolean joueurEnjeu = false;

    /** 0 = construction profondeur : 1 = construction backtracking */
    static int difficulteLabyrinthe   = 0;

    /** Diemension du labyrinthe */
    final static int[] BORNES_DIMENSIONS_LABYRINTHE = {2, 30};
    static int[] dimensionsLabyrinthe = {7, 5};

    /**
     * Lance le programme
     * @param args
     */
    public static void main(String[] args) {

        application.setVisible(true);

        application.addKeyListener(new KeyAdapter() {

            int dimensionModifiee = 0;
            boolean modificationDimensions = false;

            public void keyPressed(KeyEvent e) {

                int toucheCode = e.getKeyCode();

                if (modificationDimensions) {
                    keyListenerDimensions(toucheCode);

                } else if (joueurDansLeMenu) {
                    keyListenerMenu(toucheCode);
                }
            }

            /** 
             * Permet de se déplacer dans le menu
             * @param toucheCode
             */
            private void keyListenerMenu(int toucheCode) {
                switch (toucheCode) {

                case KeyEvent.VK_UP:
                    if(optionMenuCible > 1) {
                        optionMenuCible--;
                        afficherMenu();
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(   menuActif == 0 && optionMenuCible < tailleMenuActif
                    || menuActif != 0 && optionMenuCible < tailleMenuActif -1) {
                        optionMenuCible++;
                        afficherMenu();
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (menuActif == 0) {

                        if (optionMenuCible == tailleMenuActif) {
                            applicationEnMarche = false;
                            application.setVisible(false);
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

                        }
                        if (menuActif == 3 && optionMenuCible == 2) {
                            modificationDimensions = true;
                        }
                    }
                    if (joueurDansLeMenu) {
                        afficherMenu();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    application.setVisible(false);
                    break;
                default:
                    // Touche pas prise en compte
                    break;
                }
            }

            /** Gère les déplacement pour ne pas dépasser 
             * des dimensions quand on se déplace
             * @param toucheCode
             */
            private void keyListenerDimensions(int toucheCode) {
                switch (toucheCode) {

                case KeyEvent.VK_UP:
                    dimensionsLabyrinthe[dimensionModifiee]++;
                    setBornesDimension(dimensionModifiee);
                    afficherMenu();
                    break;
                case KeyEvent.VK_DOWN:
                    dimensionsLabyrinthe[dimensionModifiee]--;
                    setBornesDimension(dimensionModifiee);
                    afficherMenu();
                    break;
                case KeyEvent.VK_ENTER:
                    if(dimensionModifiee == 0) {
                        dimensionModifiee++;
                    } else {
                        dimensionModifiee = 0; 
                        modificationDimensions = false;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:

                    application.setVisible(false);
                    break;
                default:
                    // Touches non prises en compte
                    break;
                }
            }
        });
        afficherMenu();
    }

    /**
     * Affiche le menu de notre jeu
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
                        System.out.printf("   " + choix, difficulteLabyrinthe == 0 
                                ? "Facile" : "Difficile");
                    } else if (choix.contains("%d")) {
                        System.out.printf("   " + choix, dimensionsLabyrinthe[0],
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
     * Affichage des titres dans le menu
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

            // Fermer le scanner une fois que vous avez terminé la lecture du fichier
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de laisser un espace qui "nettoie" la console pour un affichage 
     * plus beau
     * @param le nombre de ligne à sauter
     */
    private static void nettoyerConsole(int ligneSautee) {
        for (int i = 0; i < ligneSautee; i++) {
            System.out.println();
        }
    }


    /**
     * Permet de ne pas depasser des bornes
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

    /*
     * Crée une partie de Labyrinthe
     */
    private static void creerPartie() {

        final char NORD  = 'N',
                EST   = 'E',
                SUD   = 'S',
                OUEST = 'W';

        final AscendanteChaine lab = new AscendanteChaine(dimensionsLabyrinthe[0],
                dimensionsLabyrinthe[1]);


        lab.constructionAscendance();
        nettoyerConsole(50);
        lab.afficher(0);

        application.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                int toucheCode = e.getKeyCode();

                if (joueurEnjeu) {
                    switch (toucheCode) {
                    case KeyEvent.VK_UP:
                        nettoyerConsole(50);
                        lab.deplacerJoueur(NORD);
                        break;
                    case KeyEvent.VK_DOWN:
                        nettoyerConsole(50);
                        lab.deplacerJoueur(SUD);
                        break;
                    case KeyEvent.VK_LEFT:
                        nettoyerConsole(50);
                        lab.deplacerJoueur(OUEST);
                        break;
                    case KeyEvent.VK_RIGHT:
                        nettoyerConsole(50);
                        lab.deplacerJoueur(EST);
                        break;
                    case KeyEvent.VK_Q:
                        joueurEnjeu = false;
                        joueurDansLeMenu = true;
                        menuActif = 0;
                        optionMenuCible = 1;
                        afficherMenu();
                        break;
                    case KeyEvent.VK_S:
                        joueurEnjeu = false;
                        nettoyerConsole(50);
                        lab.terminer();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        application.setVisible(false);
                        break;
                    default:
                        // Touches non prises en compte
                        break;
                    }
                }
            }
        });


    }
}
