
public class Station {
	
	private String stationName; //name of the station
	private int stationID; //ID number of the station
	private int playlistLength; //length of this station's playlist
	private int[] songPlays; //number of times this station plays each song 
	
	/**Constructor. Makes a new radio station from the name and ID.
	 * @param name Name of this radio station.
	 * @param ID ID of this radio station.
	 */
	public Station(String name, int ID) {
		stationName = name; 
		stationID = ID; 
		songPlays = null;
		playlistLength = 0;
	}
	/**
	 * @return Returns the name of this station.
	 * If you're lucky, you'll get their number too.
	 */
	public String getName() {
		return stationName;
	}
	/**
	 * @param name A name for this station.
	 */
	public void setName(String name) {
		stationName = name;
	}
	/**
	 * @return Returns the station ID.
	 */
	public int getID() {
		return stationID;
	}
	/**
	 * @param iD The Id for this station. 
	 */
	public void setID(int iD) {
		stationID = iD;
	}
	/**
	 * @return The length of the playlist that is broadcast on 
	 * this station counted by the number of songs. 
	 */
	public int getPlaylistLength() {
		return playlistLength;
	}
	/**
	 * @param playlistLength The length of this station's playlist.
	 */
	public void setPlaylistLength(int playlistLength) {
		this.playlistLength = playlistLength;
	}
	
	/**
	 * @return Station's String representation, with ID and name 
	 */
	public String toString() {
		return stationID + ". " + stationName;
	}
	
	public void setSongPlays(int[] songPlays) {
		this.songPlays = songPlays;
	}
	
	public int[] getSongPlays() {
		return songPlays;
	}
}
