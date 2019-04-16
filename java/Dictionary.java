import java.util.ArrayList;

public class Dictionary implements IDictionary {
   // DictData obj to use, passed through constructor
   private IDictionaryData ddata;
   // KeyDist obj to use
   private IKeyDistance kdist;
   // How many words to respond with
   final int MAXRESPONSE = 100;
   
   public Dictionary(IDictionaryData ddata, IKeyDistance kdist){
      this.ddata = ddata;
      this.kdist = kdist;
   }
   
   // Check if a given word exists in the dictionary
   @Override
   public boolean isWord(String word){
      return ddata.isWord(word);
   }
   
   // Return a list of words with swapped/changed letters
   @Override
   public String[] correctWord(String word){
      // Check that the word at least exists first, if so, just return an empty list (word is already correct)
      if(isWord(word)){
         String[] empty = {};
         return empty;
      }
      // First, get potential swapped letters
      String[] swapped = getSwapped(word);
      // Second, get any distance errors
      String[] disterror = getDistanceErrors(word);
      // Concatenate all three lists above
      // and return the output
      int length = swapped.length + disterror.length;
      int i = 0;
      String[] concat = new String[length];
      for(int j = 0; j < swapped.length; j++){
         concat[i] = swapped[j];
         i++;
      }
      for(int j = 0; j < disterror.length; j++){
         concat[i] = disterror[j];
         i++;
      }
      return concat;
   }
   
   // Check for any swapped characters (only works for one swap at a time)
   private String[] getSwapped(String word){
      ArrayList<String> output = new ArrayList<>();
      // For each character, swap it with the next, and check if that word exists
      for(int i = 0; i < word.length() - 1 && output.size() < MAXRESPONSE; i++){
         char a = word.charAt(i+1);
         char b = word.charAt(i);
         String newWord = word.substring(0,i) + String.valueOf(a) + String.valueOf(b) + word.substring(i+2);
         if(ddata.isWord(newWord)) output.add(newWord);
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
   
	private String[] getDistanceErrors(String word){
      ArrayList<String> output = new ArrayList<String>();
      // For each letter, find the closest chars using the keydist class
      // replace the given letter with the nearest key
      // check if that new word exists, and if it does, append it to the output
      for(int i = 0; i < word.length(); i++){
         char c = word.charAt(i);
         char[] closest = kdist.getClosestCharacters(c);
         for(int j = 0; j < closest.length; j++){
            char nc = closest[j];
            String newWord = word.substring(0,i) + nc + word.substring(i+1);
            if(ddata.isWord(newWord)) output.add(newWord);
         }
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
   
   // Gets words which have a given word at the beginning
   @Override
   public String[] extendWord(String word){
      // Get extensions from the ddata class
      String[] extensions = ddata.extendedWords(word);
      // If there are too many words, respond with only the first few
      if(extensions.length > MAXRESPONSE){
         String[] output = new String[MAXRESPONSE];
         for(int i = 0; i < MAXRESPONSE; i++){
            output[i] = extensions[i];
         }
         return output;
      }
      return extensions;
   }
   
   // Find all words which will commonly follow a given word
   @Override
   public String[] followWord(String word){
      // Get words from ddata class
      String[] followingWords = ddata.followingWords(word);
      String[] output;
      // Check if there are too many words
      if(followingWords.length > MAXRESPONSE){
         output = new String[MAXRESPONSE];
         for(int i = 0; i < MAXRESPONSE; i++) output[i] = followingWords[i];
         return output;
      }
      return followingWords;
   }
   
   // Remove any words which don't commonly follow the first word
   @Override
   public String[] filterWords(String first, String[] second){
      // Use the ddata class's method for this
      return ddata.filterUnknown(first,second);
   }
   
   @Override
   public int wordPopularity(String prev, String next){
      return ddata.wordPopularity(prev,next);
   }
}