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
         StringBuilder book = new StringBuilder();
         while(line != null){
            line = line.toLowerCase();
            book.append(line);
            
            
            line = reader.readLine();
         }
         String separator = "[^\\p{Alnum}\\s]";
         String[] sentences = book.toString().split(separator);
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
      String[] values = DictionaryEntry.getEntry(word).getValues();
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
         if(count >= 5) break;
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
}