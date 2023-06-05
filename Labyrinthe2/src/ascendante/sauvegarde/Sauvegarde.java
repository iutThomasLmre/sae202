/*
 * Sauvegarde.java                                      31 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */

package ascendante.sauvegarde;

import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

import ascendante.lab.AscendanteChaine;

/**
 * 
 * @author thomas.izard
 * @author constant.nguyen
 */
public class Sauvegarde {

    /**
     * 
     * @param args
     * @throws JSONException 
     */
    public static void main(String args[]) throws JSONException {
        
        AscendanteChaine.créationSommet();

        AscendanteChaine.créationListeMarque();

        AscendanteChaine.créationArete();
        
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("longueur", "7");
        jsonObject.put("hauteur", "5");
        for (int i = 0; i < 34; i++) {
            jsonObject.append("arete", (AscendanteChaine.listeArete.get(i).getSommetA().getNumero() + " - " + AscendanteChaine.listeArete.get(i).getSommetB().getNumero()));
        }
        try {
            FileWriter file = new FileWriter("Z:/Workspaces/workspaceSAE2.02//Labyrinthe2/test.json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+jsonObject);
    }
}