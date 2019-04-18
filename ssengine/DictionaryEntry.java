package ssengine;

import java.util.ArrayList;

// This basically stores all of the dictionary data statically, DData class manages this
public class DictionaryEntry {
   private String key;
   private ArrayList<String> values;
   private ArrayList<Integer> counts; // Used to keep track of num of occurences of a value
   private int usageCount = 1;
   
   public DictionaryEntry(String key){
      this.key = key;
      this.values = new ArrayList<>();
      this.counts = new ArrayList<>();
   }
   
   public String getKey(){
      return key;
   }
   
   public void addValue(String value){
      if(!values.contains(value)){
         values.add(value);
         counts.add(1);
      } else
      counts.set(values.indexOf(value),
         counts.get(values.indexOf(value))+1);
   }
   
   public void removeValue(String value){
      int index = values.indexOf(value);
      if(index == -1) return;
      values.remove(index);
      counts.remove(index);
   }
   
   public boolean hasValue(String value){
      return values.contains(value);
   }
   
   public String[] getValues(){
      //String[] empty = new String[0];
      //return values.toArray(empty);
      // Always sort the list
      return getValuesSorted();
   }
   
   // Returns the list of values sorted by their count
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
   
   // number of occurences of a value
   public int getCount(String value){
      if(!hasValue(value)) return 0;
      return counts.get(values.indexOf(value));
   }
   
   public int getUsage(){
      return usageCount;
   }
   
   public void incrementUsage(){
      usageCount++;
   }
   
   @Override
   public boolean equals(Object o){
      if(o == null) return false;
      if(!(o instanceof DictionaryEntry)) return false;
      return this.key.equals( ( (DictionaryEntry)o ).key);
   }
}