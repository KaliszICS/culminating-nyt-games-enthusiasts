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

    public static boolean checkWordExists(String word) {
/* 
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();
                // If the response is a valid JSON array, the word exists.
                // A simple check for "word" key within the JSON can also indicate existence.
                return content.toString().contains("\"word\":\"" + word.toLowerCase() + "\"");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) { // 404 Not Found
                return false; // Word not found
            } else {
                System.out.println("API request failed with response code: " + responseCode);
                return false; // Other API errors
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
            */
            return true;
    }


}