
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ApplicationRunner {

    static Scanner readFile;
    static Scanner scanner;
    static LinkedList<String> obj = new LinkedList<>();
    static LinkedList<Song> songLibary = new LinkedList<>();
    static BinarySearchTree<String, Song> searchSong = new BinarySearchTree<>();
    static Stack<String> playlist = new Stack<>();

    public static void main(String[] args) {

        String dataFile = System.getProperty("user.dir") + File.separator + "sample-song-data.txt";

        File myInputFile = new File(dataFile);

        try {

            readFile = new Scanner(myInputFile);

        } catch (FileNotFoundException ex) {

            System.out.println(ex.getMessage() + " File is not found, please try again.");
            System.exit(0);

        }

        while (readFile.hasNextLine()) {
            String line = readFile.nextLine();

            StringTokenizer tokenizer = new StringTokenizer(line, "\t\t\t");

            while (tokenizer.hasMoreTokens()) {
                String newline = tokenizer.nextToken();
                obj.add(newline);

            }

            String songName = obj.get(0);

            String artistName = obj.get(9);

            String timePlayingOfSong = obj.get(2);
            int playingTime = Integer.parseInt(timePlayingOfSong);

            String videoFileType = obj.get(3);

            Song s1 = new Song(songName, artistName, playingTime, videoFileType);

            //Adding the Songs into the libary.
            songLibary.add(s1);

            obj.clear();

        }
        StringBuilder msg;

        for (Song s : songLibary) {
            String title = s.getSongName();
            searchSong.set(title, s);
        }

        //Searching for songs by title.
        scanner = new Scanner(System.in);
        title();
        navigationMenu();

        while (scanner.hasNext()) {
            try {

                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("1")) {
                    System.out.println("");
                    System.out.print("Search for a song by title > ");
                    String input = scanner.nextLine();
                    BinarySearchTree.Node result = searchSong.search(input);
                    String value = result.toString();
                    System.out.println(value);

                    addSongToPlaylist();
                    boolean exit = false;
                    do {
                        try {
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    playlist.push(value);
                                    System.out.println("Song Added To The Playlist");
                                    System.out.println(playlist);
                                    break;
                                case 2:
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                            break;
                        } catch (java.util.InputMismatchException exp) {
                            System.out.println("Typing Error make sure you choose the Number Character Only.");
                            break;
                        }
                    } while (!exit);
                    System.out.println("");
                    navigationMenu();
                } else if (userInput.equalsIgnoreCase("2")) {                    
                    stackIterator<String> iterator = playlist.iterator();
                    playlist.reverseStack(playlist);
                    if (iterator.hasNext()) {
                        System.out.println("");
                        System.out.println(iterator.next());
                        System.out.println("");
                        playlist.pop();
                    } else if (playlist.isEmpty()) {
                        System.out.println("");
                        System.out.println("The playlist is empty, add more songs.");
                        System.out.println("");
                    }
                    navigationMenu();
                } else if (userInput.equalsIgnoreCase("3")) {
                    System.out.println("");
                    System.out.println("The Songs Which are in the playlist.");
                    System.out.println("");
                    System.out.println(playlist);
                    if (playlist.isEmpty()) {
                        System.out.println("The playlist is empty, add more songs.");
                    }
                    System.out.println("");
                    navigationMenu();
                } else if (userInput.equalsIgnoreCase("4")) {
                    System.out.println("");
                    playlist.viewAll();
                    System.out.println("");
                    System.out.print("Enter The Song Name and Remove from PlayList > ");
                    String input = scanner.nextLine();
                    System.out.println(playlist.searchAndRemove(input, playlist));
                    System.out.println("");
                    navigationMenu();
                } else if (userInput.equalsIgnoreCase("0")) {
                    System.exit(0);
                }

            } catch (java.lang.NullPointerException exp) {
                invlaidSearchMessage();
                navigationMenu();
                continue;
            }
        }

    }

    /* Intuitive User Interface Methods used to implement the Intuitive User Interface for searching the song in the libary
     and then adding it to the end of the playlist. */
    public static void title() {
        System.out.println("\tKaroke Application");
        System.out.println("");
    }

    public static void navigationMenu() {
        System.out.println("Search Song by Title.....................1");
        System.out.println("Play Next Song in the Playlist...........2");
        System.out.println("View The Songs in the Playlist...........3");
        System.out.println("Delete a Song from the Playlist..........4");
        System.out.println("Exit.....................................0");
        System.out.println("");
        System.out.print("Enter Choice > ");
    }

    public static void navigationMenuOnErrorAttempt() {
        System.out.println("");
        System.out.println("Search Song by Title.....................1");
        System.out.println("Play Next Song in the Playlist...........2");
        System.out.println("View The Songs in the Playlist...........3");
        System.out.println("Delete a Song from the Playlist..........4");
        System.out.println("Exit.....................................0");
        System.out.println("");
        System.out.print("Enter Choice > ");
    }

    public static void invlaidSearchMessage() {
        System.out.println("");
        System.out.println("Invalid Search");
        System.out.println("");
    }

    public static void addSongToPlaylist() {
        System.out.println("");
        System.out.println("Add Song To Playlist..............1");
        System.out.println("Go Back...........................2");
        System.out.println("");
        System.out.print("Enter Choice > ");
    }

}
