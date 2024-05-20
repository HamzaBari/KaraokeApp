
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.scene.control.TextArea;

public class KaraokeApplicationRunner extends Application {

    //Data Structures Implemented
    static LinkedList<String> obj = new LinkedList<>(); //This is used to split the song data.
    static LinkedList<Song> songLibary = new LinkedList<>();    //This is used for implementing the song liabary. 
    static BinarySearchTree<String, Song> searchSong = new BinarySearchTree<>();    //This is used for searching the songs in the binary search tree.
    static Stack<String> playlist = new Stack<>();  //This is used for implementing the song playlist.

    //Files
    static Scanner readFile;    //This is used for scanning through the smaple song file.
    static String videoDirectory = System.getProperty("user.dir");  //The is the videomp4 test file. 

    //GUI Elements
    Stage window;
    Scene mainMenuScene, searchForSong, playSongsScene, viewPlaylistScene, deleteSongScene;   //All scences in the GUI
    Button searchSongButton, playSongsButton, viewPlaylistButton, deleteSongButton, exitButton; //All buttons in main menu to navigate to the differnt scene.
    Label windowTitleMainMenu, windowTitleSearchSong, windowTitlePlaySong, windowTitlePlaylist, windowTitleDeleteSong;  //Label titlew heading for every scene. 
    MediaPlayer mediaPlayer;    //media player to display the test.mp4 videoURL. 
    MediaView mediaView;    //media view to view the song videoURL being played. 
    VBox videoPlayerWithSlider; //cretes the media view and the videoURL time slider as one content. 
    ListView playlistViewList;  //A list to view all the song which are in the playlist. This is to view the playlist. 

    public static void readFile() {
        //Reading Song File
        String dataFile = System.getProperty("user.dir") + File.separator + "sample-song-data.txt";

        //Putting the song data into an I/O file. 
        File myInputFile = new File(dataFile);

        try {

            readFile = new Scanner(myInputFile);    //This Scanner reads the song file. 

        } catch (FileNotFoundException ex) {    //catches the exception of the file not found. 

            System.out.println(ex.getMessage() + " File is not found, please try again.");  //returns back a message.
            System.exit(0); //Exits the application instatly. 

        }

        while (readFile.hasNextLine()) {    //using the read file scanner the while loop reads all the lines. 
            String line = readFile.nextLine();

            StringTokenizer tokenizer = new StringTokenizer(line, "\t\t\t");    //Splits the file contents by tab spaces

            while (tokenizer.hasMoreTokens()) {
                String newline = tokenizer.nextToken(); //this splits all the lines
                obj.add(newline);   //this adds those lines into an object

            }

            //The file has four contents and each content will be split.
            String songName = obj.get(0);   //on index 0 it gets the song name

            String artistName = obj.get(1); //on index 1 its gets the artist name

            String timePlayingOfSong = obj.get(2);  //on index 2 it gtes the playing times of the song
            int playingTime = Integer.parseInt(timePlayingOfSong);  //the time is parsed into an integer.

            String videoFileType = obj.get(3);      //on index 3 it gets the videoURL type file. 

            Song songData = new Song(songName, artistName, playingTime, videoFileType);   //Creates a class to hold the song data. 

            //Adding the Songs into the libary.
            songLibary.add(songData);

            //Clears the list
            obj.clear();

        }

        //This for-each loop gets all the songs frtom the song libary
        for (Song song : songLibary) {
            String title = song.getSongName(); //This gets all the names.
            searchSong.set(title, song);   //This adds all the song name to the binary search tree. It sets the key as the title of the song name.
        }                               //It sets the searchResultResponse as the song thereofre, the title is used to get the searchResultResponse/song from the tree when the song is searched.
    }

