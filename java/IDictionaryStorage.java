public interface IDictionaryStorage {
   void addWord(String word);
   void addWord(String firstWord, String secondWord);
   String[] getWords(String firstWord);
   String[] allWords();
   double getPercentage(String firstWord, String secondWord);
   boolean isKnownWord(String word);
   boolean isKnownWord(String firstWord, String secondWord);
}