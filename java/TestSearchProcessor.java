import java.util.Scanner;

public class TestSearchProcessor {
   public static void main(String[] args){
      DictionaryData dd = new DictionaryData("/home/matthew/SearchSuggestionEngine/bible-kjv.txt");
      KeyDistance kd = new KeyDistance("/home/matthew/SearchSuggestionEngine/keyboard.txt");
      Dictionary d = new Dictionary(dd,kd);
      SearchProcessor sp = new SearchProcessor(d);
      Scanner input = new Scanner(System.in);
      String inLine = "";
      while(inLine != null && !inLine.equals("quit")){
         inLine = input.nextLine();
         String[] output = sp.receiveSearchString(inLine);
         for(int i = 0; i < output.length; i++){
            System.out.println(output[i]);
         }
      }
   }
}