    //This is the function which Runs the GUI application.
    public void start(Stage primaryStage) {

        //This method reads the samle song data file. 
        readFile();

        //Start of GUI
        window = primaryStage;

        /* Start of Main Menu Scene */
        //Window title is the heading of the scene and this is all of its style with the location its placed at. 
        windowTitleMainMenu = new Label("Main Menu");
        windowTitleMainMenu.setTextFill(Color.CORNSILK);
        windowTitleMainMenu.setFont(new Font("Arial", 24));
        windowTitleMainMenu.setTranslateX(155);
        windowTitleMainMenu.setTranslateY(10);

        //These are all the buttons on the mian menu to navigate across the GUI.
        searchSongButton = new Button("Search For Song");
        playSongsButton = new Button("Play Songs");
        viewPlaylistButton = new Button("View Playlist");
        deleteSongButton = new Button("Delete Song From Playlist");
        exitButton = new Button("Exit");

        //This adds the buttons to the verticle box to display all the buttons togather verticllly and the no. 10 is used for adding space between them.
        VBox buttons = new VBox(10);

        //This is setting the location of the buttons.
        buttons.setTranslateX(160); //setTranslateX means moving to the side.
        buttons.setTranslateY(25);  //setTranslateY means moving up.

        //This adds the button into the verticl box.
        buttons.getChildren().addAll(searchSongButton, playSongsButton, viewPlaylistButton, deleteSongButton, exitButton);

        //These lambda functions are setting the buttons to disply the different scenes based on the button clicked.
        //The search button will display the search song scene.
        searchSongButton.setOnAction(e -> window.setScene(searchForSong));

        //The delete song button will set the delete song scene.
        deleteSongButton.setOnAction(e -> window.setScene(deleteSongScene));

        //By pressing the exit button the GUI application would close.
        exitButton.setOnAction(e -> System.exit(0));

        //This is setting the space inside the main menu scene, and adding styles.
        VBox mainMenuLayout = new VBox(20);
        BackgroundFill backgroundColour = new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY);
        Background backgroundColor = new Background(backgroundColour);
        mainMenuLayout.setBackground(backgroundColor);
        mainMenuLayout.getChildren().addAll(windowTitleMainMenu, buttons);  //This adds the window title and the buttons into the main menu scene.

        //This is setting the main menu window. This sets the layout of how it will be represented and including the height and width. 
        mainMenuScene = new Scene(mainMenuLayout, 450, 300);

        /* End of main menu. */
        /* Start of Search For Song Scene */
        //Window title is the heading of the scene and this is all of its style with the location its placed at. 
        windowTitleSearchSong = new Label("Search For Song");
        windowTitleSearchSong.setTextFill(Color.CORNSILK);
        windowTitleSearchSong.setFont(new Font("Arial", 24));
        windowTitleSearchSong.setTranslateX(230);
        windowTitleSearchSong.setTranslateY(10);

        //This is the layout of the search song scene with its space and location.
        VBox searchLayout = new VBox(30);
        searchLayout.setMaxWidth(300);
        searchLayout.setTranslateX(195);

        //This is the search label information and its sets its style and the location.
        Label searchLabel = new Label("Enter the song name:");
        searchLabel.setFont(new Font("Arial", 18));
        searchLabel.setTextFill(Color.CORNSILK);
        searchLabel.setTranslateX(50);

        //This is the textfield where the user would search for the song and there is a prompt inputted to help the user with that. 
        TextField searchSongTextField = new TextField();
        searchSongTextField.setPromptText("Click here and enter the song name:");   //This is the prompt to help. 

        //This is the button where the user presses the search to song a song. 
        Button searchButton = new Button("Search Song");
        searchButton.setTranslateX(85); //sets the lcoation of the button.

        //This is the search song response label where it will disply the song or an error messagere or show the song is added to the playlist. The styles and location have been set as well.
        Label searchSongResponseLabel = new Label("");
        searchSongResponseLabel.setFont(new Font("Arial", 16));
        searchSongResponseLabel.setTextFill(Color.CORNSILK);
        searchSongResponseLabel.setTranslateX(10);
        searchSongResponseLabel.setMaxWidth(1800);

        //This sets the two button horizontally togather with a space of 15. 
        HBox buttonLayout = new HBox(15);
        Button addSongButton = new Button("Add Song");
        Button mainMenuButtonForSearchSongScene = new Button("Main Menu");
        buttonLayout.setTranslateX(50);     //Sets the location of the button layout. 
        buttonLayout.getChildren().addAll(addSongButton, mainMenuButtonForSearchSongScene);   //The buttons are added therofre, the buttons will now be displayed in a layout.

        //All the content has been added to the searchLayout thereofre accroing to the layout the gui elements will be displayed in the search song scene.
        searchLayout.getChildren().addAll(searchLabel, searchSongTextField, searchButton, searchSongResponseLabel, buttonLayout);
        searchLayout.setTranslateY(30); //This sets the location of the layout. 

