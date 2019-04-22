package ssengine;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class KeyDistance implements IKeyDistance {
   // List of keys
   private ArrayList<KeyPos> keyPositions;
   // Read in a file passed through constructor
   public KeyDistance(String file){
      keyPositions = new ArrayList<KeyPos>();
      try(BufferedReader r = new BufferedReader(new FileReader(file))){
         String line = r.readLine().toLowerCase();
         int linenum = 0;
         while(line != null){
            for(int i = 0; i < line.length(); i++){
               if(line.substring(i,i+1).matches("\\p{Alpha}")){
                  keyPositions.add(new KeyPos(line.charAt(i),i,linenum));
               }
            }
            line = r.readLine();
            linenum++;
         }
      } catch(Exception e){
         JOptionPane.showMessageDialog(null,"Error: Could not load keyboard file " + file + "\n" +
         "Please add a " + file + " file to the same folder as SearchSuggestionEngine.jar\nNow exiting");
         System.exit(0);
      }
   }
   
   // Return list of keys exactly one key distance away
   public char[] getClosestCharacters(char c){
      KeyPos charKP = keyPositions.get(0);
      char[] output = new char[0];
      int size = 0;
      for(KeyPos k : keyPositions){
         if(c == k.getKey()) charKP = k;
      }
      for(KeyPos k : keyPositions){
         if(k.getDistance(charKP) == 1){
            size++;
            char[] temp = new char[size];
            for(int i = 0; i < output.length; i++){
               temp[i] = output[i];
            }
            temp[size-1] = k.getKey();
            output = temp;
         }
      }
      return output;
   }
}