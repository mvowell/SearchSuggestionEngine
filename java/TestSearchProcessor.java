import java.util.Scanner;

public class TestSearchProcessor {
   public static void main(String[] args){
      DictionaryData dd = new DictionaryData("/home/matthew/SearchSuggestionEngine/bible-kjv.txt");
      DummyKeyDistance dkd = new DummyKeyDistance();
      Dictionary d = new Dictionary(dd,dkd);
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