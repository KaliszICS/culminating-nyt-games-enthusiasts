/**
 * Utility class for checking if a word exists in the English dictionary using an online API.
 * 
 * This class provides a method to verify if a given word is valid by querying the 
 * Dictionary API at dictionaryapi.dev.
 * 
 * No instance variables or explicit constructor are defined.
 * 
 * @author FranklinZhu1
 */
package logic;

import java.net.HttpURLConnection;
import java.net.URL;

public class DictionaryChecker {

    /**
     * Checks if the specified word exists in the English dictionary.
     * 
     * Sends a GET request to the Dictionary API and returns true if the response code is 200,
     * indicating the word was found.
     * 
     * @param word the word to check (case-insensitive)
     * @return true if the word is found in the dictionary, false otherwise or if an error occurs
     */
    public boolean checkWord(String word) {
        try {
            System.out.println("checking" + word);
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word.toLowerCase());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestMethod("GET");
            return (con.getResponseCode() == 200);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
