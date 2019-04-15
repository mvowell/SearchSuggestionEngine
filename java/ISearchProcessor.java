public interface ISearchProcessor {
   // Returns a list of predictions for a search given a dictionary
   String[] receiveSearchString(String str);
}