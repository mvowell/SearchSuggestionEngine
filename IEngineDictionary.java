public interface IEngineDictionary {
   // Max characters for input and response
   int MAXLIMIT = 20;
   // Min characters for input and response
   int MINLIMIT = 2;
   // Max number of words that each function can return
   int MAXRESPONSE = 5;
   // Threshold for likelihood a word will follow
   double PERCENTPROBABLE = 0.01;
   
   
   // Following 3 functions respond only words which follow the PERCENTPROBABLE percentage
   //  meaning that any word that is below the percentage isn't included
   // Gets corrections for a word, or returns a list with the input if the word is already correct
   String[] corrections(String thisWord,String previousWord);
   // Gets list of words which are longer versions of the current word
   String[] continuations(String thisWord, String previousWord);
   // Gets words which commonly follow the input word
   String[] followingWords(String thisWord);
   
   // Utility functions
   boolean isKnownWord(String input);
   IDictionaryStorage getStorage();
   void setStorage(IDictionaryStorage storage);
   void readFile(String url);
   void resetStorage();
}