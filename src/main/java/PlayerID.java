import java.util.Scanner;
import java.io.*;
import java.util.HashMap;

//private int score;

//HashMap<String> map;

public class PlayerID{
    private String name;
    public void getName(String name){
        HashMap<String, String> map=new HashMap<>();
        Scanner scan=new Scanner(System.in);
        System.out.println("enter username and password");
        String save= scan.nextLine();

        map.put(save, "");


        //BufferedWriter bw=null;
        
        //try{
            //bw=new BufferedWriter(new FileWriter(save,true));
            //bw.write(save);
        //}
        //catch(IOException e){
            //System.out.println(e);
        //}

        //finally{
            //try{
                //if(bw!=null){
                    //bw.close();
                //}  
            //}
            //catch (IOException e){
                //System.out.println(e);
            //}
        //}

    }

    //public void getScore(int score){

    //}
}
