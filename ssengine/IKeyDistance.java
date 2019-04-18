package ssengine;

public interface IKeyDistance {
   // Returns a list of the closest keys to a given key (by one grid unit)
   char[] getClosestCharacters(char c);
}