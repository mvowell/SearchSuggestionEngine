import java.util.ArrayList;

public class DictionaryEntry {
   private String key;
   private ArrayList<String> values;
   private ArrayList<Integer> counts;
   static private ArrayList<DictionaryEntry> entries;
   
   private DictionaryEntry(String key){
      if(entries != null)
      for(DictionaryEntry entry : entries){
         if(entry.key.equals(key)){
            throw new IllegalArgumentException("Invalid construction of entry, already exists!");
         }
      }
      this.key = key;
      this.values = new ArrayList<>();
      this.counts = new ArrayList<>();
      if(entries == null) entries = new ArrayList<>();
      entries.add(this);
   }
   
   public static DictionaryEntry getEntry(String key){
      if(entries != null)
      for(DictionaryEntry entry : entries){
         if(entry.key.equals(key)){
            return entry;
         }
      }
      return new DictionaryEntry(key);
   }
   
   public static ArrayList<DictionaryEntry> getList(){
      return entries;
   }
   
   public String getKey(){
      return key;
   }
   
   public void addValue(String value){
      if(!values.contains(value)){
         values.add(value);
         counts.add(1);
      } else
      counts.set(values.indexOf(value),counts.get(values.indexOf(value))+1);
   }
   
   public void removeValue(String value){
      values.remove(value);
   }
   
   public boolean hasValue(String value){
      return values.contains(value);
   }
   
   public String[] getValues(){
      String[] empty = new String[0];
      return values.toArray(empty);
   }
   
   public String[] getValuesSorted(){
      String[] output = new String[values.size()];
      ArrayList<String> valCopy = (ArrayList<String>)values.clone();
      ArrayList<Integer> numCopy = (ArrayList<Integer>)counts.clone();
      for(int i = 0; i < counts.size(); i++){
         int greatest = 0;
         String greatestStr = "";
         int greatestInd = 0;
         for(int j = 0; j < valCopy.size(); j++){
            if(greatest < numCopy.get(j)){
               greatest = numCopy.get(j);
               greatestStr = valCopy.get(j);
               greatestInd = j;
            }
         }
         output[i] = greatestStr;
         valCopy.remove(greatestInd);
         numCopy.remove(greatestInd);
      }
      return output;
   }
   
   public int getCount(String value){
      if(!hasValue(value)) return 0;
      return counts.get(values.indexOf(value));
   }
   
   @Override
   public boolean equals(Object o){
      if(o == null) return false;
      if(!(o instanceof DictionaryEntry)) return false;
      return this.key.equals( ( (DictionaryEntry)o ).key);
   }
}