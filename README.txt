# SearchSuggestionEngine
A project for Software Engineering at Dalton State. This is a GUI based search engine which lists suggestions based on correcting errors, suggesting longer words, and suggesting following words, from a source txt file.

Requirements:
Java 8 JRE (https://www.java.com/en/) or above
JavaFX (if Java JRE version is greater than or equal to 11)

Instructions:
This is the source code for the Search Suggestion Engine!
In order to run this software from the most current build:
 1. download the most current release from Github (https://github.com/mvowell/SearchSuggestionEngine/releases)
 2. unzip the current release ("SearchSuggestionEngine.zip") 
 3. run the .jar located inside ("SearchSuggestionEngine.jar") with the JRE.
JRE MUST be Java 8 or above!
	(If JRE version is 11, the program must be run from the console with the JavaFX directories and javafx.controls and javafx.fxml modules included.) (Further instructions for Java 11 and above are OMITTED from this document as it is RECOMMENDED to install Java 8.)
WARNING: This program may take quite some time to load! Please be patient, and if the program has not loaded within one minute, please report this to the "Issues" section on the SearchSuggestionEngine page, or get a better CPU/hard drive. Currently, in order to work on all hardware, the "loading screen" is disabled until further notice.

Usage:
Type in any search query into the search bar at the top of the application. The search suggestion list will automatically fill with suggested corrections and following words. In order to select a suggestion, highlight the suggestion with the up/down arrow keys and press enter, or click on the suggestion. The current query will then be replaced with the suggested query. By pressing the "Search" button next to the search bar, or by pressing "Enter" while the search bar is selected, a window/tab will open to Google with the current search query. To add more words to the dictionary, press the "..." button next to the "Search" button. This will bring up a dialog to select a txt file. Select the file which you would like to append to the dictionary, and click "Open". An example "dictionarymacbeth.txt" has been provided for the user to test this feature.

If you would like to add your own words by default, add an alternate keyboard layout, or add a word filter, edit the "dictionary.txt", "keyboard.txt", or "ignorewords.txt". "keyboard.txt" consists of all characters on the keyboard printed out in a grid, in lowercase. "ignorewords.txt" is a list in lowercase of all words to be ignored, separated by newlines.
