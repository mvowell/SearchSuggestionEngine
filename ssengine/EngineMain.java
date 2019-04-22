package ssengine;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.fxml.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.URI;

// TODO: Implement GUI
public class EngineMain extends Application {
   private Stage stage;
   private ISearchProcessor mainProcessor;
   private IDictionaryData dictionaryData;
   private IKeyDistance keyDistance;
   private IDictionary dictionary;
   private Button searchButton;
   private Button loadFileButton;
   private TextField searchBar;
   private ListView searchSuggestionList;
   @Override
   public void start(Stage stage){
      try {
         /*Thread loadingThread = new Thread(){
            public void run(){
               while(true)
               JOptionPane.showMessageDialog(null,"Loading, please wait for the application to load!\n(This may take some time)");
            }
         };
         loadingThread.start();*/
         this.stage = stage;
         Parent root = FXMLLoader.load(getClass().getResource("mainapp.fxml"));
         Scene mainScene = new Scene(root,640,480);
         dictionaryData = new DictionaryData("dictionary.txt","ignorewords.txt");
         //((DictionaryData)dictionaryData).printData();
         keyDistance = new KeyDistance("keyboard.txt");
         dictionary = new Dictionary(dictionaryData,keyDistance);
         mainProcessor = new SearchProcessor(dictionary);
         // Set the components
         searchButton = (Button)mainScene.lookup("#searchButton");
         loadFileButton = (Button)mainScene.lookup("#loadFileButton");
         searchBar = (TextField)mainScene.lookup("#searchBar");
         searchSuggestionList = (ListView)mainScene.lookup("#searchSuggestionList");
         // Set handlers
         searchButton.setOnAction(e -> handleSearchButtonPress());
         loadFileButton.setOnAction(e -> handleLoadFile());
         searchBar.setOnKeyTyped(e -> handleTextInput(e.getText()));
         searchBar.setOnKeyPressed(e -> { if(e.getEventType().equals(KeyEvent.KEY_PRESSED)) handleKeyPress(e.getCode()); });
         //root.setOnKeyTyped(e -> handleKeyPress(e.getCode()));
         //searchBar.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
         searchSuggestionList.setOnMouseClicked(
            e -> handleListSelection(
               (String)(searchSuggestionList.getSelectionModel().getSelectedItem())));
         
         //System.out.println((searchButton == null ? "null " : "good ") + (searchBar == null ? "null " : "good ") + (searchSuggestionList == null ? "null" : "good"));
         stage.setScene(mainScene);
         stage.setTitle("Search Suggestion Engine");
         stage.show();
         //loadingThread.stop();
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
      //if(key.)
      //System.out.println(key);
      if(key.toString().equals(KeyCode.ENTER.toString())){
         int currentSelection = searchSuggestionList.getSelectionModel().getSelectedIndex();
         if(currentSelection == -1){
            handleSearchButtonPress();
         } else {
            handleListSelection((String)searchSuggestionList.getSelectionModel().getSelectedItem());
            searchSuggestionList.getSelectionModel().selectIndices(-1);
         }
      } else if(key.toString().equals(KeyCode.UP.toString())){
         int currentSelection = searchSuggestionList.getSelectionModel().getSelectedIndex();
         //System.out.println(currentSelection);
         if(currentSelection > -1){
            currentSelection--;
            searchSuggestionList.getSelectionModel().selectIndices(currentSelection);
         }
      } else if(key.toString().equals(KeyCode.DOWN.toString())){
         int currentSelection = searchSuggestionList.getSelectionModel().getSelectedIndex();
         //System.out.println(currentSelection);
         if(currentSelection < searchSuggestionList.getItems().size() - 1){
            currentSelection++;
            searchSuggestionList.getSelectionModel().selectIndices(currentSelection);
         }
      }
   }
   
   public void handleLoadFile(){
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Load Book File");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Document","*.txt"));
      File selectedFile = fileChooser.showOpenDialog(stage);
      if(selectedFile == null) return;
      dictionaryData.readBook(selectedFile.getAbsolutePath());
   }
   
   public void handleSearchButtonPress(){
      if(!Desktop.isDesktopSupported()
      || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
         System.out.println("Error: cannot open desktop or browser!");
      }
      String searchBase = "https://www.google.com/search?q=";
      final String searchString = searchBase + searchBar.getText()
            .trim()
            .replaceAll("\\s","+")
            .replaceAll("[^\\p{Alnum}\\x2b\\x2d\\x3a\\x22]","");
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