package ssengine;

import java.util.ArrayList;

public class SearchProcessor implements ISearchProcessor {
   private IDictionary dict;
   // Remove entries below a certain popularity
   private int POPCUTOFF = 1000;
   public SearchProcessor(IDictionary dict){
      this.dict = dict;
   }
   
   // Get list of suggested search queries from an initial query
   @Override
   public String[] receiveSearchString(String str){
      // Filter the string
      str = str.replaceAll("[\\p{Punct}&&[^\\x2b\\x2d\\x3a]]","");
      str = str.trim();
      String[] split = str.split("\\s");
      if(split.length == 0){
         return split;
      }
      String last = split[split.length - 1];
      last = last.replaceAll("[\\x2b\\x2d\\x3a\\x22]","");
      String previous = (split.length > 1 ? split[split.length - 2] : "");
      previous = previous.replaceAll("[\\x2b\\x2d\\x3a\\x22]","");
      int position = str.lastIndexOf(last);
      //if(position == -1) throw new IllegalArgumentException("Something went wrong!");
      String[] correct = dict.correctWord(last);
      String[] extend = dict.extendWord(last);
      /*if(!previous.equals("")){
         correct = dict.filterWords(previous,correct);
         extend = dict.filterWords(previous,extend);
      }*/
      String[] follow = dict.followWord(last);
      ArrayList<String> output = new ArrayList<>();
      ArrayList<Integer> popularity = new ArrayList<>();
      for(int i = 0; i < correct.length; i++){
         int p = dict.wordPopularity(previous,correct[i]);
         if(p < POPCUTOFF) continue;
         int j = 0;
         while(j < popularity.size() && p < popularity.get(j)) j++;
         //output.add(j,str.substring(0,position) + correct[i] + " " + String.valueOf(p));
         output.add(j,str.substring(0,position) + correct[i]);
         popularity.add(j,p);
      }
      for(int i = 0; i < extend.length; i++){
         int p = dict.wordPopularity(previous,extend[i]);
         if(p < POPCUTOFF) continue;
         int j = 0;
         while(j < popularity.size() && p < popularity.get(j)) j++;
         //output.add(str.substring(0,position) + extend[i] + " " + String.valueOf(p));
         output.add(str.substring(0,position) + extend[i]);
         popularity.add(j,p);
      }
      
      for(int i = 0; i < follow.length; i++){
         int p = dict.wordPopularity(last,follow[i]);
         if(p < POPCUTOFF) continue;
         int j = 0;
         while(j < popularity.size() && p < popularity.get(j)) j++;
         //output.add(str + " " + follow[i] + " " + String.valueOf(p));
         output.add(str + " " + follow[i]);
         popularity.add(j,p);
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
}