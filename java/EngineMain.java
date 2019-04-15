import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.*;
import javafx.scene.input.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.URI;

// TODO: Implement GUI
public class EngineMain extends Application {
   private ISearchProcessor mainProcessor;
   private Button searchButton;
   private TextField searchBar;
   private ListView searchSuggestionList;
   @Override
   public void start(Stage stage){
   try {
      Parent root = FXMLLoader.load(getClass().getResource("mainapp.fxml"));
      Scene mainScene = new Scene(root,640,480);
      stage.setScene(mainScene);
      stage.setTitle("Search Suggestion Engine");
      // Engine stuff
      IDictionaryData dd = new DictionaryData("dictionary.txt");
      IKeyDistance kd = new KeyDistance("keyboard.txt");
      IDictionary d = new Dictionary(dd,kd);
      mainProcessor = new SearchProcessor(d);
      // Set the components
      searchButton = (Button)mainScene.lookup("#searchButton");
      searchBar = (TextField)mainScene.lookup("#searchBar");
      searchSuggestionList = (ListView)mainScene.lookup("#searchSuggestionList");
      //searchSuggestionList.setEditable(false);
      // Set handlers
      searchButton.setOnAction(e -> handleSearchButtonPress());
      searchBar.setOnKeyTyped(e -> handleTextInput(e.getText()));
      root.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
      searchSuggestionList.setOnMouseClicked(
         e -> handleListSelection(
            (String)(searchSuggestionList.getSelectionModel().getSelectedItem())));
      
      //System.out.println((searchButton == null ? "null " : "good ") + (searchBar == null ? "null " : "good ") + (searchSuggestionList == null ? "null" : "good"));
      stage.show();
   } catch(IOException e){
      e.printStackTrace();
   }
   }
   
   public void handleTextInput(String keyPressed){
      //System.out.println(keyPressed);
      if(!searchBar.getText().trim().replaceAll("[^\\p{Alnum}\\x2b\\x2d\\x3a\\x22\\s]","").equals(""))updateList();
      else clearList();
   }
   
   public void handleKeyPress(KeyCode key){
      // Called in order to handle arrow keys
      //System.out.println(key);
   }
   
   public void handleSearchButtonPress(){
      if(!Desktop.isDesktopSupported()
      || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
         System.out.println("Error: cannot open desktop or browser!");
      }
      String searchBase = "https://www.google.com/search?q=";
      final String searchString = searchBase + searchBar.getText().trim().replaceAll("\\s","+").replaceAll("[^\\p{Alnum}\\x2b\\x2d\\x3a\\x22\\s]","");
      if(searchString.equals("")) return;
      System.out.println(searchString);
      new Thread(() -> {
         try {
            Desktop.getDesktop().browse(new URI(searchString));
         } catch(Exception e){
            e.printStackTrace();
            return;
         }
      }).start();
   }
   
   public void handleListSelection(String selection){
      if(selection != null){
         searchBar.setText(selection);
         searchSuggestionList.getSelectionModel().clearAndSelect(-1);
         updateList();
      }
   }
   
   private void updateList(){
      String[] searchQueries = mainProcessor.receiveSearchString(searchBar.getText().toLowerCase());
      ArrayList<String> list = new ArrayList<String>();
      for(int i = 0 ; i < searchQueries.length; i++){
         list.add(searchQueries[i]);
      }
      searchSuggestionList.getItems().setAll(list);
   }
   
   private void clearList(){
      ArrayList<String> list = new ArrayList<String>();
      searchSuggestionList.getItems().setAll(list);
   }
   
   public static void main(String[] args){
      launch(args);
   }
}