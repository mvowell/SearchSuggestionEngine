import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;

public class DictionaryData implements IDictionaryData {
   private ArrayList<DictionaryEntry> entries;
   private ArrayList<String> avoidWords;

   public DictionaryData(String file, String fileAvoid){
      entries = new ArrayList<DictionaryEntry>();
      avoidWords = new ArrayList<>();
      /*try (BufferedReader reader = new BufferedReader(new FileReader(fileAvoid))){
         String line = reader.readLine();
         while(line != null){
            avoidWords.add(line.trim().toLowerCase().replaceAll("[^\\p{Alpha}]",""));
            line = reader.readLine();
         }
      } catch(IOException e){
         System.out.println("Could not load blacklist!");
      }*/
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
            String separator = "\\p{Punct}";
            String[] sentences = line.split(separator);
            for(int i = 0; i < sentences.length; i++){
               String sentence = sentences[i];
               String filtered = sentence.replaceAll("[^\\p{Alnum}\\s]","");
               String[] words = sentence.split("\\s");
               for(int j = 0; j < words.length - 1; j++){
                  if(avoidWords.contains(words[j])) continue;
                  DictionaryEntry entry = getEntry(words[j]);
                  if(!avoidWords.contains(words[j+1]))
                  entry.addValue(words[j+1]);
               }
               // Adds the last word, forgot to do this earlier
               if(words.length > 0 && !avoidWords.contains(words[words.length-1])) getEntry(words[words.length-1]);
            }
            line = reader.readLine();
         }
      } catch (IOException e){
         e.printStackTrace();
         System.exit(0);
      }
   }
   
   private DictionaryEntry getEntry(String key){
      for(DictionaryEntry de : entries){
         if(de.getKey().equals(key)){
            de.incrementUsage();
            relocateEntry(de);
            return de;
         }
      }
      DictionaryEntry newEntry = new DictionaryEntry(key);
      entries.add(newEntry);
      //relocateEntry(newEntry);
      return newEntry;
   }
   
   private DictionaryEntry getEntryNoChange(String key){
      for(DictionaryEntry de : entries){
         if(de.getKey().equals(key)){
            return de;
         }
      }
      return null;
   }
   
   private void relocateEntry(DictionaryEntry entry){
      if(!entries.contains(entry)) return;
      entries.remove(entry);
      int i = 0;
      while(entries.get(i).getUsage() > entry.getUsage()) i++;
      entries.add(i,entry);
   }
   
   private void sortAllEntries(){
      Collections.sort(entries,new Comparator<DictionaryEntry>(){
         public int compare(DictionaryEntry e1, DictionaryEntry e2){
            if(e1.getUsage() > e2.getUsage()) return -1;
            else if(e2.getUsage() > e1.getUsage()) return 1;
            return 0;
         }
         public boolean equals(Object o){
            return false;
         }
      });
   }
   
   // Checks if the word exists
   @Override
   public boolean isWord(String word){
      for(DictionaryEntry entry : entries){
         if(entry.getKey().equals(word)) return true;
      }
      return false;
   }
   
   // Return list of all commonly following words
   @Override
   public String[] followingWords(String word){
      String[] values = new String[0];
      if(isWord(word))
      values = getEntry(word).getValuesSorted();
      return values;
   }
   
   // Return list of words with word at beginning
   @Override
   public String[] extendedWords(String word){
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
   // Basically returns an intersecting set of words
   @Override
   public String[] filterUnknown(String first, String[] seconds){
      ArrayList<String> output = new ArrayList<>();
      if(!isWord(first)){
         String[] empty = new String[0];
         return empty;
      }
      String[] common = new String[0];
      if(isWord(first))
      common = getEntryNoChange(first).getValues();
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
   
   public void printData(){
      try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))){
         for(DictionaryEntry de : entries){
            //System.out.println(de.getKey());
            writer.write(de.getKey() + " " + String.valueOf(de.getUsage()) + "\n");
            String[] values = de.getValues();
            for(int i = 0; i < values.length; i++){
               //System.out.println("\t" + values[i] + " " + String.valueOf(de.getCount(values[i])));
               writer.write("\t" + values[i] + " " + String.valueOf(de.getCount(values[i])) + "\n");
            }
         }
      } catch(IOException e){
         e.printStackTrace();
      }
   }
   
   public int wordPopularity(String prev, String next){
      DictionaryEntry mainEntry = getEntryNoChange(next);
      int mainCount = (mainEntry != null ? mainEntry.getUsage() : 0);
      DictionaryEntry prevEntry = getEntryNoChange(prev);
      int prevCount = 0;
      if(prevEntry != null){
         prevCount = prevEntry.getCount(next);
      }
      // If the word follows, it weighs 10 times as heavily
      return 10 * prevCount + mainCount;
   }
}