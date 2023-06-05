/*
 * Sauvegarde.java                                      31 mai 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package ascendante.lab;
import ascendante.lab.Arete;
import ascendante.lab.Sommet;
import ascendante.lab.AscendanteChaine;
import java.io.FileWriter;
import java.io.IOException;
import org.json.*;

/** TODO comment class responsibility (SRP)
 * @author constant.nguyen
 *
 */
public class Sauvegarde {
    /** 
     * @param args
     * @throws JSONException 
     */
    public static void main(String args[]) throws JSONException {
        


        AscendanteChaine.créationListeMarque();

        AscendanteChaine.créationArete();
        
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("Hauteur", 5);
        jsonObject.put("Longueur", 7); 

        for (int i = 0; i < AscendanteChaine.listeArete.size(); i++) {
            jsonObject.append("arete", (AscendanteChaine.listeArete.get(i).getSommetA().getNumero() + " - " 
                            + AscendanteChaine.listeArete.get(i).getSommetB().getNumero()));        
        }
        
        try {
           FileWriter file = new FileWriter("Z://WorkspacePOO//SaeAlgo/test.json");
           file.write(jsonObject.toString());
           file.close();
        } catch (IOException e) {

           e.printStackTrace();
        }
        System.out.println("JSON file created: "+jsonObject);
     }
}
