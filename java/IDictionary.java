public interface IDictionary {
   // Check if a word exists in the dictionary
   boolean isWord(String word);
   // Returns words that are corrections to a word
   String[] correctWord(String word);
   // Returns words which contain a given word at the beginning
   String[] extendWord(String word);
   // Returns all words that follow a word
   String[] followWord(String word);
   // Filters out any results which do not follow a given word
   String[] filterWords(String first, String[] second);
}