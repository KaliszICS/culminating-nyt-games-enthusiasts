import java.util.Scanner;

public class menu {
    //Start button
    public static void main(String[]args){

        String w=("w");
        String c=("c");
        String s=("s");
        Scanner startw=new Scanner(System.in);
        System.out.println("enter w for wordle");
        System.out.println("enter c for connections");
        System.out.println("enter s for spellingbee");
        String begin= startw.nextLine();
        if(begin==w){
            //connect to wordle
        }
        else if(begin==s){
            //connect to spellingbee
        }
        else if(begin==c){
            //connect to connections
        }


        else{
            System.out.println("");
        }

        startw.close();


    }    
    
}
