/**
 * A class to run the game without graphics for now.
 * No input validation because lkasjd;goiaehrg[0wutf09wufpowrjgt
 * 
 * @author @FranklinZhu1
 */

package kalisz;

import java.util.Scanner;
import logic.Wordle;
import logic.Connections;
import logic.SpellingBee;
import logic.Player;
import logic.DictionaryChecker;

public class Testing {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Player player = new Player();
        System.out.println("Welcome to the Kalisz Times Games!\nWould you like to register an account or log in? (r/l)");
        String username, password;
        switch (sc.nextLine()) {
            case "r":
                System.out.println("Input your username:");
                username = sc.nextLine();
                System.out.println("Input your password:");
                password = sc.nextLine();
                player.saveLoginInfo(username, password);
                // break; actually nah
            case "l":
                System.out.println("Input your username and password, line-break-separated:");
                username = sc.nextLine();
                password = sc.nextLine();
                int loginCode = player.login(username, password);
                while (loginCode != 1) {
                    System.out.println(((loginCode == -1) ? "Username not registered. " : "Wrong password. ") + "Please input your credentials again (or input \"hell\" \"naw\" to exit):");
                    username = sc.nextLine();
                    password = sc.nextLine();
                    if ((username + password).equals("hellnaw")) break;
                    loginCode = player.login(username, password);
                }
                System.out.println("Login successful! (If you didn't type hell naw!)");
                break;
        }

        //connections board

        

        // wordle test
        Wordle wordle = new Wordle(new char[]{'m', 'a', 'g', 'm', 'a'});
        System.out.println("WORDLE TEST!!! input the letter that you want to type into the guess (the first letter will be taken if you type a word)");
        String inputString = sc.nextLine();
        boolean done = false;
        int guesses = 0;
        while (guesses < 6 && !done) {
            switch (inputString) {
                case "":
                    break;
                case "delete":
                    wordle.deleteLetter();
                    System.out.println("Guess is now " + wordle.getCurrentGuess());
                    break;
                case "submit":
                    ++guesses;
                    switch (wordle.submitGuess()) {
                        case -1:
                            System.out.print("Not enough letters!");
                            break;
                        case 0:
                            System.out.print("Not a word!");
                            break;
                        default:
                            for (int index = 0; index < 5; ++index) System.out.print(wordle.getGuessData()[index] + " ");
                            break;
                    }
                    System.out.println();
                    done = wordle.getWin();
                    break;
                default:
                    wordle.inputLetter(inputString.charAt(0));
                    System.out.println("Guess is now " + wordle.getCurrentGuess());
                    break;
            }
            if (!done && guesses < 6) inputString = sc.nextLine();
        }
        if (wordle.getWin()) System.out.println("You won! Congrats!");
        else System.out.println("Better luck next time!");
        System.out.println("Results:");
        for (int index = 0; index < wordle.getResults().size(); ++index) {
            for (int i = 0; i < 5; ++i) { // change 5 as needed (i hardcoded the word for now)
                System.out.print(wordle.getResults().get(index)[i] + " ");
            }
            System.out.println();
        }
        if (player.addWordleAttempt((wordle.getWin()) ? guesses : -1)) System.out.println("Score saved to profile!");
        else System.out.println("User not logged in. Score not saved.");
        switch (player.saveUserData()) {
            case -1:
                System.out.println("Uh oh! The save files are corrupted; the save did not go through.");
                break;
            case 0:
                System.out.println("Save failed since you are not logged in. :(");
                break;
            case 1:
                System.out.println("Save successful!");
        }
        
    }

}
