import java.util.ArrayList;

public class Dictionary implements IDictionary {
   private IDictionaryData ddata;
   private IKeyDistance kdist;
   
   final int MAXRESPONSE = 5;
   final int MAXEXTRALETTERS = 5;
   
   public Dictionary(IDictionaryData ddata, IKeyDistance kdist){
      this.ddata = ddata;
      this.kdist = kdist;
   }
   
   @Override
   public boolean isWord(String word){
      return ddata.isWord(word);
   }
   
   @Override
   public String[] correctWord(String word){
      if(isWord(word)){
         String[] empty = {};
         return empty;
      }
      // First, get potential swapped letters
      String[] swapped = getSwapped(word);
      // Second, get any distance errors
      String[] disterror = getDistanceErrors(word);
      // Lastly, get replaced letters
      String[] lettererror = getLetterErrors(word);
      // Check if the length will be greater than maxresponse
      // and return the output
      int length = swapped.length + disterror.length + lettererror.length;
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
      for(int j = 0; j < lettererror.length; j++){
         concat[i] = lettererror[j];
         i++;
      }
      return concat;
   }
   
   private String[] getSwapped(String word){
      ArrayList<String> output = new ArrayList<>();
      for(int i = 0; i < word.length() - 1; i++){
         char a = word.charAt(i+1);
         char b = word.charAt(i);
         String newWord = word.substring(0,i) + String.valueOf(a) + String.valueOf(b) + word.substring(i+2);
         if(ddata.isWord(newWord)) output.add(newWord);
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
   
   // Returns nothing with the dummykeydistance class
	private String[] getDistanceErrors(String word){
      ArrayList<String> output = new ArrayList<String>();
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
   
   // Not sure why this is here
   private String[] getLetterErrors(String word){
      String[] empty = new String[0];
      return empty;
   }
   
   @Override
   public String[] extendWord(String word){
      String[] extensions = ddata.extendedWords(word);
      return extensions;
   }
   
   @Override
   public String[] followWord(String word){
      String[] followingWords = ddata.followingWords(word);
      String[] output;
      if(followingWords.length > MAXRESPONSE){
         output = new String[MAXRESPONSE];
         for(int i = 0; i < MAXRESPONSE; i++) output[i] = followingWords[i];
         return output;
      }
      return followingWords;
   }
}