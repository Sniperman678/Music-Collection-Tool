/**
 * Class for Song object, the song class stores the details about individual songs in each album including name, artist, genre, duration.
 *
 * @author Lachlan Johnson (c3350131), Pravin Cherian-Fernandez (c3306899)
 * @version 1.12
 */
public class Song
{
	private String name;
	private String artist;
	private String genre;
	private int duration;
	
	
	public void setDetails(String songName, String songArtist, String songGenre, int songDuration) //Sets the song details to user input.
	{
		 name = songName;
		 artist = songArtist;
		 genre = songGenre;
		 duration = songDuration;
	}
	public String getName()//returns the song name.
	{
		return name;
	}
	public String getArtist(){ //returns the song artist.
		return artist;
	}
	public String getGenre(){ //returns the song genre.
		return genre;
	}
	public int length() { //returns the song length.
		return duration;
	}
	
}
