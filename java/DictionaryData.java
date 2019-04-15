import java.util.ArrayList;
import java.io.*;

public class DictionaryData implements IDictionaryData {
   private ArrayList<DictionaryEntry> entries;
   public DictionaryData(String file){
      entries = new ArrayList<>();
      readBook(file);
   }

   @Override
   public void readBook(String file){
      try(BufferedReader reader = new BufferedReader(new FileReader(file))){
         String line = reader.readLine();
         while(line != null){
            line = line.toLowerCase();
            String separator = "[^\\p{Alnum}\\s]";
            String[] sentences = line.split(separator);
            for(int i = 0; i < sentences.length; i++){
               String sentence = sentences[i];
               char[] filter = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};
               String filtered = "";
               for(int j = 0; j < sentence.length(); j++){
                  char c = sentence.charAt(j);
                  for(int k = 0; k < filter.length; k++){
                     if(c == filter[k]){
                        filtered += c;
                        break;
                     }  
                  }
               }
               String[] words = filtered.split("\\s");
               for(int j = 0; j < words.length - 1; j++){
                  DictionaryEntry entry = DictionaryEntry.getEntry(words[j]);
                  entry.addValue(words[j+1]);
               }
            }
            line = reader.readLine();
         }
      } catch (IOException e){
         e.printStackTrace();
      }
   }
   
   @Override
   public boolean isWord(String word){
      DictionaryEntry entry = DictionaryEntry.getEntry(word);
      if(entry.getValues().length == 0) return false;
      return true;
   }
   
   @Override
   public String[] followingWords(String word){
      String[] values = DictionaryEntry.getEntry(word).getValuesSorted();
      return values;
   }
   
   @Override
   public String[] extendedWords(String word){
      ArrayList<DictionaryEntry> entries = DictionaryEntry.getList();
      ArrayList<String> output = new ArrayList<>();
      int count = 0;
      for(DictionaryEntry entry : entries){
         if(entry.getKey().length() > word.length()
            && entry.getKey().substring(0,word.length()).equals(word)
            && entry.getValues().length > 0){
               output.add(entry.getKey());
               count++;
            }
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
   
   @Override
   public String[] filterUnknown(String first, String[] seconds){
      ArrayList<String> output = new ArrayList<>();
      if(!isWord(first)){
         String[] empty = new String[0];
         return empty;
      }
      String[] common = DictionaryEntry.getEntry(first).getValues();
      for(int i = 0; i < common.length; i++){
         String commonString = common[i];
         for(int j = 0; j < seconds.length; j++){
            if(commonString.equals(seconds[j])){
               output.add(commonString);
            }
         }
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
}