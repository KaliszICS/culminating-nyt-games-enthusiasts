/**
 * A class with a checkWord method to check if a word is in the English dictionary.
 * There is no explicit constructor/instance variables.
 * 
 * @author @FranklinZhu1
 */

package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DictionaryChecker {

    public boolean checkWord(String word) {
         try {
            
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word.toLowerCase());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            return (con.getResponseCode() == 200);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
         
        
        // return true;
    }


}