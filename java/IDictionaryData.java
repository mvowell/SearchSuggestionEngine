public interface IDictionaryData {
   void readBook(String file);
   boolean isWord(String word);
   String[] followingWords(String word);
}