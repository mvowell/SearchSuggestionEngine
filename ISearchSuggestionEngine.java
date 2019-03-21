public interface ISearchSuggestionEngine {
   // Gets list of following queries
   String[] getSuggestions(String searchQuery);
   
   // Getters and setters
   int getMaxResponses();
   void setMaxResponses();
   IEngineDictionary getDictionary();
   void setDictionary(IEngineDictionary dict);
   
}