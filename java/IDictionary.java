public interface IDictionary {
   boolean isWord(String word);
   String[] correctWord(String word);
   String[] extendWord(String word);
   String[] followWord(String word);
}