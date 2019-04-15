import java.util.ArrayList;
import java.io.*;

public class DictionaryData implements IDictionaryData {
   public DictionaryData(String file){
      readBook(file);
   }

   // Opens file and imports any text, all non-alphabetic or numeric characters are removed
   @Override
   public void readBook(String file){
      try(BufferedReader reader = new BufferedReader(new FileReader(file))){
         String line = reader.readLine();
         // For every line...
         while(line != null){
            // Convert to lowercase
            // Split the line into sentences with .?!; characters
            // Filter out any characters not acceptable (non alphanumeric or space)
            // Split the sentence into words based on whitespace
            // Append each word to the list of entries, and add each following word
            line = line.toLowerCase();
            String separator = "[^\\p{Alnum}\\s]";
            String[] sentences = line.split(separator);
            for(int i = 0; i < sentences.length; i++){
               String sentence = sentences[i];
               char[] filter = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0',' '};
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
               // Adds the last word, forgot to do this earlier
               if(words.length > 0) DictionaryEntry.getEntry(words[words.length-1]);
            }
            line = reader.readLine();
         }
      } catch (IOException e){
         e.printStackTrace();
      }
   }
   
   // Checks if the word exists
   @Override
   public boolean isWord(String word){
      return DictionaryEntry.exists(word);
   }
   
   // Return list of all commonly following words
   @Override
   public String[] followingWords(String word){
      String[] values = new String[0];
      if(DictionaryEntry.exists(word))
      values = DictionaryEntry.getEntry(word).getValuesSorted();
      return values;
   }
   
   // Return list of words with word at beginning
   @Override
   public String[] extendedWords(String word){
      ArrayList<DictionaryEntry> entries = DictionaryEntry.getList();
      ArrayList<String> output = new ArrayList<>();
      int count = 0;
      for(DictionaryEntry entry : entries){
         if(entry.getKey().length() > word.length()
            && entry.getKey().substring(0,word.length()).equals(word)){
               output.add(entry.getKey());
               count++;
            }
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
   
   // Remove any words that do not follow 'first' from second
   @Override
   public String[] filterUnknown(String first, String[] seconds){
      ArrayList<String> output = new ArrayList<>();
      if(!isWord(first)){
         String[] empty = new String[0];
         return empty;
      }
      String[] common = new String[0];
      if(DictionaryEntry.exists(first))
      common = DictionaryEntry.getEntry(first).getValues();
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