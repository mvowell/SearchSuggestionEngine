import java.util.ArrayList;

public class SearchProcessor {
   private IDictionary dict;
   public SearchProcessor(IDictionary dict){
      this.dict = dict;
   }
   public String[] receiveSearchString(String str){
      str = str.replaceAll("\\p{Punct}","");
      String[] split = str.split("\\s");
      if(split.length == 0){
         return split;
      }
      String last = split[split.length - 1];
      int position = str.indexOf(last);
      if(position == -1) throw new IllegalArgumentException("Something went wrong!");
      String[] correct = dict.correctWord(last);
      String[] extend = dict.extendWord(last);
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