        //Search For Song using the Binary Search Tree.
        //When the user clicks on the search button the following code inside this lambda function will execute. 
        searchButton.setOnAction(e -> {
            try {
                //The Binary search tree searchers for what the user has inputted in the textfield after clicking the search button. 
                //It searches it with the key as the title of the song is the key once ot finds the key it will display the currentTime. 
                BinarySearchTree.Node result = searchSong.search(searchSongTextField.getText());
                String searchResultResponse = result.toString();    //converts the result into a string. 
                searchSongResponseLabel.setText(searchResultResponse);  //Sets the response of the serch to the response label text. 

                //Adds Song To Playlist
                //Once the user has search the song and found it then the user has to press the add song button to add the song to the playlist. 
                addSongButton.setOnAction(e1 -> {
                    //This pushes/adds the search song response to the end of the playlist, 
                    playlist.push(searchResultResponse);
                    //Gives a response back to alaert the user that the song is added to the playlist. 
                    searchSongResponseLabel.setText("Song Added To The Playlist");
                });

            } catch (java.lang.NullPointerException exp) {  //This is an exception raised when the currentTime doesnt exist in the binary search tree and it's serched.
                searchSongResponseLabel.setText("Invalid Search");  //Once the exception is caught it shows the the search was invalid which means the song dosent't exist in the playlist.
            }
        });

        //This is the main munu button which sets the scen back to the main mneu from search if it has been clicked. 
        mainMenuButtonForSearchSongScene.setOnAction(e -> window.setScene(mainMenuScene));

