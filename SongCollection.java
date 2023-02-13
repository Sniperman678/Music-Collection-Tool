import java.util.*;
import java.io.*;
/**
 * The SongCollection class is responisble for managing the entire program, storing 4 albums and the methods required to modify these albums and songs.
 *
 * @author Lachlan Johnson (c3350131), Pravin Cherian-Fernandez (c3306899)
 * @version 1.12
 */
public class SongCollection
{
    String fileName = "";
    int option;
    int songIndex = -1;
    int albumIndex = -1;
    String option2;
    int length;
    static Scanner console = new Scanner(System.in);
    private Album[] album;
    String albumName;
    String songName, songArtist, songGenre;

    public void run() { // Run Method - shows the main options that the program is able to
        boolean fileFound=false;
        album = new Album[4];
        for (int i=0;i<4;i++){//4 albums
            album[i] = new Album();
        }
        do {
        System.out.println("Do you want to import from file? Yes(1), No(2)");// Asking User if they want to Import .txt file which whill load onto program
        option = console.nextInt();
        console.nextLine();
        if (option==1) {
            System.out.println("Please enter file name (including extensions).");
            fileName = console.nextLine();// Finding and locating file(.txt) name
            File file = new File(fileName);
            try {
                Scanner inputStream = new Scanner(file);
                fileFound = true;
                while (inputStream.hasNextLine ())
                {
                    String line = inputStream.nextLine ();
                    
                        //System.out.println (line);
                        if (line.startsWith("Album")) {
                            albumIndex++;
                            songIndex = -1;
                            if (albumIndex<4){// Allowing 4 albums and if there is more than 4 they will not be loaded into the program
                                albumName = line.replaceFirst("Album","").trim();
                                album[albumIndex] = new Album();//Creates new instance of Album.
                                album[albumIndex].setName(albumName);
                            } else {
                                System.out.println("Exceeded album limit");//Warning message that is prompted when there is more than 4 albums in .txt file
                                break;
                            }
                        }
                        if (line.startsWith("Name")) {// This finds the information from the .txt file and reads it in the given format of Name, Artist,Duration and Genre
                            songIndex++;
                            songName = line.replaceFirst("Name","").trim();
                        }
                        if (line.startsWith("Artist")) {
                            songArtist = line.replaceFirst("Artist","").trim();
                        }
                        if (line.startsWith("Duration")) {
                            length = Integer.parseInt(line.replaceFirst("Duration","").trim());
                        }
                        if (line.startsWith("Genre")) {
                            songGenre = line.replaceFirst("Genre","").trim();
                            album[albumIndex].addSong(songName,songArtist,songGenre,length);
                        }
                    
                }
            inputStream.close ();
            } 
            catch (FileNotFoundException e) {//Error message presented when format of document is incorrect or cannot be found
                System.out.println("File not found or invalid.");
                fileFound = false;
            }
        }
    } while ((option != 1)||(!fileFound));
        do {
            System.out.println("Create Album(0), Add Song(1), Query(2), Remove(3), Exit (9): ");//Main Menu
            option = console.nextInt();
            console.nextLine();
            switch(option)
            {
                case 0 : createAlbum();
                        break;
                case 1: addSong();
                        break;
                case 2: songQuery();
                        break;
                case 3: delete();
                        break;
                case 9: break;
                default: System.out.println("invalid option");
            }
        }
        while(option!=9);
    }

    public static void main(String[] args) { // Creates and executes a new instance.
        SongCollection sg = new SongCollection();
        sg.run();
    }

