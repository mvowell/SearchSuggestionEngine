package ssengine;

public interface IDictionaryData {
   // Reads a txt file and appends the results to the dictionary
   void readBook(String file);
   // Checks if a word exists
   boolean isWord(String word);
   // Returns a list of known following words
   String[] followingWords(String word);
   // Returns a list of known words which contains 'word' at the beginning
   String[] extendedWords(String word);
   // Returns a list of words from 'seconds' which are known to follow 'first'
   String[] filterUnknown(String first, String[] seconds);
   //
   int wordPopularity(String prev, String next);
}