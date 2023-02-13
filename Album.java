/**
 * Class for Album object, the album class stores the albums name and 4 Song objects.
 * @author Lachlan Johnson (c3350131), Pravin Cherian-Fernandez (c3306899)
 * @version 1.12
 */
public class Album
{
    // instance variables 
    private String name;
    private Song[] song;
    private int totalTime;
    private final int MAX_TIME = 720; //12 minutes

    /**
     * Constructor for objects of class Album
     */
    public Album()
    {
        song = new Song[5];
        for (int i=0;i<5;i++){ //create a new Song object in each value of song.
            song[i] = new Song();
        }
    }

    public void setName (String inputName) //Sets the album "name" variable to the input parameter.
    {
        name = inputName;
    }
    public String getName() //Returns the album name is slot is not empty. 
    {
        if (name == null){ //Checks if album is empty.
            return "Empty Slot";
        } else {
            return name;
        }
    }
    public boolean addSong(String songName, String songArtist, String songGenre, int length){
        boolean success = false;
        //Nested if statement neccesary for seperate error messages.
        if (!alreadyExists(songName, songArtist, length)){ //Checks if song already exists (same name, artist and length.)
            if (!isEmpty()){ //checks if album is empty (cannot add a song to an empty album).
                if ((getAlbumLength() + length <= MAX_TIME)){ // Checks if adding the new song will exceed the "MAX_TIME" variable.
                    if (songGenre.equals("bossa nova") || songGenre.equals("hip hop") || songGenre.equals("pop") || songGenre.equals("rock")){
                        for (int i=0;i<5;i++){ //check each index of song for an empty slot, if one is found add song and set details.
                            if (song[i].getName() == null) {
                                song[i] = new Song();
                                song[i].setDetails(songName, songArtist, songGenre, length);
                                success = true;
                                break;
                            }
                        }
                        if (!success){ //If all slots full song cannot be added.
                            System.out.println("Unfortunately there are no more slots available.");
                            success = false;
                        }
                    } else {
                        System.out.println("Not a valid genre, please enter \"bossa nova\", \"hip hop\", \"pop\", or \"rock\".");
                        success = true;
                    }
                } else {
                    System.out.println("Failed to add song: Album length exceeds max time.");
                    success = false;
                }
            } else {
                System.out.println("Failed to add song: Album slot empty.");
                success = false;
            }
        } else {
            System.out.println("Failed to add song: Song Already exists.");
            success = false;
        }
        return success;
    }
    public int getAlbumLength() { //Adds the value of all song lengths and returns the total album length.
        totalTime = 0;
        for (int i=0;i<5;i++){
            totalTime = totalTime + song[i].length(); //add up all songs length.
        }
    	return totalTime;
    }
    public void getDetails(){ //Prints all details of avaliable songs.
        System.out.println("### "+getName()+" ###\n");
        for (int i=0;i<5;i++){
            songDetails(i); //prints all songs in the album.
        }
    }
    public void songDetails(int songNum){
        if (song[songNum].getName() != null) System.out.println("Album "+ "\"" + getName() + "\"" + "   Song " + (songNum+1) + ": " + song[songNum].getName() + " by " + song[songNum].getArtist() + " (Genre: " + song[songNum].getGenre() + ", Duration: " + song[songNum].length() + "s)"); // if song slot is not empty print details.
    }
    public boolean songLength(int inputLength){ //Compares the length of all songs in the album to a user input, if song length is less than the inputted value, the songs details are printed.
        boolean success = false;
        for (int i=0;i<5;i++){
            if ((song[i].length() <= inputLength) && (song[i].getName() != null)) { //Checks if less than the inputted time and is not an empty slot.
                songDetails(i);
                success = true;
            }
        }
        return success;
    }
    public boolean songGenre(String inputGenre) {//Compares the Genre of all the songs in the album to the users input.
        boolean success = false;
        for (int i=0;i<5;i++){
            if (song[i].getName() != null){
                if ((song[i].getGenre().toLowerCase().equals(inputGenre))) {//Case Sensitive.
                    songDetails(i);
                    success = true;
                }
            }
        }
        return success;
    }
    public boolean deleteSong(int songNum){ //Manages the deletion of songs.
        boolean success = false;
        int index = songNum-1;
            switch(index) { 
                case 0:
                case 1:
                case 2:
                case 3: 
                case 4: if (song[index].getName() == null) {
                            System.out.println("Song does not exist.");
                        } else {
                            success = true;
                            song[index] = null;
                            song[index] = new Song();
                        }
                        break;
                default:System.out.println("Input is invalid. Please enter 1, 2, 3, 4 or 5.");
                        success = false;
            }
        return success;
    }
    public boolean isEmpty() { //If slot is empty, returns true.
        return (name == null);
    }
    public boolean alreadyExists(String songName, String songArtist, int length) { //Compares the inputted parameters to all songs in the value to ensure a duplicate is not created.
        boolean exists = false;
        for (int i=0;i<5;i++){
            if (songName.equals(song[i].getName()) && songArtist.equals(song[i].getArtist()) && length == song[i].length()) exists = true;
        }
        return exists;
    }
    public boolean sameName(String songName){//Checks if inputted name already exists. 
        boolean exists = false;
        for (int i=0;i<5;i++){
            if (songName.equals(song[i].getName())){
                songDetails(i);
                exists = true;
            }
        }
        return exists;
    }
}