    public void createAlbum(){// Creating Album Methood - Allows users to name a album and save it in a slot.
        boolean alreadyExists = false;
        int index = 0;
        do {
            System.out.println("Enter Album Name:");
            albumName = console.nextLine();
            alreadyExists = false;
            for (int i=0;i<4;i++){//repeats 4 times
                if (albumName.equals(album[i].getName())){
                    alreadyExists = true;
                    System.out.println("Error: Album already exists. please try again.");//Users arent able to type name of album multiple times.
                    break;
                }
            }
        } while (alreadyExists);
        do {
        System.out.println("Save location - Slot 1, Slot 2, Slot 3 or Slot 4? Cancel(9):");//Saving album in chosen allocated slot.
        index = console.nextInt()-1;
        console.nextLine();

            switch(index)
            {
                case 0:
                case 1:
                case 2:
                case 3: if (!album[index].isEmpty()){
                            System.out.println("This will overide " + album[index].getName() + ". Do you want to continue? Yes(1), No(2):");
                            option = console.nextInt();
                            console.nextLine();
                            if (option == 1){
                                album[index] = null;//Clears Album.
                                album[index] = new Album();//Creates new instance of Album.
                                album[index].setName(albumName);//Allows user to set the new name.
                            }
                        } else {
                            album[index] = null;
                            album[index] = new Album();
                            album[index].setName(albumName);
                        }
                        break;
                case 8: break;
                default: System.out.println("Input is invalid. Please enter 1, 2, 3, 4 or 9.");//If User has selected option that is invaild, message is shown.
            }
        } while (index != 0 && index != 1 && index != 2 && index != 3 && index !=8);
    }
    public void addSong(){//Adding Song Method - Allows User to add a song into a a album with details of Artist, Song name, Genre, and Duration.
        boolean success = false;
        boolean exists = false;
        int index = 0;
    	System.out.println("Enter song name:");
        songName = console.nextLine();
        exists = sameName(songName);
        if (exists) {
            System.out.println("These song(s) with the same name already exist, do you want to continue? Yes(1), No(2):");
            option = console.nextInt();
            console.nextLine();
            if (option == 2){
                return;
            }
        }
    	System.out.println("Enter song artist:");
        songArtist = console.nextLine();
    	System.out.println("Enter song genre:");
        songGenre = console.nextLine();
        System.out.println("Enter song length (in seconds):");
        do {
            length = console.nextInt();
            console.nextLine();
            if (length <=0) System.out.println("length must be greater than 0, enter length again:");
        } while (length <= 0);
        do {
            System.out.println("Select album to add song to: " + album[0].getName() + "(1), " + album[1].getName() + "(2), " + album[2].getName() + "(3), " + album[3].getName() + "(4), Cancel(9):");
            index = console.nextInt() - 1;
            console.nextLine();
            switch(index){
                case 0:
                case 1:
                case 2: 
                case 3: success = album[index].addSong(songName,songArtist,songGenre,length);
                        break;
                case 8: success = true;
                    break;
                default: System.out.println("Input is invalid. Please enter 1, 2, 3, 4 or 9.");
            }
            index = 0;
        } while (success == false);
    }
    public void songQuery(){ //Manages the search function of the program. 
        System.out.println("All Albums(0), Songs From Album(1), Under Certain Length(2), Certain Genre(3), Certain Name(4): ");
        option = console.nextInt();
        boolean noResults = false;
        console.nextLine();
        switch(option)
        {
            case 0 :
                    getAlbumDetails(1); //Details for all albums listed.
                    System.out.println("----------------------------------------------------------------------");
                    getAlbumDetails(2);
                    System.out.println("----------------------------------------------------------------------");
                    getAlbumDetails(3);
                    System.out.println("----------------------------------------------------------------------");
                    getAlbumDetails(4);
                    System.out.println("----------------------------------------------------------------------");
                    break;
            case 1: System.out.println("Select album to add song to: " + album[0].getName() + "(1), " + album[1].getName() + "(2), " + album[2].getName() + "(3), " + album[3].getName() + "(4), Cancel(9):");
                    option = console.nextInt();
                    console.nextLine();
                    switch(option){ // Shows the details of a specific album.
                        case 1:
                        case 2:
                        case 3:
                        case 4: getAlbumDetails(option);
                                break;
                        default:
                            break;
                    }
                    break;
            case 2: System.out.println("Find songs under duration (in minutes):");
                    option = console.nextInt();
                    console.nextLine();
                    System.out.println("");
                    noResults=true;
                    for (int i=0;i<4;i++) {
                        if (album[i].songLength(option*60))
                        {
                            System.out.println("----------------------------------------------------------------------");
                            noResults=false;
                        }
                    }
                    if (noResults) System.out.println("No Results Found.");

                    break;
            case 3: System.out.println("Find songs of certain genre:");
                    option2 = console.nextLine().toLowerCase(); // sets the users input to lowercase to make the search case-insensitive.
                    System.out.println("");
                    noResults=true;
                    for (int i=0;i<4;i++) {
                        if (album[i].songGenre(option2)){
                            System.out.println("----------------------------------------------------------------------");//Similar to "songLength()" the users input is compared to each song in the album and displayed if matching.
                            noResults = false;
                        }
                    }
                    if (noResults) System.out.println("No Results Found.");
                    break;
            case 4: System.out.println("Find songs with the same name (Case Sensitive):");
                    option2 = console.nextLine();
                    System.out.println("");
                    if (!sameName(option2)) 
                    {
                        System.out.println("No Results Found.");
                    }
                    break;
            default: System.out.println("invalid option");
                    break;
        }
    }
    public void getAlbumDetails(int albumNumber){ //Gets of the details of the specified album. (album number, name, songs etc.)
                album[albumNumber-1].getDetails(); //Gets details of each song in the album.
        }
    public void delete(){ //Delete Method - Manages the deletion of albums and songs.
        boolean success = false;
        int index = 0;
        do {
            System.out.println("Delete Album(0), Delete Song From Album(1), Cancel(9)");
            option = console.nextInt();
            console.nextLine();
            switch (option) {
                case 0: do {
                            System.out.println("Select album to delete - " + album[0].getName() + "(1), " + album[1].getName() + "(2), " + album[2].getName() + "(3), " + album[3].getName()+ "(4), Cancel(9)"); // Prints all album names for user to select.
                            index = console.nextInt()-1;
                            console.nextLine();
                            switch (index) {
                                case 0:
                                case 1:
                                case 2: 
                                case 3: if (!album[index].isEmpty()) { //Check if the album is not empty.
                                                album[index] = null; //Clears album.
                                                album[index] = new Album(); //Creates a new instance of album1.
                                                System.out.println("Album has been deleted.");
                                            } else {
                                                System.out.println("Album is already empty.");
                                            }
                                        break;
                                case 8: break;
                                default: System.out.println("Input is invalid. Please enter 1, 2, 3, 4 or 9.");
                            }
                        } while (index != 0 && index != 1 && index != 2 && index != 3 && index !=8); //Continue to ask the user for input until a valid input is entered.
                        break;
                case 1: songDelete:
                        do {
                            do {
                                System.out.println("Select album to delete song from - " + album[0].getName() + "(1), " + album[1].getName() + "(2), " + album[2].getName() + "(3), "+ album[2].getName()+"(4), Cancel(9)");
                                option = console.nextInt();
                                console.nextLine();
                                switch (option) {
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                        if (album[(option-1)].isEmpty()){
                                                System.out.println("Album is empty.");
                                                success = false;
                                            } else {
                                                success = true;
                                            }
                                            break;
                                    case 9: break songDelete;
                                    default: System.out.println("Input is invalid. Please enter 1, 2, 3, 4 or 9.");
                                }
                            } while (success == false);
                            
                            // After album is selected, user must select a song to delete for the selected album.
                            
                            System.out.println("Select number of song to remove:");
                            switch (option) {
                                case 1:
                                case 2:
                                case 3: 
                                case 4: album[(option-1)].getDetails(); // Lists the avaliable songs in selected album.
                                    index = console.nextInt();
                                    console.nextLine();
                                    success = album[(option-1)].deleteSong(index); //If the inputted song exists, it is deleted and "success" is set to true. If song doesn't exist, the method returns false. 
                                    break;
                                default: System.out.println("Input invalid.");
                            }
                            break;
                        } while (success == false); //Loops until valid input is entered.
                case 9: break;
                default: System.out.println("Input is invalid. Please enter 0, 1 or 9.");
            }
        } while (option != 0 && option != 1 && option != 9);//Loops until valid input is entered.
    option = 0; // Resets option
    }
    public boolean sameName(String songName){
        boolean exists = false;
        for (int i=0;i<4;i++){
            if (album[i].sameName(songName)) 
            {
                exists = true;
                System.out.println("----------------------------------------------------------------------");
            }
        }
        return exists;
    }
}