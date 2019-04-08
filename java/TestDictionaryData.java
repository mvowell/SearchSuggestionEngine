import java.util.Scanner;
import java.util.Arrays;

public class TestDictionaryData {
   public static void main(String[] args){
      DictionaryData dd = new DictionaryData("/home/matthew/SearchSuggestionEngine/bible-kjv.txt");
      DummyKeyDistance dkd = new DummyKeyDistance();
      Dictionary d = new Dictionary(dd,dkd);
      Scanner input = new Scanner(System.in);
      String inLine = "";
      while(inLine != null && !inLine.equals("quit")){
         inLine = input.nextLine();
         boolean isWord = d.isWord(inLine);
         String[] corrections = d.correctWord(inLine);
         String[] extensions = d.extendWord(inLine);
         String[] following = d.followWord(inLine);
         System.out.println(inLine + " is" + (isWord ? "" : " not") + " a word");
         System.out.println("Corrections:" + Arrays.toString(corrections));
         System.out.println("Extensions:" + Arrays.toString(extensions));
         System.out.println("Following:" + Arrays.toString(following));
         
      }
   }
}