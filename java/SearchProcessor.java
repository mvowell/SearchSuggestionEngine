import java.util.ArrayList;

public class SearchProcessor implements ISearchProcessor {
   private IDictionary dict;
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
      int position = str.indexOf(last);
      //if(position == -1) throw new IllegalArgumentException("Something went wrong!");
      String[] correct = dict.correctWord(last);
      String[] extend = dict.extendWord(last);
      if(!previous.equals("")){
         correct = dict.filterWords(previous,correct);
         extend = dict.filterWords(previous,extend);
      }
      String[] follow = dict.followWord(last);
      ArrayList<String> output = new ArrayList<>();
      for(int i = 0; i < correct.length; i++){
         output.add(str.substring(0,position) + correct[i]);
      }
      for(int i = 0; i < extend.length; i++){
         output.add(str.substring(0,position) + extend[i]);
      }
      
      for(int i = 0; i < follow.length; i++){
         output.add(str + " " + follow[i]);
      }
      String[] empty = new String[0];
      return output.toArray(empty);
   }
}