public class Dictionary implements IDictionary {
   private IDictionaryData ddata;
   private IKeyDistance kdist;
   
   final int MAXRESPONSE = 5;
   
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
      String[] output = new String[MAXRESPONSE];
      while(i < MAXRESPONSE && i < length){
         if(i < swapped.length) output[i] = swapped[i];
         else if(i - swapped.length < disterror.length) output[i] = disterror[i - swapped.length];
         else output[i] = lettererror[i - swapped.length - disterror.length];
         i++;
      }
      return output;
   }
   
   private String[] getSwapped(String word){
      // TODO
   }
	private String[] getDistanceErrors(String word){
      // TODO
   }
   private String[] getLetterErrors(String word){
      // TODO
   }
   
   @Override
   public String[] extendWord(String word){
      // TODO
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