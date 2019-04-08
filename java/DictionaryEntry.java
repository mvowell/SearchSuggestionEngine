import java.util.ArrayList;

public class DictionaryEntry {
   private String key;
   private ArrayList<String> values;
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
      if(!values.contains(value))
      values.add(value);
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
   
   @Override
   public boolean equals(Object o){
      if(o == null) return false;
      if(!(o instanceof DictionaryEntry)) return false;
      return this.key.equals( ( (DictionaryEntry)o ).key);
   }
}