        //This is a verticel box to ceate the layout look for the search song scene. 
        VBox searchSongLayout = new VBox(20);
        //Below is where the background is being set in the search song scene. 
        BackgroundFill backgroundColourSecondScene = new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY);
        Background backgroundColorSearchSongScene = new Background(backgroundColourSecondScene);
        searchSongLayout.setBackground(backgroundColorSearchSongScene);
        searchSongLayout.getChildren().addAll(windowTitleSearchSong, searchLayout);    //This line of code sets the backgorund to the search song scene.

        //This adds the layout to the scene and sets the height and width of the search song scene. 
        searchForSong = new Scene(searchSongLayout, 650, 375);

        /* End of search for song scene. */
        /* Start of Playing Songs in the Playlist Scene */
        //Window title is the heading of the scene and this is all of its styles with the location its placed at. 
        windowTitlePlaySong = new Label("Play Songs");
        windowTitlePlaySong.setFont(new Font("Arial", 24));
        windowTitlePlaySong.setTextFill(Color.CORNSILK);

        //This is the button to go back to the main menu and it is set with the location of where its placed. 
        Button mainMenuButtonForPlaySongScene = new Button("Main Menu");
        mainMenuButtonForPlaySongScene.setTranslateX(895);
        mainMenuButtonForPlaySongScene.setTranslateY(510);

        //The horizontal box of videoURL elements is created with a apce of 85 to add the elements such as play/pause, skip and volumeElementLayout slider, and the time slider.
        //The style is also been set with the background and border.
        HBox videoElements = new HBox(85);
        videoElements.setBackground(new Background(new BackgroundFill(Color.PLUM, CornerRadii.EMPTY, Insets.EMPTY)));
        videoElements.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(2))));

        //Buttons to control the videoURL elements
        Button playButton = new Button("||");
        Button muteButton = new Button("Mute");
        Button stopButton = new Button("Stop");
        Button skipButton = new Button("Skip/Next");

        //This is a horizontal box created with space of 1.5 to add the label and the volumeElementLayout slider togather and also sets its style e.g. font.
        HBox volumeElementLayout = new HBox(1.5);
        Label volumeLabel = new Label("Volume:");
        volumeLabel.setFont(new Font("Arial", 16));
        volumeLabel.setTextFill(Color.BROWN);
        Slider volumeSlider = new Slider();
        volumeElementLayout.getChildren().addAll(volumeLabel, volumeSlider); //adds the volume label and slider togather next to each other as one element.

        //A space used to set the videoURL elements content in the middle.
        Label space = new Label("        ");

        //Adds the video controls to the video elements as one content togather. It basiclly groups them togather.
        videoElements.getChildren().addAll(space, playButton, muteButton, stopButton, skipButton, volumeElementLayout);
        videoElements.setTranslateY(410);   //sets the height lcoation of the videoURL elements

        //This gets the videoURL test play file to play the songs
        File videoFile = new File(videoDirectory, "test.mp4");  //This gets the videoURL file from the directory.
        try {
            Media videoURL = new Media(videoFile.toURI().toURL().toString());  //Chnages the videoURL file url to string
            mediaPlayer = new MediaPlayer(videoURL);   //Plays the videoURL url in the media player
        } catch (MalformedURLException ex) {    //This is the exception which is caught is the videoURL has not been recoginised.
            windowTitlePlaySong.setText("Video not recognised.");   //This will alert the user on the play songs window that the videoURL has not been recognised.    
        }

        //When the play songs button has been pressed then it will play the songs which are in the playlist. If there are no songs in the playlist then it will display a message to alert the user. 
        playSongsButton.setOnAction(e -> {
            window.setScene(playSongsScene);    //This sets the scene to the play songs scene.
            stackIterator<String> iterator = playlist.iterator();   //The iterator has been defined to iterate through the playlist. 
            playlist.reverseStack(playlist);    //This is the reverse algorthm which is called to revserse the playlist so it plays in order.

            //if the playlist has songs then it will contitune to play the next song in the playlist.
            //Each time the song is played it will remove that from the playlist. 
            if (iterator.hasNext()) {
                //Sets the window text with its location. 
                windowTitlePlaySong.setText("Playing Song: " + iterator.next());
                windowTitlePlaySong.setTranslateX(230);
                windowTitlePlaySong.setTranslateY(20);

                //Show/displays the content of the videoURL.
                videoPlayerWithSlider.setVisible(true);
                videoElements.setVisible(true);

                //Plays the new videoURL
                mediaPlayer.play();
                playlist.pop(); //Removes the song from the playlist.
            } else if (playlist.isEmpty()) {    //When the playlist is empty it will remove all the videoURL content from the window and sipaly the message.
                //Sets the window text with its location. 
                windowTitlePlaySong.setText("The playlist is empty, add more songs.");
                windowTitlePlaySong.setTranslateX(300);
                windowTitlePlaySong.setTranslateY(20);

                //Hides the videoURL elements.
                videoPlayerWithSlider.setVisible(false);
                videoElements.setVisible(false);

                mediaPlayer.stop(); //Stops the media player from playling.
            }
        });

        //Playing the next song automatclly in the playliist. E.g. when one song time finshes then it will automacticcly play the next until the playlist is empty. 
        //This event listener will execute once the videoURL has reached the end which is when the song s finsihed playling. 
        mediaPlayer.setOnEndOfMedia(() -> {
            stackIterator<String> iterator = playlist.iterator();   //The iterator has been defined to iterate through the playlist. 

            //if the playlist has songs then it will contitune to play the next song in the playlist.
            //Each time the song is played it will remove that from the playlist. 
            if (iterator.hasNext()) {
                //Sets the window text with its location. 
                windowTitlePlaySong.setText("Playing Song: " + iterator.next());
                windowTitlePlaySong.setTranslateX(230);
                windowTitlePlaySong.setTranslateY(20);

                //Show/displays the content of the videoURL.
                videoPlayerWithSlider.setVisible(true);
                videoElements.setVisible(true);

                //Gets the starting time of the new videoURL.
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.play(); //Plays the new videoURL
                playlist.pop(); //Removes the song after the videoURL has started playing
            } else if (playlist.isEmpty()) {    //When the playlist is empty it will remove all the videoURL content from the window and sipaly the message.
                //Sets the window text with its location. 
                windowTitlePlaySong.setText("The playlist is empty, add more songs.");
                windowTitlePlaySong.setTranslateX(300);
                windowTitlePlaySong.setTranslateY(20);

                //Hides the videoURL elements.
                videoPlayerWithSlider.setVisible(false);
                videoElements.setVisible(false);

                mediaPlayer.stop(); //Stops the media player from playling.
            }
        });

        //When the skip button is pressed then it will skip to the next song and remove the song from the playlist. If the playlist gets empty then it will just display a messge to alaert the user.
        skipButton.setOnAction(e -> {
            stackIterator<String> iterator = playlist.iterator(); //The iterator has been defined to iterate through the playlist. 
            playlist.reverseStack(playlist);    //This is the reverse algorthm which is called to revserse the playlist so it plays in order.
            if (iterator.hasNext()) {   //The iterator has been defined to iterate through the playlist. 
                //Sets the window text with its location. 
                windowTitlePlaySong.setText("Playing Song: " + iterator.next());
                windowTitlePlaySong.setTranslateX(230);
                windowTitlePlaySong.setTranslateY(20);

                //Show/displays the content of the videoURL.
                videoPlayerWithSlider.setVisible(true);
                videoElements.setVisible(true);

                //Gets the starting time of the new videoURL.
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.play(); //Removes the song after the videoURL has started playing
                playlist.pop();     //Removes the song after the videoURL has started playing
            } else if (playlist.isEmpty()) {    //When the playlist is empty it will remove all the videoURL content from the window and sipaly the message.
                //Sets the window text with its location. 
                windowTitlePlaySong.setText("The playlist is empty, add more songs.");
                windowTitlePlaySong.setTranslateX(300);
                windowTitlePlaySong.setTranslateY(20);

                //Hides the videoURL elements.
                videoPlayerWithSlider.setVisible(false);
                videoElements.setVisible(false);

                mediaPlayer.stop(); //Stops the media player from playling.
            }
        });

        //This button is used to navigate back to the main menu. 
        mainMenuButtonForPlaySongScene.setOnAction(e -> {
            window.setScene(mainMenuScene);
            mediaPlayer.stop(); //This will stop the media player. This is important otherwise you can hear the song playing when your on the other scences.
        });

        //This is the media view which will load the media player which contains the test.mp4 file. 
        mediaView = new MediaView(mediaPlayer);

        //This is the videoURL time slider to play the videoURL as the time goes along.
        Slider videoTimeSlider = new Slider();
        videoTimeSlider.setMaxWidth(650);   //The setting of the videoURL time slider height.

        //This is the videoURL player verticl layout with a space of two thereofre, both elements are togather as one. 
        videoPlayerWithSlider = new VBox(2);
        videoPlayerWithSlider.getChildren().addAll(mediaView, videoTimeSlider); //This adds them togather to make them one vertical content.

        //This is controlling the location of this element in the scene such as where it needs to be placed. 
        videoPlayerWithSlider.setTranslateX(175);
        videoPlayerWithSlider.setTranslateY(-75);

        //This is the play pause button funation. The button will play and pause the videoURL and the text of the button will be changed accoring to the correct output.
        playButton.setOnAction(e -> {
            switch (playButton.getText()) {
                case "|>":  //if the case is a triangle the  it will play the videoURL.
                    mediaPlayer.play(); //The videoURL is playing
                    playButton.setText("||");   //The button text is set to pause. which is if the user wants to pause the videoURL.
                    break;
                case "||":  //If the play button is set on pause sign and its been clicked then it wull pause the media player.
                    mediaPlayer.pause();    //The videoURL is paused.
                    playButton.setText("|>");   //The button text is set to triangle. which is if the user wants to play the videoURL again.
                    break;
            }
        });

        //This will mute and unmute the vdieo sound depending on the user choice. The text will chnage accroing to the input made by the user. 
        //The button will start at mute where you can hear sound. if the mute button is pressed the it will mute the videoURL.
        muteButton.setOnAction(e -> {
            switch (muteButton.getText()) {
                case "Mute":
                    mediaPlayer.setMute(true);  //if the mute is pressed then it wull set the videoURL sound to mute.
                    muteButton.setText("Unmute");   //This will change the text to unmute if the user wnats to hear the song sound again. 
                    break;
                case "Unmute":
                    mediaPlayer.setMute(false); //if the unmute is pressed then it will set the soudnd on.
                    muteButton.setText("Mute"); //This will change the text to mute again if the user wnats to mue/not hear the song sound again. 
                    break;
            }
        });

        //This is the stop button to stop the videoURL from playing. 
        stopButton.setOnAction(e -> {
            mediaPlayer.stop(); //The videoURL stops.
            playButton.setText("|>");   //The play button text is set to triagle as the user might decide to play the videoURL later on or in a few moment. 
        });

        //Volume Slider to control the volume of the song such as if the volume wants to be set high or low. 
        //The user can drag the slider to incresee and decrease the volume. 
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);   //Is gets the volume and times it by 100. 
        //The listener on the currentTime property of the slider has been added. 
        volumeSlider.valueProperty().addListener((Observable observable) -> {
            mediaPlayer.setVolume(volumeSlider.getValue() / 100);   //This will set the media player currentTime wiht the get currentTime of the volume slider and then it will divide by 100. This way it will control the sound volume for the song.
        });

        //Time Foward/Backward Slider
        //We can use thise videoURL time slider to go back to the previous frames of the videoURL or we can darg the slider fowared to fowared the song frames. 
        mediaPlayer.setOnReady(() -> {
            videoTimeSlider.setMin(0);  //The most minum vlaue for the videoURL time slider is set to 0. 
            videoTimeSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());   //This gets the total duration of the videoURL and converts it to second.
            videoTimeSlider.setValue(0);    //Tge valu of the videoURL time slider starts from 0. 
        });

        //Adds the listener to the current time of the videoURL.
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
            Duration duration = mediaPlayer.getCurrentTime();   //Gets the total duration of the videoURL time.
            videoTimeSlider.setValue(duration.toSeconds()); //This sets the total videoURL time into seconds.
        });

        //Adds the listener to the videoURL time slider.
        videoTimeSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (videoTimeSlider.isPressed()) {  //If the slider is dragged or pressed. 
                double currentTime = videoTimeSlider.getValue();  //Gets the current time on time videoURL where it has been clicked
                mediaPlayer.seek(new Duration(currentTime * 1000)); //Coverts the time to milliseconds and the videoURL seeks and starts playing from there.
            }
        });

        VBox playingSongsLayout = new VBox(20); //This is the layout of the playing song scene.

        //This is seeting the background of the playing songs.
        BackgroundFill backgroundColourThirdScene = new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY);
        Background backgroundColorThirdScene = new Background(backgroundColourThirdScene);
        playingSongsLayout.setBackground(backgroundColorThirdScene);    //Sets the background

        //Adds all the content to the play song layout so it is visible.
        playingSongsLayout.getChildren().addAll(windowTitlePlaySong, mainMenuButtonForPlaySongScene, videoElements, videoPlayerWithSlider);

        //This sets the scene layout, height and the width. The layout is now added to the scene.
        playSongsScene = new Scene(playingSongsLayout, 1000, 600);

        /* End of playing songs scene */
        /* Start of View Playlist Scene */
        //This buttons opens the view playlist scene
        //playlist.viewAll() function displys the state of what playlist is in at that current time.
        viewPlaylistButton.setOnAction(e -> {
            window.setScene(viewPlaylistScene); //Sets the scene. 
            playlistViewList.getItems().clear();    //This resets the playlist view list
            playlistViewList.getItems().add(playlist.viewAll());    //This would display what ever is cirrently in the playlist at the current time. 
        });

        //This is the heading label for the playlist view scene with its font, style and the location.
        windowTitlePlaylist = new Label("View Playlist");
        windowTitlePlaylist.setFont(new Font("Arial", 24));
        windowTitlePlaylist.setTextFill(Color.CORNSILK);
        windowTitlePlaylist.setTranslateX(180);
        windowTitlePlaylist.setTranslateY(10);

        //This is the list where the songs in the playlist are viewable.
        playlistViewList = new ListView();
        //Serrting the width and the location of the list to the fixed position.
        playlistViewList.setMaxWidth(450);
        playlistViewList.setTranslateX(25);
        playlistViewList.setTranslateY(5);

        //This is the button to navigate back to the main menu
        Button mainMenuForPlaylistView = new Button("Main Menu");
        mainMenuForPlaylistView.setTranslateX(380); //This sets the location
        mainMenuForPlaylistView.setOnAction(e -> window.setScene(mainMenuScene));   //Function  to set the scene to the main menu scene

        //This creates a vertical box to create the layout of the playlist view and it has a space of 20.
        VBox playlistViewLayout = new VBox(20);

        //The background is filled in for view playlist scene. 
        BackgroundFill backgroundColourForPlaylistView = new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY);
        Background backgroundColorFourthScene = new Background(backgroundColourForPlaylistView);
        playlistViewLayout.setBackground(backgroundColorFourthScene);   //Sets the background.
        playlistViewLayout.getChildren().addAll(windowTitlePlaylist, playlistViewList, mainMenuForPlaylistView);    //Adds the GUI elements to the view playlist layout

        //Adds the playlist view layout to the Scene and sets the height and width for the window.
        viewPlaylistScene = new Scene(playlistViewLayout, 500, 510);

        /* End of view playlist scene */
        /* Start of Delete Song Scene */
        //This sets the delete song window title to the delete song scene. This also sets the style of the label and the location.
        windowTitleDeleteSong = new Label("Delete Song");
        windowTitleDeleteSong.setTextFill(Color.CORNSILK);
        windowTitleDeleteSong.setFont(new Font("Arial", 24));
        windowTitleDeleteSong.setTranslateX(160);
        windowTitleDeleteSong.setTranslateY(10);

        //This is the button to navigate back to the main menu.
        Button mainMenuButtonForDeleteSong = new Button("Main Menu");
        mainMenuButtonForDeleteSong.setOnAction(e -> window.setScene(mainMenuScene));   //This function loads the main menu scene again

        //This is the button to delete the song
        Button deleteSongButton = new Button("Delete Song");

        //This vertical box makes the delete button and the main menu button in the same line as one content togather. 
        VBox deleteSongSceneButtons = new VBox(12.5);   //The spce verticlly between them is 12.5

        //Sets the space of the scene buttons
        deleteSongSceneButtons.setTranslateX(180);
        deleteSongSceneButtons.setTranslateY(-7);

        //Both buttons are now added to the vertical box as one content.
        deleteSongSceneButtons.getChildren().addAll(deleteSongButton, mainMenuButtonForDeleteSong);

        //This is the delete label on top of the delete song textarea to alert the user. This sets the location and its style.
        Label deleteLabel = new Label("Enter the song name:");
        deleteLabel.setFont(new Font("Arial", 18));
        deleteLabel.setTextFill(Color.CORNSILK);
        deleteLabel.setTranslateX(150);
        deleteLabel.setTranslateY(10);

        //This is the text area where the user is goign to type the song to delete it from the playlist. This also sets the textarea height and width as well as the location.
        TextArea searchSongToDelete = new TextArea();
        searchSongToDelete.setMinHeight(4);
        searchSongToDelete.setMaxWidth(330);
        searchSongToDelete.setTranslateX(80);
        searchSongToDelete.setTranslateY(10);
        searchSongToDelete.setPromptText("Enter e.g. Weekend    Kane Brown  226 test.mp4"); //This will guide the user of what they need to enter.

        //This is the delete label response to find out if the delete was succdessful or unsuccdessful. It also sets the location and style of the delete button.
        Label deleteResponse = new Label("");
        deleteResponse.setFont(new Font("Arial", 18));
        deleteResponse.setTextFill(Color.CORNSILK);
        deleteResponse.setTranslateX(200);
        deleteResponse.setTranslateY(4);

        /* Delete Song by inputting in the textfield.
         The user needs to search for a song in the text area field by typing it in and then press the delete button.
         if the delete label resposne turns true then the song has been deleted from the playlist.
         If the delete label resposne turns false then the song has not been deleted from the playlist as its invlaid search to delete the song.
         The following code will execute clicking the delete button. */
        deleteSongButton.setOnAction(e -> {
            boolean delete = playlist.searchAndRemove(searchSongToDelete.getText(), playlist); //Checks with the playlist to see if the song exists in the playlist.
            String reusltOfSongDeleted = Boolean.toString(delete);  //Truns the reuult into a strting from a boolean.
            deleteResponse.setText(reusltOfSongDeleted);    //Displays the string result in the label.
        });

        //This is the vertical box which adds all the GUI elements into the  the vertical box as one.
        VBox deleteSongSceneElements = new VBox(20);

        //Adds the GUI elements from the delete song into the delete song elements.
        deleteSongSceneElements.getChildren().addAll(windowTitleDeleteSong, deleteLabel, searchSongToDelete, deleteResponse, deleteSongSceneButtons);

        //This is the delete song layout with a space of 20.
        VBox deleteSongLayout = new VBox(20);

        //This sets the background of the delete song scene.
        BackgroundFill backgroundColourFifthScene = new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY);
        Background backgroundColorFifthScene = new Background(backgroundColourFifthScene);
        deleteSongLayout.setBackground(backgroundColorFifthScene);  //Sets the background.
        deleteSongLayout.getChildren().addAll(deleteSongSceneElements);     //Adds the GUI elements of the delete scene into the delete song layout.

        //This adds the delete song layout into the GUI and sets the height and width of the Delete Song GUI window.
        deleteSongScene = new Scene(deleteSongLayout, 450, 260);

        /* End of Delete Song Scene */
        //When Loading The Applciation GUI
        window.setScene(mainMenuScene); //First scene set is the main menu scene.
        window.setTitle("Karoke Application");  //The title of the window is the name of the application
        window.setResizable(false); //Stops the window from being resized.
        window.show();  //Show the window once it has been loaded

    }

    public static void main(String[] args) {
        launch(args);   //This loads up the GUI to open the applciation.
    }

}
